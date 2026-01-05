package com.dashu.dbtool.util;

public class DBDriverUtil {
    public static String detectDriver(String jdbcUrl) {
        if (jdbcUrl  == null) {
            throw new IllegalArgumentException("数据库连接为空");
        }
        if (jdbcUrl.startsWith("jdbc:mysql")) {
            return "com.mysql.cj.jdbc.Driver";
        }
        if (jdbcUrl.startsWith("jdbc:postgresql:")) {
            return "org.postgresql.Driver";
        }
        if (jdbcUrl.startsWith("jdbc:oracle")) {
            return "oracle.jdbc.OracleDriver";
        }
        if (jdbcUrl.startsWith("jdbc:sqlserver")) {
            return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }
        throw new IllegalArgumentException("不支持的数据库类型: " + jdbcUrl);

    }
}
