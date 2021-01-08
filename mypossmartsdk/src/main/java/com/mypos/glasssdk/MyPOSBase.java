package com.mypos.glasssdk;

import java.io.Serializable;

public class MyPOSBase<D extends MyPOSBase> implements Serializable {

    private String      foreignTransactionId;
    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public D setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return (D) this;
    }

    protected MyPOSBase(BaseBuilder builder) {
        this.foreignTransactionId = builder.foreignTransactionId;
    }

    public static BaseBuilder builder() {
        return new BaseBuilder();
    }

    public static class BaseBuilder<T extends BaseBuilder<T>> implements Serializable {

        private String foreignTransactionId;
        private int    printMerchantReceipt;
        private int    printCustomerReceipt;

        public T foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return (T) this;
        }

        public MyPOSBase build() {
            return new MyPOSBase(this);
        }

    }
}