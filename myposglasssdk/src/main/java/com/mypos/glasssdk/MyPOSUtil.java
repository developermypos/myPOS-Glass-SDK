package com.mypos.glasssdk;

public class MyPOSUtil {
    /**
     * Used to start a transaction.
     */
    public static final String PAYMENT_CORE_ENTRY_POINT_INTENT           = "com.mypos.transaction.START_TRANSACTION";
    /**
     * Start a void transaction
     */
    public static final String PAYMENT_CORE_VOID_INTENT                  = "com.mypos.transaction.VOID";

    public static final String PAYMENT_CORE_VOID_INTENT_EX               = "com.mypos.transaction.VOID_EX";

    public static final String GET_SIMPLE_POS_INFO                       = "com.mypos.action.GET_SIMPLE_POS_INFO";
    public static final String GET_SIMPLE_POS_INFO_RESPONSE              = "com.mypos.broadcast.exported.SIMPLE_POS_INFO_RESPONSE";

    /**
     * Used to pass the transaction amount to the Payment core
     */
    public static final String INTENT_TRANSACTION_AMOUNT                 = "amount";
    /**
     * Used for telling the Payment core what the transaction type is
     */
    public static final String INTENT_TRANSACTION_REQUEST_CODE           = "request_code";
    /**
     * For setting the transaction currency
     */
    public static final String INTENT_TRANSACTION_CURRENCY               = "currency";
    /**
     * If true the payment app won't show the confirmation screen
     */
    public static final String INTENT_SKIP_CONFIRMATION_SCREEN           = "skip_confirmation_screen";
    /**
     * Used for passing the foreign transaction ID parameter to the payment activity
     */
    public static final String INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID = "foreign_transaction_id";
    /**
     * Whether or not tips should be enabled
     */
    public static final String INTENT_TRANSFER_TIPS_ENABLED              = "tips_enabled";
    /**
     * Amount of the tip
     */
    public static final String INTENT_TRANSACTION_TIP_AMOUNT             = "tip_amount";

    public static final String INTENT_OPERATOR_CODE              = "operator_code";
    public static final String INTENT_REFERENCE_NUMBER           = "reference_number";
    public static final String INTENT_REFERENCE_NUMBER_TYPE      = "reference_number_type";
    public static final String INTENT_ENABLE_MASTERCARD_SONIC    = "enable_mastercard_sonic";
    public static final String INTENT_ENABLE_VISA_SENSORY        = "enable_visa_sensory";

    public static final String INTENT_VOID_STAN                            = "STAN";
    public static final String INTENT_VOID_AUTH_CODE                       = "authorization_code";
    public static final String INTENT_VOID_DATE_TIME                       = "date_time";

    /**
     * Request code for a Payment
     */
    public static final int TRANSACTION_TYPE_PAYMENT = 101;
    /**
     * Request code for Void
     */
    public static final int TRANSACTION_TYPE_VOID    = 102;
    /**
     * Request code for a Refund
     */
    public static final int TRANSACTION_TYPE_REFUND  = 103;

    public static boolean isReferenceNumberValid(String referenceNumber) {
        return referenceNumber == null || (referenceNumber.length() <= 50 && referenceNumber.matches("[a-zA-Z0-9\\p{Punct}]+"));
    }

}
