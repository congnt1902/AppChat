package com.kydon.appchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnChat;
    EditText edtChat;
    ListView lvUserName, lvChat;
    //    public static Socket mSocket;
    ArrayList<String> arrUserName;
    ArrayList<String> arrChat;
    private ContactAdapter adapter2;
    ListChatAdapter adapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    static String username;
    ImageView btnInsertPhoto;
//    {
//        try {
//            mSocket = IO.socket("http://192.168.100.101:3000");
//        } catch (URISyntaxException e) {
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();


    }

    private void initview() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setTitle("Room chat server");
//        mSocket.connect();
//        mSocket.on("new_user", onReceiptUser);
//        mSocket.on("server-gui-username", listUserName);
//        mSocket.on("server-gui-tin-chat", listChat);
//
//        mSocket.on("new_user_online", onReceiptStatusUserOnline);
//
//        mSocket.on("user_disconnect", onReceiptStatusUserOffline);
        SocketIO.Instance().getmSocket().connect();
        SocketIO.Instance().getmSocket().on("new_user", onReceiptUser);
        SocketIO.Instance().getmSocket().on("server-gui-username", listUserName);
        SocketIO.Instance().getmSocket().on("server-gui-tin-chat", listChat);
        SocketIO.Instance().getmSocket().on("new_user_online", onReceiptStatusUserOnline);
        SocketIO.Instance().getmSocket().on("user_disconnect", onReceiptStatusUserOffline);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        arrChat = new ArrayList<String>();
        arrUserName = new ArrayList<>();
        lvUserName = findViewById(R.id.lvUserName);
        lvChat = findViewById(R.id.lvChat);

        btnChat = findViewById(R.id.btnChat);
btnInsertPhoto=findViewById(R.id.btnInsertPhoto);
        edtChat = findViewById(R.id.edtChat);
        btnInsertPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
             startActivity(intent);
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketIO.Instance().getmSocket().emit("client-gui-tin-chat", edtChat.getText().toString());
                edtChat.setText("");

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private Emitter.Listener listUserName = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray noidung;

                    try {
                        noidung = data.getJSONArray("danhsach");

                        for (int i = 0; i < noidung.length(); i++) {
                            arrUserName.add(noidung.get(i).toString());
                        }
                        adapter2 = new ContactAdapter(arrUserName, getLayoutInflater());
                        lvUserName.setAdapter(adapter2);
                        adapter2.notifyDataSetChanged();


                    } catch (JSONException e) {
                        return;
                    }


                }
            });
        }
    };
    private Emitter.Listener listChat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;

                    try {
                        noidung = data.getString("message");
                        arrChat.add(noidung);
                        adapter = new ListChatAdapter(arrChat, getLayoutInflater());
                        // ArrayAdapter adapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, arrChat);
                        lvChat.setAdapter(adapter);


                    } catch (JSONException e) {
                        return;
                    }


                }
            });
        }
    };

    private Emitter.Listener onReceiptStatusUserOnline = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject) args[0];
                    String user = obj.optString("username");

                    adapter2.addUserOnline(user);
                }
            });
        }
    };

    private Emitter.Listener onReceiptUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject) args[0];
                    String user = obj.optString("username");


                }
            });
        }
    };
    private Emitter.Listener onReceiptStatusUserOffline = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject) args[0];
                    String user = obj.optString("username");
                    adapter2.removeUserOffline(user);
                }
            });

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnlogout:
                showAlertDialog();

                return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");
        builder.setMessage("Are you want to Log out");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                MainActivity.super.finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
