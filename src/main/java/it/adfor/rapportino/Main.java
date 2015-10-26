package it.adfor.rapportino;

import it.adfor.rapportino.dao.ProjectDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        AnnotationConfigApplicationContext ctx =new AnnotationConfigApplicationContext(AppConfig.class);
        ProjectDao p = (ProjectDao) ctx.getBean("projectDao");
        System.out.println(p.findById(1417).getName());
    }
}
