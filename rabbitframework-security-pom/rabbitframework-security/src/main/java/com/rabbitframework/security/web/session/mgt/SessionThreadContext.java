package com.rabbitframework.security.web.session.mgt;

import org.apache.shiro.session.Session;

public class SessionThreadContext {
	private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	public static String SESSION_KEY = SessionThreadContext.class + "_SESSION_KEY";

	public static void set(Session session) {
		threadLocal.set(session);
	}

	public static Session get() {
		return threadLocal.get();
	}

	public static void remove() {
		try {
			threadLocal.set(null);
			threadLocal.remove();
		} catch (Exception e) {

		}
	}
}
