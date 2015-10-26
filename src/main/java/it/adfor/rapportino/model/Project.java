/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;

/**
 *
 * @author AlexOne
 */
@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue
    private Integer id;
    private String old_division;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;
    private String name;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "committente_id", nullable = false)
    private Client committente;
    private String area;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_id", nullable = false)
    private Staff pm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cm_id", nullable = false)
    private Staff cm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rba_id", nullable = false)
    private Staff rba;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resp_id", nullable = false)
    private Staff resp;
    @Temporal(DATE)
    private Date begin;
    @Temporal(DATE)
    private Date end;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    public Project(Division division, Type type, String name, String status, Client client, Client committente, String area, Staff pm, Staff cm, Staff rba, Staff resp, Date begin, Date end, String old_division) {
        this.division = division;
        this.type = type;
        this.name = name;
        this.status = status;
        this.client = client;
        this.committente = committente;
        this.area = area;
        this.pm = pm;
        this.cm = cm;
        this.rba = rba;
        this.resp = resp;
        this.begin = begin;
        this.end = end;
        this.old_division = old_division;
    }

    public Project() {
    }

    public Integer getId() {
        return id;
    }

    public Division getDivision() {
        return division;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public Client getCommittente() {
        return committente;
    }

    public String getArea() {
        return area;
    }

    public Staff getPm() {
        return pm;
    }

    public Staff getCm() {
        return cm;
    }

    public Staff getRba() {
        return rba;
    }

    public Staff getResp() {
        return resp;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public String getOld_division() {
        return old_division;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCommittente(Client committente) {
        this.committente = committente;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setPm(Staff pm) {
        this.pm = pm;
    }

    public void setCm(Staff cm) {
        this.cm = cm;
    }

    public void setRba(Staff rba) {
        this.rba = rba;
    }

    public void setResp(Staff resp) {
        this.resp = resp;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setOld_division(String old_division) {
        this.old_division = old_division;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", old_division=" + old_division + ", type=" + type + ", name=" + name + ", status=" + status + ", client=" + client + ", committente=" + committente + ", area=" + area + ", pm=" + pm + ", cm=" + cm + ", rba=" + rba + ", resp=" + resp + ", begin=" + begin + ", end=" + end + ", division=" + division + '}';
    }
    
    
}
