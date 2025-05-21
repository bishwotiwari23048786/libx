package com.Libx.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/pages/*"})
public class AuthenticationFilter implements Filter {
	private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Authentication Filter initialized");	
	}

	@Override
	public void destroy() {
		logger.info("Authentication Filter destroyed");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {

	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;

	    String uri = req.getRequestURI();
	    HttpSession session = req.getSession(false);

	    boolean loggedIn = session != null && session.getAttribute("user") != null;
	    boolean isAuthPage = uri.endsWith("Login.jsp") || uri.endsWith("LoginController") ||
	                         uri.endsWith("Registration.jsp") || uri.endsWith("RegisterController");

	    if (loggedIn || isAuthPage) {
	        chain.doFilter(request, response); // Allow access
	    } else {
	        res.sendRedirect(req.getContextPath() + "/pages/user/Login.jsp"); // Redirect to login
	    }
	}

}
