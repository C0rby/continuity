package de.c0rby.continuity;

import android.content.Context;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager;
import android.util.Log;

public class NsdHelper {
    public static final String TAG = "NsdHelper";
    public static final String SERVICE_TYPE = "_continuity._tcp";

    private NsdManager nsdManager;
    private DiscoverStatus discoveryStatus = DiscoverStatus.OFF;
    private DiscoveryListener listener;

    public NsdHelper(Context context, DiscoveryListener listener) {
        this.nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        this.listener = listener;
    }

    public void discoverServices() {
        if (this.discoveryStatus == DiscoverStatus.ON) return;
        this.nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, this.mDiscoveryListener);
        this.discoveryStatus = DiscoverStatus.ON;
    }

    public void stopDiscovery() {
        if (this.discoveryStatus == DiscoverStatus.OFF) return;
        this.nsdManager.stopServiceDiscovery(this.mDiscoveryListener);
    }

    private NsdManager.DiscoveryListener mDiscoveryListener = new NsdManager.DiscoveryListener() {

        @Override
        public void onDiscoveryStarted(String regType) {
            Log.d(TAG, "Service discovery started");
        }

        @Override
        public void onServiceFound(NsdServiceInfo info) {
            Log.d(TAG, "Service discovery success" + info);
            nsdManager.resolveService(info, resolveListener);
        }

        @Override
        public void onServiceLost(NsdServiceInfo info) {
            Log.e(TAG, "service lost" + info);
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            Log.i(TAG, "Discovery stopped: " + serviceType);
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            nsdManager.stopServiceDiscovery(this);
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            discoveryStatus = DiscoverStatus.OFF;
            nsdManager.stopServiceDiscovery(this);
        }
    };

    private NsdManager.ResolveListener resolveListener = new NsdManager.ResolveListener() {
        @Override
        public void onResolveFailed(NsdServiceInfo info, int errorCode) {
            Log.d(TAG, "Resolve failed: " + info.toString());
        }

        @Override
        public void onServiceResolved(NsdServiceInfo info) {
            Log.d(TAG, "Resolved: " + info.toString());
            listener.onServiceDiscovered(info);
        }
    };

    public enum DiscoverStatus {
        ON,
        OFF;
    }
}
