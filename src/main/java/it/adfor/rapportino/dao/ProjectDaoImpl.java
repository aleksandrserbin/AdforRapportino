package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Division;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.model.Staff;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author aleksandrserbin
 * <p> Specific Data Access Object for Project Objects </p>
 * <p> Contains all methods of DAO interface and adds some specific</p>
 * @see it.adfor.rapportino.dao.DAO
 * @see it.adfor.rapportino.model.Project
 */

@Repository("projectDao")
public class ProjectDaoImpl extends AbstractDao<Project> implements ProjectDao{
    /**
     * 
     * @param c Client, whose projects should be found
     * @return List of projects according to given restrictions
     */
    public List<Project> findByClient(Client c){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).add(Restrictions.eq("client", c)).
                list();
    }
    
    
    /**
     * 
     * @param pm Project Manager, whose projects should be found
     * @return List of projects according to given restrictions
     */
    public List<Project> findByProjectManager(Staff pm){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("pm",pm)).list();
    }
    
    /**
     * 
     * @param cm Client Manager, whose projects should be found
     * @return List of projects according to given restrictions
     */
     public List<Project> findByClientManager(Staff cm){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("cm",cm)).list();
    }
     
    /**
     * 
     * @param name Name of project to be found
     * @return List of projects according to given restrictions
     */
    public List<Project> findByName(String name){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("name",name)).list();
    }
    
    /**
     * @param d Division, whose projects should be found
     * @return List of projects according to given restrictions
     */
    public List<Project> findByDivision(Division d){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("division",d)).list();
    }
}
