/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.adfor.rapportino.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name="users")
public class User {
    
    @Id @Column(name = "username", nullable = false)
    @JsonProperty("username")
    private String username;
    @JsonIgnore
    private String password;
    private Integer staffId;
    private short scope;

    public short getScope() {
        return scope;
    }

    public void setScope(short scope) {
        this.scope = scope;
    }
    
    public void setStaffId(Integer staff_id) {
        this.staffId = staff_id;
    }

    public Integer getStaffId() {
        return staffId;
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
