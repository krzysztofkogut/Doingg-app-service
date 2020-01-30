package com.example.doingg.model;

import com.example.doingg.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "offers")
public class Offer extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 140)
    private String question;

    @NotBlank
    @Size(max = 40)
    private String city;

    @NotBlank
    @Size(max = 40)
    private String phone;

    @NotBlank
    @Size(max = 40)
    private String wage;

    @NotNull
    private Instant expirationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getWage() { return wage; }

    public void setWage(String wage) { this.wage = wage; }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

}
