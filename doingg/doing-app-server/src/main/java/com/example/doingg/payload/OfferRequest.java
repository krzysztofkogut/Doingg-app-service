package com.example.doingg.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OfferRequest {
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
    @Valid
    private OfferLength offerLength;

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

    public OfferLength getOfferLength() {
        return offerLength;
    }

    public void setOfferLength(OfferLength offerLength) {
        this.offerLength = offerLength;
    }
}
