package de.c0rby.continuity;

import android.content.Context;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager;
import android.util.Log;

public class NsdHelper {
    public static final String TAG = "NsdHelper";
    public static final String SERVICE_TYPE = "_http._tcp";

    public String mServiceName = "Continuity";

    private Context mContext;
    private NsdManager mNsdManager;
    private DiscoverStatus discoveryStatus = DiscoverStatus.OFF;

    public NsdHelper(Context context ) {
        this.mContext = context;
        this.mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    public void discoverServices() {
        if (this.discoveryStatus == DiscoverStatus.ON) return;
        this.mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, this.mDiscoveryListener);
        this.discoveryStatus = DiscoverStatus.ON;
    }

    public void stopDiscovery() {
        if (this.discoveryStatus == DiscoverStatus.OFF) return;
        this.mNsdManager.stopServiceDiscovery(this.mDiscoveryListener);
    }

    private NsdManager.DiscoveryListener mDiscoveryListener = new NsdManager.DiscoveryListener() {

        @Override
        public void onDiscoveryStarted(String regType) {
            Log.d(TAG, "Service discovery started");
        }

        @Override
        public void onServiceFound(NsdServiceInfo service) {
            Log.d(TAG, "Service discovery success" + service);
        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            Log.e(TAG, "service lost" + service);
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            Log.i(TAG, "Discovery stopped: " + serviceType);
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            mNsdManager.stopServiceDiscovery(this);
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            discoveryStatus = DiscoverStatus.OFF;
            mNsdManager.stopServiceDiscovery(this);
        }
    };

    public static enum DiscoverStatus {
        ON,
        OFF;
    }
}
