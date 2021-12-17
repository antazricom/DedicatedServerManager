package com.antazri.servermanager.security.salt;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SaltGenerator {

    public static void main(String[] args) {
        String encodedSalt = BCrypt.gensalt(12);
        System.out.println(encodedSalt);
        System.out.println(BCrypt.hashpw("", encodedSalt));
    }
}
