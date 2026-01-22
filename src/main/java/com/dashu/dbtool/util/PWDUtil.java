package com.dashu.dbtool.util;

import org.mindrot.jbcrypt.BCrypt;

public class PWDUtil {
    public static String hash(String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt(6));
    }

    public static boolean verify(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }
}
