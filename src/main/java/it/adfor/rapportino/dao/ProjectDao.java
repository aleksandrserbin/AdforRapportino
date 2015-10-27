/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Division;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.model.Staff;
import java.util.List;


public interface ProjectDao {
     public List<Project> findByClient(Client c);
     public List<Project> findByProjectManager(Staff pm);
     public List<Project> findByClientManager(Staff cm);
     public List<Project> findByName(String name);
     public List<Project> findByDivision(Division d);
}
