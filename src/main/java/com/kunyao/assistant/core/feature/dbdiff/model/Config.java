package com.kunyao.assistant.core.feature.dbdiff.model;

public class Config {
	
	public String localMysql;
	public String localDbName;
	public String localUsername;
	public String localPassword;
	
	public String remoteMysql;
	public String remoteDbName;
	public String remoteUsername;
	public String remotePassword;
	
	public Boolean isHidenSameValue;
	
	public Config() {
		
	}
	
	public Config(String localMysql, String localDbName, String localUsername, String localPassword,
			String remoteMysql, String remoteDbName, String remoteUsername, String remotePassword,
			Boolean isHidenSameValue) {
		this.localMysql = localMysql;
		this.localDbName = localDbName;
		this.localUsername = localUsername;
		this.localPassword = localPassword;
		this.remoteMysql = remoteMysql;
		this.remoteDbName = remoteDbName;
		this.remoteUsername = remoteUsername;
		this.remotePassword = remotePassword;
		this.isHidenSameValue = isHidenSameValue;
	}

	public String getLocalMysql() {
		return localMysql;
	}

	public void setLocalMysql(String localMysql) {
		this.localMysql = localMysql;
	}

	public String getLocalDbName() {
		return localDbName;
	}

	public void setLocalDbName(String localDbName) {
		this.localDbName = localDbName;
	}

	public String getLocalUsername() {
		return localUsername;
	}

	public void setLocalUsername(String localUsername) {
		this.localUsername = localUsername;
	}

	public String getLocalPassword() {
		return localPassword;
	}

	public void setLocalPassword(String localPassword) {
		this.localPassword = localPassword;
	}

	public String getRemoteMysql() {
		return remoteMysql;
	}

	public void setRemoteMysql(String remoteMysql) {
		this.remoteMysql = remoteMysql;
	}

	public String getRemoteDbName() {
		return remoteDbName;
	}

	public void setRemoteDbName(String remoteDbName) {
		this.remoteDbName = remoteDbName;
	}

	public String getRemoteUsername() {
		return remoteUsername;
	}

	public void setRemoteUsername(String remoteUsername) {
		this.remoteUsername = remoteUsername;
	}

	public String getRemotePassword() {
		return remotePassword;
	}

	public void setRemotePassword(String remotePassword) {
		this.remotePassword = remotePassword;
	}

	public Boolean getIsHidenSameValue() {
		return isHidenSameValue;
	}

	public void setIsHidenSameValue(Boolean isHidenSameValue) {
		this.isHidenSameValue = isHidenSameValue;
	}

	@Override
	public String toString() {
		return "Config [localMysql=" + localMysql + ", localDbName=" + localDbName + ", localUsername=" + localUsername
				+ ", localPassword=" + localPassword + ", remoteMysql=" + remoteMysql + ", remoteDbName=" + remoteDbName
				+ ", remoteUsername=" + remoteUsername + ", remotePassword=" + remotePassword + ", isHidenSameValue="
				+ isHidenSameValue + "]";
	}
	
}
