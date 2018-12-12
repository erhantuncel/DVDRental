package com.erhan.dvdrental.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class AuthenticationUtils {
    public static String encodeSHA1(String password) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        return new BigInteger(1 ,digest).toString(16);
    }
}
