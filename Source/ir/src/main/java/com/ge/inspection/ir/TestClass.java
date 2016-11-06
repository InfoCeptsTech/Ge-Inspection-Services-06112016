package com.ge.inspection.ir;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class TestClass {
  public static void main(String args[]) throws ParseException{
	  System.out.println("in test");
	  
	  SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy",Locale.ENGLISH);
	  formatter.setLenient(false);
	  System.out.println(formatter.parse("Oct 18 2016"));
  }
}
