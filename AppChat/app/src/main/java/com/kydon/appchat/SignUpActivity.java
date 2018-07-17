package com.kydon.appchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SignUpActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword, edtRePassword;
    Button btnSignUp;

//    {
//        try {
//            MainActivity.mSocket = IO.socket("http://192.168.100.101:3000");
//        } catch (URISyntaxException e) {
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePassword = findViewById(R.id.edtRePassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(btnSignUp_OnClick);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SocketIO.Instance().getmSocket().connect();
        SocketIO.Instance().getmSocket().on("ket-qua-dang-ky", register);

    }

    View.OnClickListener btnSignUp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SocketIO.Instance().getmSocket().emit("clent-gui-username", edtUsername.getText().toString());

        }
    };
    private Emitter.Listener register = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;

                    try {
                        noidung = data.getString("noidung");

                        if (edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("") || edtRePassword.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "The Username or Password field is empty", Toast.LENGTH_SHORT).show();
                        } else if (!edtPassword.getText().toString().equals(edtRePassword.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Password and confirm password does not match", Toast.LENGTH_SHORT).show();
                        } else if (edtPassword.getText().toString().length() < 6) {
                            Toast.makeText(getApplicationContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();

                        } else {
                            if (noidung == "true") {
                                StaticVariable.username = edtUsername.getText().toString();
                                StaticVariable.passWord = edtPassword.getText().toString();
                                Toast.makeText(getApplicationContext(), "You have successfully signed up", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                SignUpActivity.super.finish();
                            } else
                                Toast.makeText(getApplicationContext(), "Username already exists please choose another", Toast.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {
                        return;
                    }


                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
