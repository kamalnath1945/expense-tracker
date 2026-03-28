package com.expense.tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name is required")
    @Size(min = 4, message = "minimum four character is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message="Enter valid mail")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$",
        message = "Password must contain uppercase, lowercase, number, special character and be at least 6 characters"
    )
    
    private String password;

    private Integer fixedmonthsalary;

    public Integer getFixedmonthsalary() {
        return fixedmonthsalary;
    }

    public void setFixedmonthsalary(Integer fixedmonthsalary) {
        this.fixedmonthsalary = fixedmonthsalary;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
