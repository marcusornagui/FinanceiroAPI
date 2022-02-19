package br.com.mo.financeiroapi.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@RefreshScope
@Configuration
public class ConfigBD {

    @Bean(name = "bdLocal")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplateLocal")
    public JdbcTemplate jdbcTemplate1(@Qualifier("bdLocal") DataSource ds) throws Exception {

        return new JdbcTemplate(ds);
    }


}
