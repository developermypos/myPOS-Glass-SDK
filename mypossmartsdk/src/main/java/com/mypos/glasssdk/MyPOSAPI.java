package com.mypos.glasssdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.mypos.glasssdk.data.POSInfo;
import com.mypos.glasssdk.exceptions.FunctionalityNotSupportedException;


public class MyPOSAPI {

    /**
     * Takes care of send and receive broadcast receivers to/from the system
     * AVAILABLE IN VERSION: 1.0.2
     *
     * @param context       this context will be used to for broadcast communication with the system
     * @param listener     a callback listener for received POS info
     */
    public static void registerPOSInfo(final Context context, final OnPOSInfoListener listener) {

        if (context == null)
            return;

        context.sendBroadcast(new Intent(MyPOSUtil.GET_SIMPLE_POS_INFO));

        context.registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context cx, Intent intent) {

                        if (listener != null) {
                            POSInfo inf = new POSInfo();
                            inf.parseFromBundle(intent.getExtras());
                            listener.onReceive(inf);
                        }

                        context.unregisterReceiver(this);
                    }
                },new IntentFilter(MyPOSUtil.GET_SIMPLE_POS_INFO_RESPONSE));
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param payment     a {@link MyPOSPayment} object with payment-related data
     * @param requestCode the request code used later to distinguish
     */
    public static void openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode) throws FunctionalityNotSupportedException {
        openPaymentActivity(activity, payment, requestCode, false);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param payment                a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getPaymentIntent(payment, skipConfirmationScreen);
        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a refund transaction
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param refund      a {@link MyPOSPayment} object with payment-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode) throws FunctionalityNotSupportedException {
        openRefundActivity(activity, refund, requestCode, false);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a refund transaction
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param refund                 a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getRefundIntent(refund, skipConfirmationScreen);
        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a void transaction
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param voidTr                 a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openVoidActivity(Activity activity, MyPOSVoid voidTr, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getVoidIntent(voidTr, skipConfirmationScreen);
        startActivityForResult(activity, myposIntent, requestCode);
    }

    private static void startActivityForResult(Activity activity, Intent intent, int requestCode) throws FunctionalityNotSupportedException {
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            throw new FunctionalityNotSupportedException("Functionality not supported");
        }
    }
}
