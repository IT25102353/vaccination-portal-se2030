package com.se2030.vaccination_portal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import com.se2030.vaccination_portal.pattern.strategy.ExpiryStatusStrategy;
import com.se2030.vaccination_portal.pattern.strategy.WarningWindowExpiryStatus;

import java.time.LocalDate;

@Entity
public class VaccineStock {

    private static final ExpiryStatusStrategy EXPIRY_STATUS = new WarningWindowExpiryStatus(30);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;
    private String batchNumber;
    private String manufacturer;
    private Integer quantityAvailable;
    private LocalDate receivedDate;
    private LocalDate expiryDate;
    private String storageLocation;

    public VaccineStock() {
    }

    public VaccineStock(String vaccineName, String batchNumber, String manufacturer, Integer quantityAvailable,
                         LocalDate receivedDate, LocalDate expiryDate, String storageLocation) {
        this.vaccineName = vaccineName;
        this.batchNumber = batchNumber;
        this.manufacturer = manufacturer;
        this.quantityAvailable = quantityAvailable;
        this.receivedDate = receivedDate;
        this.expiryDate = expiryDate;
        this.storageLocation = storageLocation;
    }

    // The status is calculated by a Strategy and is not saved in the database
    @Transient
    public String getStatus() {
        return EXPIRY_STATUS.calculateStatus(expiryDate, LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }
}
