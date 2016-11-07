package com.ge.inspection.ia.security;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

public class EncodeString64 {

    public static void main(String[] args) throws UnsupportedEncodingException {

      //  String str = "sgf5q!$wQklMXt6$";
        String str="admin";
    	// encode data using BASE64
        String encoded = DatatypeConverter.printBase64Binary(str.getBytes());
        System.out.println("encoded value is \t" + encoded);

        // Decode data 
        String decoded = new String(DatatypeConverter.parseBase64Binary(encoded));
        System.out.println("decoded value is \t" + decoded);

        System.out.println("original value is \t" + str);

    }

}