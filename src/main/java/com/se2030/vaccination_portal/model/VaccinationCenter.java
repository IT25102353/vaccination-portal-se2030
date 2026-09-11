package com.se2030.vaccination_portal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VaccinationCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String centerName;
    private String address;
    private String district;
    private String contactNumber;
    private Integer capacityPerDay;
    private String operatingHours;
    private String status;

    public VaccinationCenter() {
    }

    public VaccinationCenter(String centerName, String address, String district, String contactNumber,
                              Integer capacityPerDay, String operatingHours, String status) {
        this.centerName = centerName;
        this.address = address;
        this.district = district;
        this.contactNumber = contactNumber;
        this.capacityPerDay = capacityPerDay;
        this.operatingHours = operatingHours;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Integer getCapacityPerDay() {
        return capacityPerDay;
    }

    public void setCapacityPerDay(Integer capacityPerDay) {
        this.capacityPerDay = capacityPerDay;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
