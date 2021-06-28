package com.henry.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edUderid;
    private EditText edPasswd;
    private  static final String TAG=LoginActivity.class.getSimpleName();
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSharedPreferences("atm",MODE_PRIVATE)
                .edit()
                .putInt("LEVEL",3)
                .putString("NAME","Henry")
                .commit();
        int level = getSharedPreferences("atm",MODE_PRIVATE)
                .getInt("LEVEL",0);
        Log.d(TAG, "onCreate: "+level);
        edUderid = findViewById(R.id.userid);
        edPasswd = findViewById(R.id.passwd);
        cbRemember = findViewById(R.id.cb_rem_userid);
        cbRemember.setChecked(
                getSharedPreferences("atm",MODE_PRIVATE)
                        .getBoolean("REMEMBER_USERID",false));
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getSharedPreferences("atm",MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_USERID",b)
                        .apply();
            }
        });
        String userid = getSharedPreferences("atm",MODE_PRIVATE)
                .getString("USERID","");
        edUderid.setText(userid);
    }

    public void login(View view){

       final String userid = edUderid.getText().toString();
       final String passwd = edPasswd.getText().toString();

        FirebaseDatabase.getInstance().getReference("users").child(userid).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                          String pw = (String) snapshot.getValue();
                          if(pw.equals(passwd)){
                              boolean remember = getSharedPreferences("atm",MODE_PRIVATE)
                                      .getBoolean("REMEMBER_USERID",false);
                              //save userid
                              if(remember){
                              getSharedPreferences("atm",MODE_PRIVATE)
                                      .edit()
                                      .putString("USERID",userid)
                                      .apply();
                              }
                              setResult(RESULT_OK);
                              finish();
                          }else {
                              new AlertDialog.Builder(LoginActivity.this)
                                      .setTitle("登入結果")
                                      .setMessage("登入失敗")
                                      .setPositiveButton("OK",null)
                                      .show();

                          }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        /*if("Henry".equals(userid) && "1234".equals(passwd)){

        }*/

    }
    public  void quit(View view){

    }
}