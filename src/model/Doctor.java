package model;

import java.time.LocalDate;
import java.util.Objects;

public class Doctor {
    private int ID;
    private String fullName;
    private String specialization;
    private int availability;
    private String email;
    private String phoneNumber;
    private LocalDate birth;
    private static int AUTO_INCREMENT_ID = 0;

    public Doctor() {
        this.ID = generateID();
    }
    public Doctor(String fullName,
                  String specialization,
                  int availability,
                  String email,
                  String phoneNumber,
                  LocalDate birth) {
        ID = generateID();
        this.fullName = fullName;
        this.specialization = specialization;
        this.availability = availability;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
    }

    private static int generateID() {
        return ++AUTO_INCREMENT_ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public long getID() {
        return ID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getAvailability() {
        return availability;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvailabilityName() {
        switch (availability) {
            case 0:
                return "Vacation";
            case 1:
                return "Available";
            case 2:
                return "Busy";
            case 3:
                return "Diagnose Case";
            default:
                return "";
        }
    }

    public LocalDate getBirth() {
        return birth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return ID == doctor.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
