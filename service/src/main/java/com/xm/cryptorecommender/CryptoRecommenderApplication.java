package com.xm.cryptorecommender;

import liquibase.Liquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.support.DatabaseStartupValidator;

import javax.sql.DataSource;
import java.util.stream.Stream;

@SpringBootApplication
@EnableCaching
public class CryptoRecommenderApplication {

   @Value(value="${database.startup.timeout}")
   private int databaseStartupTimeout;
   @Value(value="${database.startup.interval}")
   private int databaseStartupInterval;

   @Bean
   public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource) {
      var validator = new DatabaseStartupValidator();
      validator.setDataSource(dataSource);
      validator.setTimeout(databaseStartupTimeout);
      validator.setInterval(databaseStartupInterval);
      return validator;
   }

   @Bean
   public static BeanFactoryPostProcessor dependsOnPostProcessor() {
      return bf -> {
         String[] liquibase = bf.getBeanNamesForType(Liquibase.class);
         Stream.of(liquibase)
                 .map(bf::getBeanDefinition)
                 .forEach(it -> it.setDependsOn("databaseStartupValidator"));
      };
   }

   public static void main(String[] args) {
      SpringApplication.run(CryptoRecommenderApplication.class, args);
   }

}
