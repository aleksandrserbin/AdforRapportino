package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Division;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.model.Staff;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alex
 */
public class ProjectDao extends AbstractDao{
    
    public List<Project> findByClient(Client c){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).add(Restrictions.eq("client_id", c.getId())).
                list();
    }
    
    
    public List<Project> findByDate(Date begin, Date end){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.or(Restrictions.ge("begin_date", begin),
                        Restrictions.le("end_date", end))).list();
    }
    
    public List<Project> findByProjectManager(Staff pm){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("pm_id",pm.getId())).list();
    }
    
     public List<Project> findByClientManager(Staff cm){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("cm_id",cm.getId())).list();
    }
    
    public List<Project> findByName(String name){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("name",name)).list();
    }
    
    public List<Project> findByDivision(Division d){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("division_id",d.getId())).list();
    }
}
