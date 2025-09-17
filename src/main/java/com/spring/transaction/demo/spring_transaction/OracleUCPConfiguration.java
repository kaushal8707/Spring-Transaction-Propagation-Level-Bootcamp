package com.spring.transaction.demo.spring_transaction;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

@Profile("!test")
@Configuration
@ConfigurationProperties(prefix ="spring.datasource.oracleucp" )
@Data
public class OracleUCPConfiguration {

    private String connectionPoolName;
    private int initialPoolSize;
    private int maxPoolSize;
    private int minPoolSize;
    private int connectionWaitTimeout;
    private int inactiveConnectionTimeout;
    private int abandonedConnectionTimeout;
    private int maxConnectionReuseTime;
    private int timeToLiveConnectionTimeout;
    private String connectionFactoryClassName;
    private boolean fastConnectionFailoverEnabled;
    private boolean validateConnectionOnBorrow;
    private int queryTimeout;
    private int maxStatements;
    private Properties connectionProperties;
}