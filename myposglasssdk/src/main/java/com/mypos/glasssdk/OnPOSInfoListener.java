package com.mypos.glasssdk;

import com.mypos.glasssdk.data.POSInfo;


public interface OnPOSInfoListener {
    void onReceive(POSInfo info);
}
