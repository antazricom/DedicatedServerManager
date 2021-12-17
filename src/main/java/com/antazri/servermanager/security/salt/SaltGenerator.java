package com.antazri.servermanager.security.salt;

import org.apache.commons.lang3.RandomStringUtils;

public class SaltGenerator {

    private static final int n = 0;

    public static void main(String[] args) {
        String random = RandomStringUtils.random(n, true, true);
        System.out.println(random);
    }
}
