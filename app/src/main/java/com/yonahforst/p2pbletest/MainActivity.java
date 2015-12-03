package com.yonahforst.p2pbletest;

import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.thaliproject.p2p.btconnectorlib.ConnectionManager;
import org.thaliproject.p2p.btconnectorlib.PeerDeviceProperties;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ConnectionManager mConnectionManager;
    private ConnectionManager.ConnectionManagerListener mConnectionManagerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startP2P();
    }

    private void startP2P() {
        if (mConnectionManagerListener == null) {
            mConnectionManagerListener = new ConnectionManager.ConnectionManagerListener() {
                @Override
                public void onConnectionManagerStateChanged(ConnectionManager.ConnectionManagerState connectionManagerState) {
                    Log.d("CML", "connection mgr state changed");
                }

                @Override
                public void onPeerListChanged(List<PeerDeviceProperties> list) {
                    Log.d("CML", "connection mgr peer list changed: " + list);
                }

                @Override
                public void onPeerDiscovered(PeerDeviceProperties peerDeviceProperties) {
                    Log.d("CML", "connection mgr peer discovered: " + peerDeviceProperties);
                    mConnectionManager.connect(peerDeviceProperties);
                }

                @Override
                public void onConnected(BluetoothSocket bluetoothSocket, boolean b, String s, String s1, String s2) {
                    Log.d("CML", "connection mgr connected");
                }

                @Override
                public void onConnectionFailed(String s, String s1, String s2) {
                    Log.d("CML", "connection mgr failed");
                }
            };
        }


        if (mConnectionManager == null) {
            mConnectionManager = new ConnectionManager(this, mConnectionManagerListener, UUID.randomUUID(), "Yonah1", "_myservice._tcp");
            mConnectionManager.initialize("myPeerID1", "myPeerName1");
            mConnectionManager.start();
        }

    }
}
