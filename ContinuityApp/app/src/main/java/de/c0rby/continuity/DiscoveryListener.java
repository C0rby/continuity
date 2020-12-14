package de.c0rby.continuity;

import android.net.nsd.NsdServiceInfo;

public interface DiscoveryListener {
    void onServiceDiscovered(NsdServiceInfo service);
}
