// src/main/java/com/ps_dev/classenrollment/dto/UserDTO.java
package com.ps_dev.classenrollment.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String uniqueId;

    private String fullName;

    // --- GETTERS AND SETTERS ---
    // The IDE and Thymeleaf need these to access the fields

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}