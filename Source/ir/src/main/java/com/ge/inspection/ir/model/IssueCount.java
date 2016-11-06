package com.ge.inspection.ir.model;

public class IssueCount {
  private String type;
  private String count;
  
public IssueCount(String type, String count) {
	super();
	this.type = type;
	this.count = count;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getCount() {
	return count;
}
public void setCount(String count) {
	this.count = count;
}
  
  
}
