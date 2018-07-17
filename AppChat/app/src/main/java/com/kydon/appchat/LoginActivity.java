package com.kydon.appchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txtSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!isNetworkConnected()) {
            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        txtSignUp = findViewById(R.id.txtSignUp);

        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp.setOnClickListener(txtSignUp_OnClick);
        btnLogin.setOnClickListener(btnLogin_OnCLick);
        if (StaticVariable.username != null) {
            saveUsername(StaticVariable.username);
            savePass(StaticVariable.passWord);
        }
        edtUsername.setText(getUsername().toString());
        edtPassword.setText(getPass().toString());
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    View.OnClickListener txtSignUp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
            LoginActivity.super.finish();


        }
    };
    View.OnClickListener btnLogin_OnCLick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent2 = new Intent(getApplicationContext(), ListChatAdapter.class);
            intent2.putExtra("username", edtUsername.getText().toString());
            if (edtPassword.getText().toString().equals(getPass().toString()) && edtUsername.getText().toString().equals(getUsername().toString()) && !edtUsername.getText().toString().equals("")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("username", edtUsername.getText().toString());
                startActivity(intent);
                LoginActivity.super.finish();

            } else
                Toast.makeText(getApplicationContext(), "Username or password is incorrect", Toast.LENGTH_SHORT).show();


        }
    };

    public void saveUsername(String info) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getPreferences(MODE_PRIVATE);
        editor = settings.edit();
        editor.putString("findingemail", info);
        editor.commit();
    }

    public void savePass(String info) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getPreferences(MODE_PRIVATE);
        editor = settings.edit();
        editor.putString("findingpass", info);
        editor.commit();
    }

    private String getUsername() {
        SharedPreferences settings;
        settings = this.getPreferences(MODE_PRIVATE);
        String val = settings.getString("findingemail", "");
        return val;

    }

    private String getPass() {
        SharedPreferences settings;
        settings = this.getPreferences(MODE_PRIVATE);
        String val = settings.getString("findingpass", "");
        return val;

    }


}
