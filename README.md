# myPOS-Glass-SDK

This repository provides a guidance on integrating an Android app with a myPOS Glass payment solution. Once integrated, the app will be able to communicate and call the main myPOS Glass functionalities in order to accept card payments (Limited to VISA and Mastercard). myPOS-Glass-SDK is a bridge between the applications and myPOS Glass app, converting the smart device into a POS machine, allowing to initiate transaction, complete all steps for processing payment, make refund to the customer card account and send a custom bill slip via mail or SMS. The built-in functionalities of myPOS-Glass-SDK allows you to accept payments, make refunds and void the last approved transaction.

No sensitive card data is ever passed through or stored on the smart device. All data is encrypted by myPOS Glass app, which has been fully certified to the highest industry standards (PCI, EMV II, Visa, MasterCard).


### Table of Contents

* [Installation](#installation)

* [Usage](#Usage)

  * [Receive POS info](#receive-pos-info)

  * [Process a checkout](#process-a-checkout)

  * [Refund request](#refund-request)
  
  * [Void Request](#void-request)
  
* [Response](#response)

* [Deep Links API](#deep-links-api)


## Installation

Add the repository to your gradle dependencies:

```java
allprojects {
   repositories {
      mavenCentral()
   }
}
```

Add the dependency to a module:

```java
implementation 'com.mypos:glasssdk:1.0.4'
```

# Usage

Once the SDK is added to your project, using the Payment API can be done with the provided helper classes.


### Receive POS info

Add this to your AndroidManifest.xml file
```xml
<queries>
	<package android:name="com.mypos.top" />
</queries>
```

Here you can find simple info about myPOS terminal like	TID, currency name, currency code, merchant info, etc.

```java
MyPOSAPI.registerPOSInfo(MainActivity.this, new OnPOSInfoListener() {
            @Override
            public void onReceive(POSInfo info) {
                //info is received
            }
        });
```

### Process a checkout


##### 1. Perform the payment

```java
// Build the payment call
 MyPOSPayment payment = MyPOSPayment.builder()
         // Mandatory parameters
         .productAmount(13.37)
         .currency(Currency.EUR)
         // Foreign transaction ID. Maximum length: 128 characters
         .foreignTransactionId(UUID.randomUUID().toString())
	 // Optional parameters
	 // Enable tipping mode
	 .tippingModeEnabled(true)
         .tipAmount(1.55)
	 // Operator code. Maximum length: 4 characters
	 .operatorCode("1234")
	 // Reference number. Maximum length: 20 alpha numeric characters
	 .reference("asd123asd", ReferenceType.REFERENCE_NUMBER)
	 // card scheme brandnig
	 .mastercardSonicBranding(true)
	 .visaSensoryBranding(true)
	 // Set receipt mode if printer is paired
	 .printMerchantReceipt(MyPOSUtil.RECEIPT_ON) // possible options RECEIPT_ON, RECEIPT_OFF
	 .printCustomerReceipt(MyPOSUtil.RECEIPT_ON) // possible options RECEIPT_ON, RECEIPT_OFF, RECEIPT_AFTER_CONFIRMATION, RECEIPT_E_RECEIPT
	 //set email or phone e-receipt receiver, works with customer receipt configuration RECEIPT_E_RECEIPT or RECEIPT_AFTER_CONFIRMATION
	 .eReceiptReceiver("examplename@example.com")
         .build();
	 
 // Start the transaction
 MyPOSAPI.openPaymentActivity(MainActivity.this, payment, 1);
```


##### 2. Handle the result

In your calling Activity, override the ``onActivityResult`` method to handle the result of the payment:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // The same request code as when calling MyPOSAPI.openPaymentActivity
    if (requestCode == 1) {
        // The transaction was processed, handle the response
        if (resultCode == RESULT_OK) {
            // Something went wrong in the Payment core app and the result couldn't be returned properly
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

            Toast.makeText(this, "Payment transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

            // TODO: handle each transaction response accordingly
            if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Transaction is successful
            }
        } else {
            // The user cancelled the transaction
            Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}

```

Checking if the transaction is approved can be done by reading the ``transaction_approved`` boolean extra from the response:

```java
boolean transaction_approved = data.getBooleanExtra("transaction_approved", false);

if (transaction_approved) {
    // Transaction is approved
} else {
    // Transaction was not approved
    // The response code is in the "response_code" string extra
}

```


### Refund request


##### 1. Perform the refund

``` java
// Build the refund request
MyPOSRefund refund = MyPOSRefund.builder()
	// Mandatoy parameters
        .refundAmount(1.23)
        .currency(Currency.EUR)
        .foreignTransactionId(UUID.randomUUID().toString())
	// Set receipt mode if printer is paired
	.printMerchantReceipt(MyPOSUtil.RECEIPT_ON) // possible options RECEIPT_ON, RECEIPT_OFF
	.printCustomerReceipt(MyPOSUtil.RECEIPT_ON) // possible options RECEIPT_ON, RECEIPT_OFF, RECEIPT_AFTER_CONFIRMATION, RECEIPT_E_RECEIPT
	//set email or phone e-receipt receiver, works with customer receipt configuration RECEIPT_E_RECEIPT or RECEIPT_AFTER_CONFIRMATION
	.eReceiptReceiver("examplename@example.com")
        .build();

// Start the transaction
MyPOSAPI.openRefundActivity(MainActivity.this, refund, 2);
```

##### 2. Handle the result

The same as with the payment, in your calling Activity, override the ``onActivityResult`` method to handle the result of the refund:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // The same request code as when calling MyPOSAPI.openRefundActivity
    if (requestCode == 2) {
        // The transaction was processed, handle the response
        if (resultCode == RESULT_OK) {
            // Something went wrong in the Payment core app and the result couldn't be returned properly
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

            Toast.makeText(this, "Refund transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

            // TODO: handle each transaction response accordingly
            if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Transaction is successful
            }
        } else {
            // The user cancelled the transaction
            Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}

```

### Void Request


##### 1. Perform void transaction

 ```java
 // Build the void transaction
 private static final int VOID_REQUEST_CODE = 4;
    private void startVoid() {
        // Build the void request
        MyPOSVoid voidEx = MyPOSVoid.builder()
                .STAN(27)
                .authCode("VISSIM")
                .dateTime("180129123753")
		//.voidLastTransactionFlag(true) // this may void last transaction initialized by this terminal
                .build();
				
		// Start the void transaction
		MyPOSAPI.openVoidActivity(MainActivity.this, voidEx, VOID_REQUEST_CODE, true);
    }
```

##### 2.  Handle the result

The same as with the payment, in your calling Activity, override the ``onActivityResult`` method to handle the result of the void request:

```java
@Override
	void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == VOID_REQUEST_CODE) {
			// The transaction was processed, handle the response
			if (resultCode == RESULT_OK) {
				// Something went wrong in the Payment core app and the result couldn't be returned properly
				if (data == null) {
					Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
					return;
				}
				int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

				Toast.makeText(this, "Void transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

				// TODO: handle each transaction response accordingly
				if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
					// Transaction is successful
				}
			} else {
				// The user cancelled the transaction
				Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
			}
		}
    }
```

### Response

When a transaction has finished, an Intent with the following data is returned to the calling Activity:

* reference_number - Internal myPOS reference number for the transaction
* cardholder_name - Emboss name on the card
* date_time - Date and time of the transaction formatted as YYMMDDHHmmss
* status (int) - one of the constants in the [TransactionProcessingResult](myposglasssdk/src/main/java/com/mypos/glasssdk/TransactionProcessingResult.java) class
* status_text - a textual representation of the status
* card_brand - MASTERCARD, MAESTRO, VISA, VISA ELECTRON, VPAY
* card_entry_mode – method of presenting the card:
	-	ENTRY_MODE_MAGSTR – mag stripe transaction
	-	ENTRY_MODE_EMV – chip transaction
	-	ENTRY_MODE_CONTACTLESS – contactless mag stripe transaction
	-	ENTRY_MODE_CONTACTLESS_MCHIP – contactless chip transaction
	-	ENTRY_MODE_MANUAL – Manual Key Entry (MO/TO) transaction

* response_code - response code returned by issuer. Values, different from "00", represent the reason for a declined transaction
* authorization_code - authorization code returned by issuer
* signature_required (boolean) - true : signature row must be present on receipt , false : signature row must not be present on receipt
* TSI - Transaction Status Indicator
* TVR - Terminal Verification Result
* AID - Application Identifier (card)
* STAN - System Trace Audit Number (unique number of transaction by TID)
* CVM - Cardholder Verification Method (P – PIN, S – Signature , N – NO CVM)
* application_name - Application Label, read from the card chip
* transaction_approved (boolean) - – true : approved, false : declined
* TID - Terminal id
* update_pending (boolean) - New update is available
* resp_code - Payment request response code. Values, different from "00", represent the reason for a declined transaction
* expire_date - Payment request expire date
* merchant_data - Bundle with data from your myPOS profile used for printing the receipts. It contains:
  * billing_descriptor - merchant billing descriptor
  * address_line1 - merchant address line 1
  * address_line2 - merchant address line 2
  * MID - Merchant ID
  * custom_receipt_row1 - custom receipt footer row 1
  * custom_receipt_row2 - custom receipt footer row 2
* installment_data - Bundle with data if user paid in installments. It contains:
  * number (int) - selected number of installments
  * interest_rate (double) - installment interest rate
  * fee (double) - installment fee
  * annual_percentage_rate (double) - installment annual percantage rate
  * total_amount (double) - installments total amount
  * first_installment_amount (double) - first installment amount
  * subsequent_installment_amount (double) - subsequent installment amount


Note 1: Unless noted, extras in the bundle are Strings.

Note 2: Depending on the card and transaction type, some of the extras are not always present.

# Deep Links API

This API gives its user the option to deep-link their website to take in-person payments via the myPOS Glass app.

[Full API Documentation](https://developers.mypos.eu/en/doc/in_person_payments/v1_0/395-deep-links-api)


##### 1. Perform the payment

If you want to execute payment from а web page, you`ll need to add this URL to your website.

Example:

```html
 <a href="myposapi://glass/1.0?action=com.mypos.transaction.START_TRANSACTION&request_code=101&amount=1.23&currency=EUR&app_id=yourwebsite.com&app_version=1.0.0&foreign_transaction_id=123test321&callback=https://www.yourwebsite.com/myposglassendpoint">Start myPOS Payment</a>
```

##### Handle the result

Make sure that the callback URL you provide is correct and controlled by you.


Example of Approved transaction:

{CALLBACK_URL}?status=0&status_text=TRANSACTION_SUCCESS&transaction_approved=true&foreign_transaction_id=123test321&stan=6&date_time=211123172012&authorization_code=074114


Example of Declined transaction:

{CALLBACK_URL}?status=2&status_text=TRANSACTION_DECLINED&transaction_approved=false&foreign_transaction_id=123test321
