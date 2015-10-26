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

@Entity
@Table(name="Companies")
public class Company {
    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String fiscal;

    public Company(Integer id, String name, String fiscal) {
        this.id = id;
        this.name = name;
        this.fiscal = fiscal;
    }

    public Company(String name, String fiscal) {
        this.name = name;
        this.fiscal = fiscal;
    }

    public Company() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }
    
    
    
    
}
