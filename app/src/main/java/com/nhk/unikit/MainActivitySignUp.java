package com.nhk.unikit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClients;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivitySignUp extends AppCompatActivity {

    Button logBtn, signup;
    EditText emailInput, passInput,fullnameInput,phoneInput;

    String URL = "http://192.168.86.198/Unikit-Final/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);

        logBtn = (Button) findViewById(R.id.logPage);
        signup = (Button) findViewById(R.id.signBtn);

        emailInput = (EditText) findViewById(R.id.mailInput);
        passInput = (EditText) findViewById(R.id.passInput);
        fullnameInput = (EditText) findViewById(R.id.fullName);
        phoneInput = (EditText) findViewById(R.id.phoneNum);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivitySignUp.this, MainActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String result = signup(emailInput.getText().toString(), passInput.getText().toString(), fullnameInput.getText().toString(), phoneInput.getText().toString());
                    Log.e("result",result);
                    if (!result.equalsIgnoreCase("Success!")) {
                        startActivity(new Intent(MainActivitySignUp.this, MainActivity.class));
                    } else {
                        Toast.makeText(MainActivitySignUp.this, result, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String signup(final String email, final String password, String fullName, String phoneNumber) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(URL);

// Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("full_name", fullName));
        params.add(new BasicNameValuePair("phone_number", phoneNumber));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

//Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                String result = IOUtils.toString(instream, StandardCharsets.UTF_8);
                return result;
            }
        }else{
            throw new IOException("No Data found!");
        }
    }
}