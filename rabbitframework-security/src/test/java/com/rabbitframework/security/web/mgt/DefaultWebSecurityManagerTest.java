/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.rabbitframework.security.web.mgt;

import com.rabbitframework.security.web.servlet.SecurityHttpSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rabbitframework.security.authc.UsernamePasswordToken;
import com.rabbitframework.security.config.Ini;
import com.rabbitframework.security.realm.text.IniRealm;
import com.rabbitframework.security.session.ExpiredSessionException;
import com.rabbitframework.security.session.Session;
import com.rabbitframework.security.session.mgt.AbstractSessionManager;
import com.rabbitframework.security.subject.PrincipalCollection;
import com.rabbitframework.security.subject.SimplePrincipalCollection;
import com.rabbitframework.security.subject.Subject;
import com.rabbitframework.security.web.config.WebIniSecurityManagerFactory;
import com.rabbitframework.security.web.session.mgt.WebSessionManager;
import com.rabbitframework.security.web.subject.WebSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @since 0.9
 */
public class DefaultWebSecurityManagerTest extends AbstractWebSecurityManagerTest {

    private DefaultWebSecurityManager sm;

    @Before
    public void setup() {
        sm = new DefaultWebSecurityManager();
        sm.setSessionMode(DefaultWebSecurityManager.NATIVE_SESSION_MODE);
        Ini ini = new Ini();
        Ini.Section section = ini.addSection(IniRealm.USERS_SECTION_NAME);
        section.put("lonestarr", "vespa");
        sm.setRealm(new IniRealm(ini));
    }

    @After
    public void tearDown() {
        sm.destroy();
        super.tearDown();
    }

    protected Subject newSubject(ServletRequest request, ServletResponse response) {
        return new WebSubject.Builder(sm, request, response).buildSubject();
    }

	@Test
	public void checkSessionManagerDeterminesContainerSessionMode() {
		sm.setSessionMode(DefaultWebSecurityManager.NATIVE_SESSION_MODE);
		WebSessionManager sessionManager = createMock(WebSessionManager.class);

		expect(sessionManager.isServletContainerSessions()).andReturn(true).anyTimes();

		replay(sessionManager);

		sm.setSessionManager(sessionManager);

		assertTrue("The set SessionManager is not being used to determine isHttpSessionMode.", sm.isHttpSessionMode());

		verify(sessionManager);
	}

    @Test
    public void shiroSessionModeInit() {
        sm.setSessionMode(DefaultWebSecurityManager.NATIVE_SESSION_MODE);
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void testLogin() {
        HttpServletRequest mockRequest = createNiceMock(HttpServletRequest.class);
        HttpServletResponse mockResponse = createNiceMock(HttpServletResponse.class);

        expect(mockRequest.getCookies()).andReturn(null);
        expect(mockRequest.getContextPath()).andReturn("/");

        replay(mockRequest);

        Subject subject = newSubject(mockRequest, mockResponse);

        assertFalse(subject.isAuthenticated());

        subject.login(new UsernamePasswordToken("lonestarr", "vespa"));

        assertTrue(subject.isAuthenticated());
        assertNotNull(subject.getPrincipal());
        assertTrue(subject.getPrincipal().equals("lonestarr"));
    }

    @Test
    public void testSessionTimeout() {
        shiroSessionModeInit();
        long globalTimeout = 100;
        ((AbstractSessionManager) sm.getSessionManager()).setGlobalSessionTimeout(globalTimeout);

        HttpServletRequest mockRequest = createNiceMock(HttpServletRequest.class);
        HttpServletResponse mockResponse = createNiceMock(HttpServletResponse.class);

        expect(mockRequest.getCookies()).andReturn(null);
        expect(mockRequest.getContextPath()).andReturn("/");

        replay(mockRequest);

        Subject subject = newSubject(mockRequest, mockResponse);

        Session session = subject.getSession();
        assertEquals(session.getTimeout(), globalTimeout);
        session.setTimeout(125);
        assertEquals(session.getTimeout(), 125);
        sleep(200);
        try {
            session.getTimeout();
            fail("Session should have expired.");
        } catch (ExpiredSessionException expected) {
        }
    }

    @Test
    public void testGetSubjectByRequestResponsePair() {
        shiroSessionModeInit();

        HttpServletRequest mockRequest = createNiceMock(HttpServletRequest.class);
        HttpServletResponse mockResponse = createNiceMock(HttpServletResponse.class);

        expect(mockRequest.getCookies()).andReturn(null);

        replay(mockRequest);
        replay(mockResponse);

        Subject subject = newSubject(mockRequest, mockResponse);

        verify(mockRequest);
        verify(mockResponse);

        assertNotNull(subject);
        assertTrue(subject.getPrincipals() == null || subject.getPrincipals().isEmpty());
        assertTrue(subject.getSession(false) == null);
        assertFalse(subject.isAuthenticated());
    }

    @Test
    public void testGetSubjectByRequestSessionId() {

        shiroSessionModeInit();

        HttpServletRequest mockRequest = createNiceMock(HttpServletRequest.class);
        HttpServletResponse mockResponse = createNiceMock(HttpServletResponse.class);

        replay(mockRequest);
        replay(mockResponse);

        Subject subject = newSubject(mockRequest, mockResponse);

        Session session = subject.getSession();
        Serializable sessionId = session.getId();

        assertNotNull(sessionId);

        verify(mockRequest);
        verify(mockResponse);

        mockRequest = createNiceMock(HttpServletRequest.class);
        mockResponse = createNiceMock(HttpServletResponse.class);
        //now simulate the cookie going with the request and the Subject should be acquired based on that:
        Cookie[] cookies = new Cookie[]{new Cookie(SecurityHttpSession.DEFAULT_SESSION_ID_NAME, sessionId.toString())};
        expect(mockRequest.getCookies()).andReturn(cookies).anyTimes();
        expect(mockRequest.getParameter(isA(String.class))).andReturn(null).anyTimes();

        replay(mockRequest);
        replay(mockResponse);

        subject = newSubject(mockRequest, mockResponse);

        session = subject.getSession(false);
        assertNotNull(session);
        assertEquals(sessionId, session.getId());

        verify(mockRequest);
        verify(mockResponse);
    }

    /**
     * Asserts fix for <a href="https://issues.apache.org/jira/browse/SHIRO-350">SHIRO-350</a>.
     */
    @Test
    public void testBuildNonWebSubjectWithDefaultServletContainerSessionManager() {

        Ini ini = new Ini();
        Ini.Section section = ini.addSection(IniRealm.USERS_SECTION_NAME);
        section.put("user1", "user1");

        WebIniSecurityManagerFactory factory = new WebIniSecurityManagerFactory(ini);

        WebSecurityManager securityManager = (WebSecurityManager)factory.getInstance();

        PrincipalCollection principals = new SimplePrincipalCollection("user1", "iniRealm");
        Subject subject = new Subject.Builder(securityManager).principals(principals).buildSubject();

        assertNotNull(subject);
        assertEquals("user1", subject.getPrincipal());
    }

}
