package com.kydon.appchat;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketIO {
    private static SocketIO instance;
    Socket mSocket;
    private SocketIO() {
        try {
            mSocket = IO.socket("http://192.168.0.108:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static SocketIO Instance()
    {
        if (instance == null)
        {
            instance = new SocketIO();

        }
        return instance;
    }
    public Socket getmSocket()
    {
        return this.mSocket;
    }

    public void setmSocket(Socket mSocket) {
        this.mSocket = mSocket;
    }
}
