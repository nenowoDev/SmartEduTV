package com.featureforce.smartedutv.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "program")
public class ManageProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "programName", nullable = false)
    private String programName;

    @Column(name = "programDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate programDate;

    @Column(name = "programLocation", nullable = false)
    private String programLocation;

    @Column(name = "targetParticipation", nullable = false)
    private Integer targetParticipation;

    @Column(name = "programDescription", nullable = false)
    private String programDescription;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "has_report", nullable = false)
    private boolean hasReport = false;

    public ManageProgram() {
        this.status = "Pending";
        this.programDate = LocalDate.now();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public LocalDate getProgramDate() {
        return programDate;
    }

    public void setProgramDate(LocalDate programDate) {
        this.programDate = (programDate == null) ? LocalDate.now() : programDate;
    }

    public String getProgramLocation() {
        return programLocation;
    }

    public void setProgramLocation(String programLocation) {
        this.programLocation = programLocation;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTargetParticipation() {
        return targetParticipation;
    }

    public void setTargetParticipation(Integer targetParticipation) {
        this.targetParticipation = targetParticipation;
    }

    public boolean isHasReport() {
        return hasReport;
    }

    public void setHasReport(boolean hasReport) {
        this.hasReport = hasReport;
    }
}