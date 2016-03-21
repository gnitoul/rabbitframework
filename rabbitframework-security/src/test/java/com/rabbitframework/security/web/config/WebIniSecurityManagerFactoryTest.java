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
package com.rabbitframework.security.web.config;

import org.junit.Test;

import com.rabbitframework.security.config.Ini;
import com.rabbitframework.security.config.IniSecurityManagerFactory;
import com.rabbitframework.security.web.config.WebIniSecurityManagerFactory;
import com.rabbitframework.security.web.filter.mgt.DefaultFilter;
import com.rabbitframework.security.web.mgt.DefaultWebSecurityManager;

import javax.servlet.Filter;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * TODO - Class JavaDoc
 *
 * @since May 8, 2010 4:21:10 PM
 */
public class WebIniSecurityManagerFactoryTest {


    /**
     * Test that ensures the WebIniSecurityManagerFactory will automatically add the default
     * filters to the pool of beans before the INI configuration is interpreted.
     */
    @Test
    public void testDefaultFiltersPresent() {
        Ini ini = new Ini();
        //just a normal configuration line in the MAIN section for any of the default filtes should work
        //out of the box.  So, create the main section and just config one of them:
        Ini.Section section = ini.addSection(IniSecurityManagerFactory.MAIN_SECTION_NAME);
        section.put("authc.loginUrl", "/login.jsp");

        WebIniSecurityManagerFactory factory = new WebIniSecurityManagerFactory(ini);
        com.rabbitframework.security.mgt.SecurityManager sm = factory.getInstance();
        assertNotNull(sm);
        assertTrue(sm instanceof DefaultWebSecurityManager);

        //now assert that all of the default filters exist:
        Map<String, ?> beans = factory.getBeans();
        for (DefaultFilter defaultFilter : DefaultFilter.values()) {
            Filter filter = (Filter) beans.get(defaultFilter.name());
            assertNotNull(filter);
            assertTrue(defaultFilter.getFilterClass().isAssignableFrom(filter.getClass()));
        }
    }
}
