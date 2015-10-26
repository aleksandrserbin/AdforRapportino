/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.adfor.rapportino.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author AlexOne
 */
@Entity
@Table(name="Divisions")
public class Division {
    @Id @GeneratedValue
    private Integer id;
    private String name;

    public Division(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Division(String name) {
        this.name = name;
    }

    public Division() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
