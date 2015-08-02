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
package com.ribas.andrei.weblogic.serverproperties.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
public class ServerProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3868149335155686352L;

	private final String name;

	private final String defaultDomain;

	private final String[] domains;

	private final Collection<ServerPropertiesInfo> serverPropertiesInfoCollection;

	public ServerProperties(String name, String defaultDomain,
			String[] domains,
			Collection<ServerPropertiesInfo> serverPropertiesInfoCollection) {
		super();
		this.name = name;
		this.defaultDomain = defaultDomain;
		this.domains = domains;
		this.serverPropertiesInfoCollection = serverPropertiesInfoCollection;
	}

	public String getName() {
		return name;
	}

	public String getDefaultDomain() {
		return defaultDomain;
	}

	public String[] getDomains() {
		return domains;
	}

	public Collection<ServerPropertiesInfo> getServerPropertiesInfoCollection() {
		return serverPropertiesInfoCollection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((defaultDomain == null) ? 0 : defaultDomain.hashCode());
		result = prime * result + Arrays.hashCode(domains);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((serverPropertiesInfoCollection == null) ? 0
						: serverPropertiesInfoCollection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerProperties other = (ServerProperties) obj;
		if (defaultDomain == null) {
			if (other.defaultDomain != null)
				return false;
		} else if (!defaultDomain.equals(other.defaultDomain))
			return false;
		if (!Arrays.equals(domains, other.domains))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serverPropertiesInfoCollection == null) {
			if (other.serverPropertiesInfoCollection != null)
				return false;
		} else if (!serverPropertiesInfoCollection
				.equals(other.serverPropertiesInfoCollection))
			return false;
		return true;
	}

}
