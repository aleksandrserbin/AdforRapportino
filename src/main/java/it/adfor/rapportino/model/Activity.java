/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.adfor.rapportino.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;


@Entity
@Table(name="Activity")
public class Activity {
    @Id @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empl_id", nullable = false)
    private Staff empl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proj_id", nullable = false)
    private Project proj;
    @Temporal(DATE)
    private Date date;
    private String description;
    private String place;
    private String note;
    private Integer hours;
    @Column(name="type_id")
    private Integer type_id;

    public Activity(Staff empl, Project proj, Date date, String description, String place, String note, Integer hours) {
        this.empl = empl;
        this.proj = proj;
        this.date = date;
        this.description = description;
        this.place = place;
        this.note = note;
        this.hours = hours;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Activity() {
        this.empl = null;
        this.proj = null;
        this.date = null;
        this.description = null;//
        this.place = null;//
        this.note = null;//
        this.hours = null;//
        this.type_id=null;//
    }

    public Integer getId() {
        return id;
    }

    public Staff getEmpl() {
        return empl;
    }

    public Project getProj() {
        return proj;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public String getNote() {
        return note;
    }

    public Integer getHours() {
        return hours;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmpl(Staff empl) {
        this.empl = empl;
    }

    public void setProj(Project proj) {
        this.proj = proj;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + id + ", empl=" + empl + ", proj=" + proj + ", date=" + date + ", description=" + description + ", place=" + place + ", note=" + note + ", hours=" + hours + ", type_id=" + type_id + '}';
    }
    
    
    
}
