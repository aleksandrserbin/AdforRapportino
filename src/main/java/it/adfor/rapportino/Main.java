package it.adfor.rapportino;

import it.adfor.rapportino.dao.GenericDao;
import it.adfor.rapportino.dao.ProjectDaoImpl;
import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Project;
import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
@ComponentScan
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
@PropertySource("properties/properties.properties")
public class Main {
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);	
    }
}
