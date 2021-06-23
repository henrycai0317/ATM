package com.henry.atm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText edUderid;
    private EditText edPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUderid = findViewById(R.id.userid);
        edPasswd = findViewById(R.id.passwd);
    }

    public void login(View view){
        String userid = edUderid.getText().toString();
        String passwd = edPasswd.getText().toString();
        if("Henry".equals(userid) && "1234".equals(passwd)){
            setResult(RESULT_OK);
            finish();
        }

    }
    public  void quit(View view){

    }
}