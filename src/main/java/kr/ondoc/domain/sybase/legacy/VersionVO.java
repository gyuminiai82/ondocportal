package kr.ondoc.domain.sybase.legacy;

import java.util.ArrayList;
import java.util.List;

public class VersionVO {
	String version = "";
	String pcName = "";
	String ip = "";
	
	List<VersionDataVO> data = new ArrayList<VersionDataVO>();

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPcName() {
		return pcName;
	}

	public void setPcName(String pcName) {
		this.pcName = pcName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<VersionDataVO> getData() {
		return data;
	}

	public void setData(List<VersionDataVO> data) {
		this.data = data;
	}
}