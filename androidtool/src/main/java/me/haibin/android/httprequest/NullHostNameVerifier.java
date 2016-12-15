package me.haibin.android.httprequest;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by ParkZhao on 16/12/14.
 */

public class NullHostNameVerifier implements HostnameVerifier{
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
