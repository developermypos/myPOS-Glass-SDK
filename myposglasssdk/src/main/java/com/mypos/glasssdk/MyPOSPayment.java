package com.mypos.glasssdk;


import com.mypos.glasssdk.exceptions.InvalidAmountException;
import com.mypos.glasssdk.exceptions.InvalidEReceiptReceiverException;
import com.mypos.glasssdk.exceptions.InvalidOperatorCodeExcepton;
import com.mypos.glasssdk.exceptions.InvalidReferenceNumberException;
import com.mypos.glasssdk.exceptions.InvalidReferenceTypeException;
import com.mypos.glasssdk.exceptions.InvalidTipAmountException;
import com.mypos.glasssdk.exceptions.MissingCurrencyException;

/**
 * Describes a payment
 */
public class MyPOSPayment extends MyPOSBase<MyPOSPayment> {

    private boolean     tippingModeEnabled;
    private double      productAmount;
    private double      tipAmount;
    private Currency    currency;
    private String      operatorCode;
    private String      referenceNumber;
    private int         referenceType;
    private boolean     mastercardSonicBranding;
    private boolean     visaSensoryBranding;
    private String      eReceiptReceiver;


    MyPOSPayment(Builder builder) {
        super(builder);
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.tippingModeEnabled = builder.tippingModeEnabled;
        this.tipAmount = builder.tipAmount;
        this.operatorCode = builder.operatorCode;
        this.referenceNumber = builder.referenceNumber;
        this.referenceType = builder.referenceType;
        this.mastercardSonicBranding = builder.mastercardSonicBranding;
        this.visaSensoryBranding = builder.visaSensoryBranding;
        this.eReceiptReceiver = builder.eReceiptReceiver;
    }


    public static Builder builder() {
        return new Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSPayment setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSPayment setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isTippingModeEnabled() {
        return tippingModeEnabled;
    }

    public MyPOSPayment setTippingModeEnabled(boolean tippingModeEnabled) {
        this.tippingModeEnabled = tippingModeEnabled;
        return this;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public MyPOSPayment setTipAmount(double tipAmount) {
        this.tipAmount = tipAmount;
        return this;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public MyPOSPayment setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
        return this;
    }
        
    public boolean mastercardSonicBranding() {
        return mastercardSonicBranding;
    }

    public MyPOSPayment setMastercardSonicBranding(boolean  mastercardSonicBranding) {
        this.mastercardSonicBranding = mastercardSonicBranding;
        return this;
    }

    public boolean visaSensoryBranding() {
        return visaSensoryBranding;
    }

    public MyPOSPayment setVisaSensoryBranding(boolean  visaSensoryBranding) {
        this.visaSensoryBranding = visaSensoryBranding;
        return this;
    }

    public String getEReceiptReceiver() {
        return eReceiptReceiver;
    }

    public MyPOSPayment setEReceiptReceiver(String eReceiptReceiver) {
        this.eReceiptReceiver = eReceiptReceiver;
        return this;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getReferenceType() {
        return referenceType;
    }

    public MyPOSPayment setReference(String referenceNumber, int referenceType) {
        this.referenceNumber = referenceNumber;
        this.referenceType = referenceType;
        return this;
    }

    public static class Builder extends MyPOSBase.BaseBuilder<Builder> {
        private boolean     tippingModeEnabled;
        private double      tipAmount;
        private Double      productAmount;
        private Currency    currency;
        private String      operatorCode;
        private String      referenceNumber;
        private int         referenceType;
        private boolean     mastercardSonicBranding = true;
        private boolean     visaSensoryBranding = true;
        private String      eReceiptReceiver;

        public Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public Builder tipAmount(Double tipAmount) {
            this.tipAmount = tipAmount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder tippingModeEnabled(boolean tippingModeEnabled) {
            this.tippingModeEnabled = tippingModeEnabled;
            return this;
        }

        public Builder operatorCode(String operatorCode) {
            this.operatorCode = operatorCode;
            return this;
        }

        public Builder mastercardSonicBranding(boolean mastercardSonicBranding) {
            this.mastercardSonicBranding = mastercardSonicBranding;
            return this;
        }

        public Builder visaSensoryBranding(boolean visaSensoryBranding) {
            this.visaSensoryBranding = visaSensoryBranding;
            return this;
        }

        public Builder reference(String referenceNumber, int referenceType) {
            this.referenceNumber = referenceNumber;
            this.referenceType = referenceType;
            return this;
        }

        public Builder eReceiptReceiver(String eReceiptReceiver) {
            this.eReceiptReceiver = eReceiptReceiver;
            return this;
        }

        public MyPOSPayment build() throws InvalidAmountException, InvalidTipAmountException, MissingCurrencyException, InvalidOperatorCodeExcepton, InvalidReferenceTypeException, InvalidReferenceNumberException {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Missing currency");
            }

            if (this.tippingModeEnabled && this.tipAmount <= 0.0D) {
                throw new InvalidTipAmountException("Invalid tip amount");
            }

            if (operatorCode != null ) {

                boolean valid = true;

                if (operatorCode.length() > 4 || operatorCode.isEmpty()) {
                    valid = false;
                }
                else {
                    try {
                        if(Integer.parseInt(operatorCode) < 0) {
                            valid = false;
                        }
                    } catch (NumberFormatException e) {
                        valid = false;
                    }
                }

                if(!valid) {
                    throw new InvalidOperatorCodeExcepton("incorrect operator code");
                }
            }

            if(!ReferenceType.isInBound(referenceType)) {
                throw new InvalidReferenceTypeException("reference type out of bound");
            }
            if(ReferenceType.isEnabled(referenceType) && !MyPOSUtil.isReferenceNumberValid(referenceNumber)) {
                throw new InvalidReferenceNumberException("incorrect reference number");
            }
            if(eReceiptReceiver != null && !MyPOSUtil.isEmailValid(eReceiptReceiver) && !MyPOSUtil.isMobileNumberValid(eReceiptReceiver)) {
                throw new InvalidEReceiptReceiverException("e-receipt receiver is not valid");
            }

            return new MyPOSPayment(this);
        }
    }
}
