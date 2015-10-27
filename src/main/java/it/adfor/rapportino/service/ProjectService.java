package it.adfor.rapportino.service;

import it.adfor.rapportino.dao.ProjectDao;
import it.adfor.rapportino.dao.ProjectDaoImpl;
import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Project;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("projectService")
@Transactional
public class ProjectService {

    @Autowired
    ProjectDao projectDao;

    public List<Project> findByClient(Client c) {
        return projectDao.findByClient(c);
    }
}
