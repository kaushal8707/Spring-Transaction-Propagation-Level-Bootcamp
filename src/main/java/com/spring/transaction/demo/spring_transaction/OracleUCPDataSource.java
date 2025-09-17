package com.spring.transaction.demo.spring_transaction;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@Profile("!test")
@Configuration
@EnableTransactionManagement(order = 100)
public class OracleUCPDataSource {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean(name = "dataSource")
    public DataSource dataSource(OracleUCPConfiguration oracleUCPConfiguration) throws SQLException {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();

        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setConnectionFactoryClassName(oracleUCPConfiguration.getConnectionFactoryClassName());
        dataSource.setURL(url);
        dataSource.setConnectionPoolName(oracleUCPConfiguration.getConnectionPoolName());
        dataSource.setInitialPoolSize(oracleUCPConfiguration.getInitialPoolSize());
        dataSource.setMinPoolSize(oracleUCPConfiguration.getMinPoolSize());
        dataSource.setMaxPoolSize(oracleUCPConfiguration.getMaxPoolSize());
        dataSource.setConnectionWaitTimeout(oracleUCPConfiguration.getConnectionWaitTimeout());
        dataSource.setInactiveConnectionTimeout(oracleUCPConfiguration.getInactiveConnectionTimeout());
        dataSource.setAbandonedConnectionTimeout(oracleUCPConfiguration.getAbandonedConnectionTimeout());
        dataSource.setMaxConnectionReuseTime(oracleUCPConfiguration.getMaxConnectionReuseTime());
        dataSource.setFastConnectionFailoverEnabled(oracleUCPConfiguration.isFastConnectionFailoverEnabled());
        dataSource.setQueryTimeout(oracleUCPConfiguration.getQueryTimeout());
        dataSource.setConnectionProperties(oracleUCPConfiguration.getConnectionProperties());
        dataSource.setTimeToLiveConnectionTimeout(oracleUCPConfiguration.getTimeToLiveConnectionTimeout());
        dataSource.setValidateConnectionOnBorrow(oracleUCPConfiguration.isValidateConnectionOnBorrow());
        dataSource.setMaxStatements(oracleUCPConfiguration.getMaxStatements());

        return dataSource;
    }
}