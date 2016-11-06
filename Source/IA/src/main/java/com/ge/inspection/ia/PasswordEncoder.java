package com.ge.inspection.ia;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordEncoder {

	public static void main(String args[]){
		StandardPasswordEncoder spe=new StandardPasswordEncoder();
		System.out.println(spe.encode("08tgR2@hytb"));
	}
}
