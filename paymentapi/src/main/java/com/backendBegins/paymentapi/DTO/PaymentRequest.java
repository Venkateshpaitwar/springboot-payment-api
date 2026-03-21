package com.backendBegins.paymentapi.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotBlank(message = "Currency cannot be empty")
    private String currency;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String userEmail;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}