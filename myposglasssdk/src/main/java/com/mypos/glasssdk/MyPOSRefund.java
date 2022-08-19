package com.mypos.glasssdk;


import com.mypos.glasssdk.exceptions.InvalidAmountException;
import com.mypos.glasssdk.exceptions.InvalidEReceiptReceiverException;
import com.mypos.glasssdk.exceptions.MissingCurrencyException;

/**
 * Describes a refund
 */
public class MyPOSRefund extends MyPOSBase<MyPOSRefund> {

    private double              refundAmount;
    private Currency            currency;
    private String              eReceiptReceiver;


    private MyPOSRefund(Builder builder) {
        super(builder);
        this.refundAmount = builder.refundAmount;
        this.currency = builder.currency;
        this.eReceiptReceiver = builder.eReceiptReceiver;
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

    public String getEReceiptReceiver() {
        return eReceiptReceiver;
    }

    public MyPOSRefund setEReceiptReceiver(String eReceiptReceiver) {
        this.eReceiptReceiver = eReceiptReceiver;
        return this;
    }

    public static final class Builder extends MyPOSBase.BaseBuilder<Builder> {
        private Double          refundAmount;
        private Currency        currency;
        private String          eReceiptReceiver;

        public Builder refundAmount(Double productAmount) {
            this.refundAmount = productAmount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder eReceiptReceiver(String eReceiptReceiver) {
            this.eReceiptReceiver = eReceiptReceiver;
            return this;
        }

        public MyPOSRefund build() throws InvalidAmountException, MissingCurrencyException {
            if (this.refundAmount == null || this.refundAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Invalid currency");
            }
            if(eReceiptReceiver != null && !MyPOSUtil.isEmailValid(eReceiptReceiver) && !MyPOSUtil.isMobileNumberValid(eReceiptReceiver)) {
                throw new InvalidEReceiptReceiverException("e-receipt credential is not valid");
            }

            return new MyPOSRefund(this);
        }
    }
}
