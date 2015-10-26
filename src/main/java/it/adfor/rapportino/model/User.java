/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.adfor.rapportino.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author AlexOne
 */
@Entity
@Table(name="Users")
public class User {
    @Id @Column(name = "username", nullable = false)
    private String username;
    private String password;
    private Integer staff_id;

    public void setStaff_id(Integer staff_id) {
        this.staff_id = staff_id;
    }

    public Integer getStaff_id() {
        return staff_id;
    }

    public User() {
    }

    
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
