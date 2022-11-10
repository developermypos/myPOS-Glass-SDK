package com.mypos.glasssdk;

import com.mypos.glasssdk.exceptions.InvalidAmountException;
import com.mypos.glasssdk.exceptions.MissingCurrencyException;

/**
 * Created by rumen.ivanov on 24.10.2017 г..
 */

public class MyPOSQRPaymentRequest {
    private Double productAmount;
    private Currency currency;
    private Language language;

    MyPOSQRPaymentRequest(MyPOSQRPaymentRequest.Builder builder) {
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.language = builder.language;
    }

    public static MyPOSQRPaymentRequest.Builder builder() {
        return new MyPOSQRPaymentRequest.Builder();
    }

    public double getProductAmount() {
        return this.productAmount.doubleValue();
    }

    public MyPOSQRPaymentRequest setProductAmount(double productAmount) {
        this.productAmount = Double.valueOf(productAmount);
        return this;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public MyPOSQRPaymentRequest setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public Language getLanguage() {
        return this.language;
    }

    public MyPOSQRPaymentRequest setLanguage(Language language) {
        this.language = language;
        return this;
    }

    public static class Builder {
        private Double productAmount;
        private Currency currency;
        private Language language;

        public Builder() {
        }

        public MyPOSQRPaymentRequest.Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public MyPOSQRPaymentRequest.Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public MyPOSQRPaymentRequest.Builder language(Language language) {
            this.language = language;
            return this;
        }

        public MyPOSQRPaymentRequest build() throws MissingCurrencyException, InvalidAmountException {
            if(this.productAmount != null && this.productAmount.doubleValue() > 0.0D) {
                if(this.currency == null) {
                    throw new MissingCurrencyException("Missing currency");
                } else {
                    return new MyPOSQRPaymentRequest(this);
                }
            } else {
                throw new InvalidAmountException("Invalid or missing amount");
            }
        }
    }
}
