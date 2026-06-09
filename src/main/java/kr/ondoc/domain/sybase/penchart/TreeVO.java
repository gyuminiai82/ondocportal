package kr.ondoc.domain.sybase.penchart;

import java.util.ArrayList;
import java.util.List;

public class TreeVO {
	String id = "";
	String type = "";
	String text = "";
	TreeStateVO state = null;
	List<TreeVO> children = new ArrayList<TreeVO>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public TreeStateVO getState() {
		return state;
	}
	public void setState(TreeStateVO state) {
		this.state = state;
	}
	public List<TreeVO> getChildren() {
		return children;
	}
	public void setChildren(List<TreeVO> children) {
		this.children = children;
	} 
}


