package com.mypos.glasssdk;


import com.mypos.glasssdk.exceptions.InvalidAmountException;
import com.mypos.glasssdk.exceptions.MissingCurrencyException;

/**
 * Describes a refund
 */
public class MyPOSRefund extends MyPOSBase<MyPOSRefund> {

    private double              refundAmount;
    private Currency            currency;

    private MyPOSRefund(Builder builder) {
        super(builder);
        this.refundAmount = builder.refundAmount;
        this.currency = builder.currency;
    }


    public static Builder builder() {
        return new Builder();
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public MyPOSRefund setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSRefund setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public static final class Builder extends MyPOSBase.BaseBuilder<Builder> {
        private Double          refundAmount;
        private Currency        currency;

        public Builder refundAmount(Double productAmount) {
            this.refundAmount = productAmount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public MyPOSRefund build() throws InvalidAmountException, MissingCurrencyException {
            if (this.refundAmount == null || this.refundAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Invalid currency");
            }

            return new MyPOSRefund(this);
        }
    }
}
