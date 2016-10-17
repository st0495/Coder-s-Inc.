package com.example.iway.codersinc.LoginSignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iway.codersinc.Contests.ConnectivityReceiver;
import com.example.iway.codersinc.MainActivity;
import com.example.iway.codersinc.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IWAY on 26-09-2016.
 */
public class SignupActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    public static final String status = "loggedin";
    public String username,password;
    SharedPreferences sharedpreferences;

    private Button btn_register;

    private static final String REGISTER_URL = "http://shashank.net23.net/register.php";
    //Signin button
    private SignInButton signInButton;

    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        sharedpreferences =getSharedPreferences("loginPrefs", MODE_PRIVATE);
        Boolean ankit=sharedpreferences.getBoolean(status,false);
        if (ankit) {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            intent.putExtra("USERNAME", editTextUsername.getText().toString());
            startActivity(intent);
            finish();
        }
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        btn_register = (Button) findViewById(R.id.submit_btn_register);
        btn_register.setOnClickListener(this);
        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //Setting onclick listener to signing button
        signInButton.setOnClickListener(this);
    }
    //This function will option signing intent
    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }
    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            //Displaying name and email
            //(acct.getDisplayName());
            //(acct.getEmail());
            String name = (acct.getDisplayName());
            int y=name.indexOf(' ');
            name=(name.substring(0,y)).trim();
            String username =name;
            String password =name;
            String email = (acct.getEmail()).trim();
            Log.e(name, email);


            register(name, username, password, email);




        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {
        if(v == btn_register){
            checkConnection();
        }
        if (v == signInButton) {
            //Calling signin
            signIn();
        }

    }

    public void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    public void showSnack(boolean isConnected) {
        if (isConnected) {
           /* private void sendMail(String email, String subject, String messageBody) {
                Session session = createSessionObject();

                try {
                    Message message = createMessage(email, subject, messageBody, session);
                    new SendMailTask().execute(message);
                } catch (AddressException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }*/
            registerUser();
        }
        else{
            Toast.makeText(SignupActivity.this, "No Internet Access", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        if (name != null && username != null && password != null && email != null) {
            if (isValidEmail(email) && isValidPassword(password)) {
                register(name, username, password, email);
            } else {
                if (isValidEmail(email)) {
                    Toast.makeText(SignupActivity.this, "password is too short", Toast.LENGTH_SHORT).show();
                } else if (isValidPassword(password)) {
                    Toast.makeText(SignupActivity.this, "Please Provide a Correct email id", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "password iss too short And email id is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(SignupActivity.this, "Please, Fill all the values", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }



    //sending mail
    /*private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SignupActivity.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/


    private void register(String name, String username, String password, String email) {
        String urlSuffix = "?name="+name+"&username="+username+"&password="+password+"&email="+email;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignupActivity.this, "Please Wait", null, true, true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("successfully registered"))
                {
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(status, true);
                    editor.commit();
                    intent.putExtra("USERNAME","ankit");
                    SignupActivity.this.startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;

                    result = bufferedReader.readLine();

                    if(result=="successfully registered")
                    {
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        SignupActivity.this.startActivity(intent);
                        return result;
                    }
                    else
                    {
                        return result;
                    }

                }catch(Exception e){
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
