package com.dashu.dbtool.service;

import com.dashu.dbtool.model.dto.request.ConnectionConnectReq;
import com.dashu.dbtool.util.DBDriverUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DataSourceManager {
    // 缓存 key = 会话 ID， value = 连接池
    // 30 分组不访问自动过期，触发 removalListener 关闭连接池
    private final Cache<String, HikariDataSource> dataSourceCache = Caffeine.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .maximumSize(100)
            .removalListener((RemovalListener<String, HikariDataSource>) (key, datasource, cause) -> {
                if (datasource != null && !datasource.isClosed()) {
                    // 缓存过期时关闭连接
                    datasource.close();
                    log.info("会话失效，关闭数据源：{}", key);
                }
            })
            .build();

    /**
     * 建立连接 （延迟加载 + 连接池化）
     */
    public String createConnection(ConnectionConnectReq req) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(req.getJdbcUrl());
        hikariConfig.setUsername(req.getUsername());
        hikariConfig.setPassword(req.getPassword());
        hikariConfig.setDriverClassName(DBDriverUtil.detectDriver(req.getJdbcUrl()));


        hikariConfig.setPoolName("DBTool-Pool-" + UUID.randomUUID().toString().substring(0, 8));
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setConnectionTimeout(10000);

        HikariDataSource dataSource = null;

        try {
            dataSource = new HikariDataSource(hikariConfig);
            try(Connection connection = dataSource.getConnection()) {
                String sessionId = UUID.randomUUID().toString();
                dataSourceCache.put(sessionId, dataSource);
                return sessionId;
            }
        } catch (SQLException e) {
            log.error("建立连接失败：{}", e.getMessage());
        }
        return null;
    }
}
