package com.example.appender;

/**
 * @author Administrator
 */
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class HikariDBAppender extends AppenderBase<ILoggingEvent> {

    private HikariDataSource dataSource;

    // 必须提供 JDBC 配置
    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName = "com.mysql.cj.jdbc.Driver"; // 可配置

    @Override
    public void start() {
        if (this.jdbcUrl == null || this.username == null) {
            addError("JDBC URL and Username must be set.");
            return;
        }

        // 初始化连接池
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        if (dataSource != null) {
            dataSource.close();
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        String sql = "INSERT INTO app_logs (level, logger, message, thread, log_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getLevel().toString());
            ps.setString(2, event.getLoggerName());
            ps.setString(3, event.getFormattedMessage());
            ps.setString(4, event.getThreadName());
            ps.setTimestamp(5, new Timestamp(event.getTimeStamp()));

            ps.executeUpdate();
        } catch (Exception e) {
            addError("Failed to insert log into database", e);
        }
    }

    // Getters and setters for configuration
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
