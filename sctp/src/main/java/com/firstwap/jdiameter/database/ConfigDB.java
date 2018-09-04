package com.firstwap.jdiameter.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.sql2o.Sql2o;

import java.sql.SQLException;

@Component
@Profile("server")
public class ConfigDB {
public HikariConfig jdbcConfig;
public Sql2o sql2o;

    public final HikariDataSource dataSource() throws SQLException {
        System.out.println("pooool");
        jdbcConfig = new HikariConfig();
        jdbcConfig.setMaximumPoolSize(100);
        jdbcConfig.setMinimumIdle(2);
        jdbcConfig.setJdbcUrl("jdbc:mysql://localhost:3306/JSMSC");
        jdbcConfig.setUsername("root");
        jdbcConfig.setPassword("root");
        jdbcConfig.setAutoCommit(false);
        return new HikariDataSource(jdbcConfig);
    }

    @Bean
    public final Sql2o sql20(){
        try {
            System.out.println("sql2oPpooool");
            sql2o = new Sql2o(dataSource());
        }catch (SQLException e){
            e.printStackTrace();
        }
      return sql2o;
    }

}
