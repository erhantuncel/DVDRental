package com.erhan.dvdrental.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class TestEncodeSHA1 {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pass = "12345";
        String encodedPass = AuthenticationUtils.encodeSHA1(pass);
        System.out.println("Password = " + pass);
        System.out.println("Encoded Password = " + encodedPass);
        
        String pass2 = "abcdef12";
        String encodedPass2 = AuthenticationUtils.encodeSHA1(pass2);
        System.out.println("Password = " + pass2);
        System.out.println("Encoded Password = " + encodedPass2);
    }
}
