package com.example.iqrakhan.studentapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    TextView tvSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etUsername = (EditText) findViewById(R.id.edittext_username);
        etPassword = (EditText) findViewById(R.id.edittext_password);
        tvSignUp = (TextView) findViewById(R.id.textview_signup);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });

        final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("please wait");
                progressDialog.show();
                //login
                String query = "SELECT * FROM user WHERE username='" + etUsername.getText().toString() + "' AND password='" + etPassword.getText().toString() + "'";
                String url = Dbutils.queryPath + URLEncoder.encode(query);
                StringRequest loginRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            if (jsonArray.length() == 0) {
                                Toast.makeText(LoginActivity.this, "invalid username/password", Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject userJSONObject = jsonArray.getJSONObject(0);
                                String username = userJSONObject.getString("username");
                                String password = userJSONObject.getString("password");
//                                int userType = userJSONObject.getInt("userType");
//                                String imagePath = userJSONObject.getString("imagePath");

                                User user = new User(username, password);
                                Intent intent = new Intent(LoginActivity.this, TDashboardActivity.class);
                                intent.putExtra("username", username);
//                                intent.putExtra("imagePath", imagePath);
//                                intent.putExtra("userType", userType);

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                sharedPreferences.edit().putBoolean("isUserLoggedIn", true).apply();

                                startActivity(intent);

                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "login failed because: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(loginRequest);

            }
        });
    }

}
