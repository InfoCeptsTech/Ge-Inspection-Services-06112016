package com.ge.inspection.ia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {


    @RequestMapping(value="/hello",method = RequestMethod.GET)
    @ResponseBody
    public String sayHello() {
        return "Hello User!";
    }
    
    
    @RequestMapping(value="/getJson",method = RequestMethod.GET)
    @ResponseBody
    public String getJson() {
    	
    	String json="{\"miniPath\":\"/Polymer/temp/0003.jpg\",\"id\":\"1\"}";
        return json;
    }

}
