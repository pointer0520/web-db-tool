# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Web-based Database Management Tool (web-db-tool) - 一个基于 Spring Boot 3.2.5 的多数据库连接池管理工具。

核心功能:为每个用户请求动态创建独立的数据库连接池(HikariCP),通过 Caffeine 缓存会话,支持 MySQL/PostgreSQL/Oracle/SQL Server 四种数据库。

技术栈:Spring Boot 3.2.5 + HikariCP + Caffeine Cache + Log4j2 + JSqlParser + Knife4j

## Build & Run Commands

### 构建
```bash
mvn clean package
```

### 运行
```bash
mvn spring-boot:run
# 或直接运行打包后的 JAR
java -jar target/web-db-tool-1.0-SNAPSHOT.jar
```

### 测试
```bash
mvn test
```

项目当前无测试用例,需要补充。

## Architecture Overview

### 核心设计模式

**会话隔离架构**
- `DataSourceManager` (service/DataSourceManager.java):使用 Caffeine Cache 管理数据库连接池
  - key: UUID sessionId
  - value: HikariDataSource
  - 30分钟不访问自动过期,触发 removalListener 关闭连接池
  - 最大100个并发连接池

**数据库驱动检测**
- `DBDriverUtil` (util/DBDriverUtil.java):根据 JDBC URL 自动检测数据库类型并返回对应驱动类名

**统一响应封装**
- `CommonResult<T>` (common/response/CommonResult.java):泛型响应类,支持成功/失败多种工厂方法
- `ResponseCode`:响应码枚举

**日志追踪**
- `TraceIDFilter`:为每个请求生成唯一 traceId
- `LoggingAspect`:AOP 切面记录 Controller 层方法调用、参数、执行时间

### 包结构

```
com.dashu.dbtool/
├── aspect/          # AOP 切面
├── common/          # 通用工具类、响应封装
│   ├── response/    # 统一响应结构
│   └── utils/       # 日期时间工具
├── controller/      # REST 控制器
├── exception/       # 异常定义和全局处理器
├── model/           # 数据模型
│   ├── dto/         # 数据传输对象
│   └── entity/      # 实体类
├── service/         # 核心业务逻辑
└── util/            # 工具类(驱动检测等)
```

### 关键依赖说明

- **HikariCP**:数据库连接池,每个 sessionId 独立一个池(poolName 格式: `DBTool-Pool-{uuid8}`)
- **Caffeine**:本地缓存,管理会话生命周期
- **JSqlParser**:SQL 解析器,用于识别 SQL 类型(未来用于安全检查)
- **Knife4j 4.5.0**:Swagger 增强版,生成 API 文档
- **Log4j2**:日志框架(已排除默认的 Logback)

### 配置文件

- `application.yml`:主配置,激活 dev profile,端口 8080
- `application-dev.yml`:开发环境配置
- `log4j2-spring.xml`:日志配置

## Development Notes

### 日志规范
- 所有组件使用 Lombok 的 `@Slf4j` 注解
- Controller 层通过 AOP 自动记录方法调用和性能指标
- 关键操作需手动记录 INFO/WARN/ERROR 级别日志

### 异常处理
- `BusinessException`:业务异常基类
- `GlobalExceptionHandler`:全局异常拦截,统一返回 `CommonResult.error()`
- 不在代码中吞掉异常,必须记录错误上下文

### 响应规范
所有 Controller 方法返回 `CommonResult<T>`:
- 成功: `CommonResult.success(data)` 或 `CommonResult.success(message, data)`
- 失败: `CommonResult.error(message)` 或 `CommonResult.error(ResponseCode, message)`

### 数据库连接管理
- 连接池配置(最小空闲1,最大5,连接超时10s)
- 新建连接后立即测试连接(try-with-resources)
- sessionId 作为会话令牌返回给前端,后续请求需携带
- 缓存过期自动关闭连接池,无需手动释放

### 安全注意事项
- 当前版本未对 SQL 注入做防护(计划用 JSqlParser 解析检查)
- 密码等敏感信息不应记录到日志中
- 考虑添加会话鉴权机制

## Current State & TODO

- [x] 基础框架搭建(Spring Boot + Log4j2)
- [x] 多数据库驱动支持
- [x] 连接池动态创建与管理
- [ ] 完整的 Controller 接口实现(当前仅有个 TestController)
- [ ] SQL 执行与结果集查询功能
- [ ] JSqlParser 集成做 SQL 类型检查和安全过滤
- [ ] WebSocket 实时日志推送
- [ ] 单元测试覆盖
- [ ] 前端页面集成
