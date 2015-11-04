/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.model.User;
import org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestMvcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@Configuration
public class RepositoryConfig  extends SpringBootRepositoryRestMvcConfiguration {
    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Activity.class);
        config.exposeIdsFor(Project.class);
    }
}
