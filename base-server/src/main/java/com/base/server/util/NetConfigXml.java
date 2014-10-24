package com.base.server.util;

/**
 * 服务器配置
 * 
 * @author xi.mu
 * 
 */
public class NetConfigXml {
	private int id;
	private String name;
	private String address;
	private int port;
	private int adminPort;
	private String user;
	private String password;

	public NetConfigXml(int id, String name, String address, int port, int adminPort, String user, String password) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.port = port;
		this.adminPort = adminPort;
		this.user = user;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public int getAdminPort() {
		return adminPort;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("NetConfigXml:");
		sb.append("id=").append(id).append("|");
		sb.append("address=").append(address).append("|");
		sb.append("port=").append(port).append("|");
		sb.append("adminPort=").append(adminPort).append("|");
		return sb.toString();
	}

}
