package it.adfor.rapportino;

import it.adfor.rapportino.dao.GenericDao;
import it.adfor.rapportino.dao.ProjectDaoImpl;
import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.security.config.SecurityConfig;
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "it.adfor")
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:properties/properties.properties")
public class Main {
    public static void main(String[] args){
        SpringApplication.run(new Object[]{Main.class, RepositoryConfig.class, AppConfig.class, SecurityConfig.class}, args);
        
    }
}
