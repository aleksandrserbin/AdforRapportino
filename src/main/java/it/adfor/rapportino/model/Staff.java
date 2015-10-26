/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.adfor.rapportino.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author AlexOne
 */
@Entity
@Table()
public class Staff {
    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String sname;
    private String fiscal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Staff(Integer id, String name, String sname, String fiscal, Company company) {
        this.id = id;
        this.name = name;
        this.sname = sname;
        this.fiscal = fiscal;
        this.company = company;
    }

    public Staff(String name, String sname, String fiscal, Company company) {
        this.name = name;
        this.sname = sname;
        this.fiscal = fiscal;
        this.company = company;
    }

    public Staff() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSname() {
        return sname;
    }

    public String getFiscal() {
        return fiscal;
    }

    public Company getCompany() {
        return company;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Staff{" + "id=" + id + ", name=" + name + ", sname=" + sname+'}';
    }
    
}