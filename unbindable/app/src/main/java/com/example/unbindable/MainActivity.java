package com.example.unbindable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final int MSG_REGISTER_CLIENT = 1;
    private static final int MSG_GET_FLAG = 4;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    break;
                case MSG_GET_FLAG:
                    Log.i("MOBISEC", "HANDLER");
                    Bundle ret = (Bundle) msg.obj;
                    if(ret.isEmpty()){
                        Log.i("MOBISEC", "NOTHING");
                    }
                    String res = ret.getString("flag");
                    Log.i("MOBISEC", res);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i("MOBISEC", "ENTER");
            Messenger sender = new Messenger(service);
            Message msg = Message.obtain(null,MainActivity.MSG_REGISTER_CLIENT);
            msg.replyTo = new Messenger(new IncomingHandler());
            try {
                sender.send(msg);
            } catch (RemoteException e) {
                Log.i("MOBISEC", e.toString());
            }
            Log.i("MOBISEC", "REGISTERED");
            msg = Message.obtain(null,MainActivity.MSG_GET_FLAG);
            try {
                sender.send(msg);
            } catch (RemoteException e) {
                Log.i("MOBISEC", e.toString());
            }
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.i("MOBISEC", "I dont know what happened");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setClassName("com.mobisec.unbindable", "com.mobisec.unbindable.UnbindableService");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

}