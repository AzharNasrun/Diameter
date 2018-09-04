package com.firstwap.jdiameter.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.vavr.Tuple2;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.sql.SQLException;


public class ResponseDaoTest {

    @Test
    public void get_response_should_valid() throws SQLException {
        final HikariConfig jdbcConfig = new HikariConfig();
        jdbcConfig.setMaximumPoolSize(200);
        jdbcConfig.setMinimumIdle(2);
        jdbcConfig.setJdbcUrl("jdbc:mysql://10.32.9.164:3306/JSMSC");
        jdbcConfig.setUsername("root");
        jdbcConfig.setPassword("Ganteng#123");

        final HikariDataSource dataSource = new HikariDataSource(jdbcConfig);
    //    final Tuple2<String, Integer> response = new ResponseDao().getResponse("6285223965145");
 //       Assertions.assertThat(response._1).isNotEmpty();
   //     Assertions.assertThat(response._2).isEqualTo(1000);
    }
}
