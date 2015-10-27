package it.adfor.rapportino;

import it.adfor.rapportino.dao.GenericDao;
import it.adfor.rapportino.dao.ProjectDaoImpl;
import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.service.ProjectService;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        AnnotationConfigApplicationContext ctx =new AnnotationConfigApplicationContext(AppConfig.class);
        Client c = new Client();
        c.setId(1071);
        ProjectService p = (ProjectService) ctx.getBean("projectService");
        Project pj = p.findByClient(c).get(0);
        System.out.println(pj.getName());
    }
}
