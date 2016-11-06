package com.ge.inspection.ir.model;

import java.util.Date;

public class DurationModel implements Comparable<DurationModel> {

  private Date dateTime;

  public DurationModel(Date dateTime) {
	super();
	this.dateTime = dateTime;
}

public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date datetime) {
    this.dateTime = datetime;
  }

  @Override
  public int compareTo(DurationModel o) {
    return getDateTime().compareTo(o.getDateTime());
  }
}