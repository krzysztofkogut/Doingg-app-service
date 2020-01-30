package com.example.doingg.payload;
import javax.validation.constraints.NotNull;

public class BidRequest {
    @NotNull
    private Long offerId;

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }
}

