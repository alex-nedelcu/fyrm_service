package com.fyrm.fyrm_service.integration_test_setup;

import static org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.ContainerLessJdbcDelegate;
import org.testcontainers.utility.DockerImageName;

public class PostgreSQLInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(
      DockerImageName.parse("postgres:14.6").asCompatibleSubstituteFor("postgres")
  ).withUsername("postgres").withStartupAttempts(3);

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    if (!POSTGRESQL_CONTAINER.isCreated()) {
      POSTGRESQL_CONTAINER.start();
      ScriptUtils.runInitScript(
          new ContainerLessJdbcDelegate(getConnection()),
          "scripts/init_database.sql"
      );
      Runtime.getRuntime().addShutdownHook(new Thread(POSTGRESQL_CONTAINER::stop));
    }

    TestPropertyValues values = TestPropertyValues.of(
        "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
        "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
        "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword()
    );

    values.applyTo(applicationContext);
  }

  private Connection getConnection() {
    try {
      Properties connectionProps = new Properties();
      connectionProps.put("user", "postgres");
      connectionProps.put("password", "test");

      String jdbcUrl = "jdbc:postgresql://"
          + POSTGRESQL_CONTAINER.getHost()
          + ":"
          + POSTGRESQL_CONTAINER.getMappedPort(POSTGRESQL_PORT)
          + "/";

      return DriverManager.getConnection(jdbcUrl, connectionProps);
    } catch (SQLException error) {
      throw new RuntimeException(error);
    }
  }
}
