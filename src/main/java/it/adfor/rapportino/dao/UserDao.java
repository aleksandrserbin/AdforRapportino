/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.User;

public interface UserDao {
    public boolean checkIfExists(String username, String pass);
    public User getByUsername(String username);
}
