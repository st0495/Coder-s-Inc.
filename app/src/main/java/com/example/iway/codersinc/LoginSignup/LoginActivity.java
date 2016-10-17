package com.example.iway.codersinc.LoginSignup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iway.codersinc.Contests.ConnectivityReceiver;
import com.example.iway.codersinc.LoginSignup.SignupActivity;
import com.example.iway.codersinc.MainActivity;
import com.example.iway.codersinc.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IWAY on 26-09-2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textView;
    private Button btn_login;
    private TextView text_signup;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String status = "loggedin";
    public String username,password;
    SharedPreferences sharedpreferences;
    private static final String LOGIN_URL = "http://shashank.net23.net/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        sharedpreferences =getSharedPreferences("loginPrefs", MODE_PRIVATE);
        //editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        //editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        Boolean ankit=sharedpreferences.getBoolean(status,false);
        if (ankit) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("USERNAME", editTextUsername.getText().toString());
            startActivity(intent);
            finish();
        }
        btn_login= (Button) findViewById(R.id.button_login);
        btn_login.setOnClickListener(this);

       text_signup = (TextView) findViewById(R.id.text_signup);
        text_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btn_login){

            checkConnection();
        }
        else if(v == text_signup)
        {
            Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
            LoginActivity.this.startActivity(intent);

        }
    }

   /* private void loginUser() {
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();

        login(username, password);
    }*/
    public void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    public void showSnack(boolean isConnected){
        if(isConnected){
            String username = editTextUsername.getText().toString().trim().toLowerCase();
            String password = editTextPassword.getText().toString().trim().toLowerCase();
            if(username!=null&&password!=null) {
                setUsername(username);
                setPassword(password);
                login(username, password);
            }
            else if(username!=null){
                Toast.makeText(LoginActivity.this, "Please input your password", Toast.LENGTH_SHORT).show();
            }
            else if(password!=null){
                Toast.makeText(LoginActivity.this, "please input your correct password", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(LoginActivity.this, "please fill the details carefully", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(LoginActivity.this, "No Internet Access  ", Toast.LENGTH_SHORT).show();
        }

    }



    private void login( String username, String password) {
        String urlSuffix = "?&username="+username+"&password="+password;

        class loginUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("login successful"))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(status, true);
                    //editor.putString(Password,getPassword());
                    editor.commit();
                    intent.putExtra("USERNAME","ankit");
                    LoginActivity.this.startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(LOGIN_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }

        loginUser ru = new loginUser();
        ru.execute(urlSuffix);
    }
    public String getUsername(){ return username;}
    public String getPassword(){ return password; }
    public void setUsername(String username){ this.username=username;  }
    public void setPassword(String password) { this.password=password; }
}
