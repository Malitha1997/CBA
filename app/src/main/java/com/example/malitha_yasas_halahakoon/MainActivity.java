package com.example.malitha_yasas_halahakoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        //Prepare the request body (if required)
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", "test");
            requestBody.put("password", "Test123");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Create a JSON media type
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Create the request
        Request request = new Request.Builder()
                .url("http://123.231.9.43:3999")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic " + Base64.encodeToString("test:Test123".getBytes(), Base64.NO_WRAP))
                .post(RequestBody.create(mediaType, requestBody.toString()))
                .build();

        //Send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Handle the request failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Check if the response was successful (HTTP status code 200)
                if (response.isSuccessful()) {
                    //Parse the response JSON
                    String responseBody = response.body().string();
                    Log.d(TAG, "Response: " + responseBody);

                    try {
                        //Extract the data from the response JSON
                        JSONObject jsonResponse = new JSONObject(responseBody);

                        int resCode = jsonResponse.getInt("res_code");
                        String resDesc = jsonResponse.getString("res_desc");
                        JSONObject userData = jsonResponse.getJSONObject("user_data");

                        if(resCode == 0) {
                            String userId = userData.getString("id");
                            String email = userData.getString("email");
                            String name = userData.getString("name");
                            String dob = userData.getString("dob");
                            String gender = userData.getString("gender");
                            String company = userData.getString("company");
                            String position = userData.getString("position");

                            int responseCode = 0;
                            String responseDescription = "success";
                            String userID = "ID001";
                            String userEmail = "test@cba.lk";
                            String userName = "Test User CBA";
                            String userDOB = "1971-01-01";
                            String userGender = "MALE";
                            String userCompany = "CBA - Dehiwala";
                            String userPosition = "Trainee SE";

                            //Process the extracted data as needed
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Update UI elements with the response data

                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Handle the error case
                    Log.e(TAG, "Request failed with status code: " + response.code());
                }
            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().trim().isEmpty()){
                    username.setError("Username should not be empty");
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sucessful!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().trim().isEmpty()){
                    password.setError("Password should not be empty");
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sucessful!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Validation
    private boolean validateLoginUsername(){
        String val = username.getText().toString();
        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        }else{
            username.setError(null);
            return true;
        }
    }

    private boolean validateLoginPassword(){
        String val = password.getText().toString();
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        }else{
            password.setError(null);
            return true;
        }
    }

    public void LoginScreen(View view) {
        if (validateLoginUsername() && validateLoginPassword()) {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
        } else {
            username.setError("Invalid Username");
            password.setError("Invalid Password");
        }
    }
}