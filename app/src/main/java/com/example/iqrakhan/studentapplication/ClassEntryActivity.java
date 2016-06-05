package com.example.iqrakhan.studentapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;

public class ClassEntryActivity extends AppCompatActivity {

    EditText etClassName, etDeptName, etProgram, etSession, etSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etClassName = (EditText) findViewById(R.id.edittext_className);
        etDeptName = (EditText) findViewById(R.id.edittext_deptName);
        etProgram = (EditText) findViewById(R.id.edittext_program);
        etSession = (EditText) findViewById(R.id.edittext_session);
        etSemester = (EditText) findViewById(R.id.edittext_semester);

        final RequestQueue requestQueue = Volley.newRequestQueue(ClassEntryActivity.this);
        final ProgressDialog progressDialog = new ProgressDialog(ClassEntryActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please Wait...");
                String className = etClassName.getText().toString();
                String deptName = etDeptName.getText().toString();
                String program = etProgram.getText().toString();
                String session = etSession.getText().toString();
                String semester = etSemester.getText().toString();
                progressDialog.show();
                String query = "INSERT INTO class (class_name , dept_name, program, session, semester) VALUES ('"+className+"','"+deptName+"','"+program+"','"+session+"','"+semester+"')";
                String url = Dbutils.nonQueryPath + URLEncoder.encode(query);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if(response.equals("1")){
                            Toast.makeText(ClassEntryActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ClassEntryActivity.this,ClassesActivity.class));
                            finish();
                        }else if(response.equals("0")){
                            Toast.makeText(ClassEntryActivity.this, "There is some problem. :(", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ClassEntryActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClassEntryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);
            }
        });
    }

}
