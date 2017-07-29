package com.rabbitframework.web.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class RabbitServletContainer extends ServletContainer {
	private static final long serialVersionUID = 1L;

	/**
	 * Create Jersey Servlet container.
	 */
	public RabbitServletContainer() {
		super();
	}

	/**
	 * Create Jersey Servlet container.
	 *
	 * @param resourceConfig
	 *            container configuration.
	 */
	public RabbitServletContainer(final ResourceConfig resourceConfig) {
		super(resourceConfig);
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(request, response, chain);
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		super.service(req, res);
	}
}
