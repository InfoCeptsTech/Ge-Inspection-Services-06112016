package com.ge.inspection.ir.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MetadataModel {
  private String altitude;
  private String longitude;
  
public String getAltitude() {
	return altitude;
}
public void setAltitude(String altitude) {
	this.altitude = altitude;
}
public String getLongitude() {
	return longitude;
}
public void setLongitude(String longitude) {
	this.longitude = longitude;
}
public MetadataModel(String altitude, String longitude) {
	this.altitude = altitude;
	this.longitude = longitude;
}
  
  
}
