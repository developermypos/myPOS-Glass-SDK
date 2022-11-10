package com.mypos.glasssdk;

import com.mypos.glasssdk.exceptions.InvalidAmountException;
import com.mypos.glasssdk.exceptions.MissingCurrencyException;
import com.mypos.glasssdk.exceptions.MissingRecipientException;

/**
 * Created by rumen.ivanov on 24.10.2017 г..
 */

public class MyPOSPaymentRequest {
    private Double productAmount;
    private Currency currency;
    private Language language;
    private String GSM;
    private String eMail;
    private String reason;
    private String recipientName;
    private Integer expiryDays;

    MyPOSPaymentRequest(Builder builder) {
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.language = builder.language;
        this.GSM = builder.GSM;
        this.eMail = builder.eMail;
        this.reason = builder.reason;
        this.recipientName = builder.recipientName;
        this.expiryDays = builder.expiryDays;
    }

    public static Builder builder() {
        return new Builder();
    }

    public double getProductAmount() {
        return this.productAmount.doubleValue();
    }

    public MyPOSPaymentRequest setProductAmount(double productAmount) {
        this.productAmount = Double.valueOf(productAmount);
        return this;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public MyPOSPaymentRequest setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public Language getLanguage() {
        return this.language;
    }

    public MyPOSPaymentRequest setLanguage(Language language) {
        this.language = language;
        return this;
    }

    public String getGSM() {
        return this.GSM;
    }

    public MyPOSPaymentRequest setGSM(String GSM) {
        this.GSM = GSM;
        return this;
    }

    public String getEMail() {
        return this.eMail;
    }

    public MyPOSPaymentRequest setEMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public String getReason() {
        return this.reason;
    }

    public MyPOSPaymentRequest setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getRecipientName() {
        return this.recipientName;
    }

    public MyPOSPaymentRequest setRecipientName(String recipientName) {
        this.recipientName = recipientName;
        return this;
    }

    public Integer getExpiryDays() {
        return this.expiryDays;
    }

    public MyPOSPaymentRequest setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
        return this;
    }

    public static class Builder {
        private Double productAmount;
        private Currency currency;
        private Language language;
        private String GSM;
        private String eMail;
        private String reason;
        private String recipientName;
        private Integer expiryDays;

        public Builder() {
        }

        public Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder language(Language language) {
            this.language = language;
            return this;
        }

        public Builder GSM(String GSM) {
            this.GSM = GSM;
            return this;
        }

        public Builder eMail(String eMail) {
            this.eMail = eMail;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder recipientName(String recipientName) {
            this.recipientName = recipientName;
            return this;
        }

        public Builder expiryDays(int expiryDays) {
            this.expiryDays = Integer.valueOf(expiryDays);
            return this;
        }

        public MyPOSPaymentRequest build() throws MissingCurrencyException, InvalidAmountException, MissingRecipientException {
            if(this.productAmount != null && this.productAmount.doubleValue() > 0.0D) {
                if(this.currency == null) {
                    throw new MissingCurrencyException("Missing currency");
                } else if(this.GSM != null && !this.GSM.isEmpty() || this.eMail != null && !this.eMail.isEmpty()) {
                    return new MyPOSPaymentRequest(this);
                } else {
                    throw new MissingRecipientException("Missing recipient");
                }
            } else {
                throw new InvalidAmountException("Invalid or missing amount");
            }
        }
    }
}
