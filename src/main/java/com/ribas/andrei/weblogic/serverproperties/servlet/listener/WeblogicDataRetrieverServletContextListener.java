/* 
The MIT License (MIT)
Copyright (c) 2015 Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package com.ribas.andrei.weblogic.serverproperties.servlet.listener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.ribas.andrei.weblogic.serverproperties.model.ServerProperties;
import com.ribas.andrei.weblogic.serverproperties.model.ServerPropertiesInfo;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@WebListener
public class WeblogicDataRetrieverServletContextListener implements
		ServletContextListener {

	private static final Logger LOGGER = Logger
			.getLogger(WeblogicDataRetrieverServletContextListener.class);

	private static final Set<String> NOT_ALLOWED_ATTRIBUTES = new HashSet<>();

	static {
		NOT_ALLOWED_ATTRIBUTES.add("JavaStandardTrustKeyStorePassPhrase");
		NOT_ALLOWED_ATTRIBUTES.add("CustomIdentityKeyStorePassPhrase");
		NOT_ALLOWED_ATTRIBUTES.add("CustomTrustKeyStorePassPhrase");
		NOT_ALLOWED_ATTRIBUTES.add("DefaultIIOPPassword");
		NOT_ALLOWED_ATTRIBUTES.add("DefaultTGIOPPassword");

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ServletContextListener started");
		}

		try {

			InitialContext ctx = new InitialContext();

			MBeanServer mbeanServer = (MBeanServer) ctx
					.lookup("java:comp/env/jmx/runtime");
			// "java:comp/env/jmx/domainRuntime"

			String[] domains = mbeanServer.getDomains();

			String defaultDomain = mbeanServer.getDefaultDomain();

			String serverName = System.getProperty("weblogic.Name");

			Collection<ServerPropertiesInfo> serverPropertiesInfoCollection = this
					.getAllServerProperties(serverName, mbeanServer);

			ServerProperties serverProperties = new ServerProperties(
					serverName, defaultDomain, domains,
					serverPropertiesInfoCollection);

			servletContextEvent.getServletContext().setAttribute(
					"serverProperties", serverProperties);

		} catch (MalformedObjectNameException | NamingException
				| AttributeNotFoundException | InstanceNotFoundException
				| MBeanException | ReflectionException | RuntimeException
				| IntrospectionException e) {

			LOGGER.error("Could not get the server details.", e);

			String exceptionStackTrace = this
					.getFormattedExceptionsStackTrace(e);

			servletContextEvent.getServletContext().setAttribute(
					"exceptionStackTrace", exceptionStackTrace);

		}

	}

	private Collection<ServerPropertiesInfo> getAllServerProperties(
			String serverName, MBeanServer mbeanServer)
			throws MalformedObjectNameException, IntrospectionException,
			InstanceNotFoundException, ReflectionException,
			AttributeNotFoundException, MBeanException {

		Collection<ServerPropertiesInfo> serverPropertiesInfoCollection = new ArrayList<>();

		ObjectName objName = new ObjectName("com.bea:Name=" + serverName
				+ ",Type=Server");

		MBeanAttributeInfo[] beanInfoAttributesArray = mbeanServer
				.getMBeanInfo(objName).getAttributes();

		for (MBeanAttributeInfo info : beanInfoAttributesArray) {

			Object attributeValue = "not allowed to show";

			if (isAttributeAllowedToShow(info.getName())) {

				attributeValue = mbeanServer.getAttribute(objName,
						info.getName());

				if (attributeValue == null) {
					attributeValue = "[null]";
				}

			}

			serverPropertiesInfoCollection.add(new ServerPropertiesInfo(info
					.getName(), info.getType(), attributeValue, info
					.getDescription()));

		}

		return serverPropertiesInfoCollection;

	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ServletContextListener destroyed");
		}
	}

	private boolean isAttributeAllowedToShow(String attributeName) {

		boolean isAttributeAllowedToShow = true;

		for (String deniedAttributeName : NOT_ALLOWED_ATTRIBUTES) {
			if (attributeName.equals(deniedAttributeName)) {

				isAttributeAllowedToShow = false;

				break;

			}
		}

		return isAttributeAllowedToShow;

	}

	private String getFormattedExceptionsStackTrace(Exception e) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return sw.toString();

	}

}
