package com.nhk.unikit;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    Button regBtn, login;
    EditText emailInput, passInput;

    String URL = "http://192.168.86.198/Unikit-Final/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        regBtn = (Button) findViewById(R.id.regPage);
        login = (Button) findViewById(R.id.signBtn);
        emailInput = (EditText) findViewById(R.id.mailInput);
        passInput = (EditText) findViewById(R.id.passInput);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivitySignUp.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String result = login(emailInput.getText().toString(), passInput.getText().toString());
                    Log.e("result",result);
                    if (!result.equalsIgnoreCase("Invalid email or password")) {
                        goToItemsPage(result);
                    } else {
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void goToItemsPage(String id) {
        Intent i = new Intent(MainActivity.this, ItemsPage.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    private String login(final String email, final String password) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(URL);

// Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
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