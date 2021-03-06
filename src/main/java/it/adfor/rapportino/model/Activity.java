package it.adfor.rapportino.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonAutoDetect
public class Activity implements Serializable{
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
    private Integer typeId;
    private Boolean submitted;

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
        this.typeId = type_id;
    }

    public Activity() {
        this.empl = null;
        this.proj = null;
        this.date = null;
        this.description = null;//
        this.place = null;//
        this.note = null;//
        this.hours = null;//
        this.typeId=null;//
    }

    public Integer getId() {
        return id;
    }

    public Staff getEmpl() {
        return empl;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Project getProj() {
        return proj;
    }

    public Boolean getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
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

    public Integer getTypeId() {
        return typeId;
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
        return "Activity{" + "id=" + id + ", empl=" + empl + ", proj=" + proj + ", date=" + date + ", description=" + description + ", place=" + place + ", note=" + note + ", hours=" + hours + ", type_id=" + typeId + '}';
    }
    
    
    
}
