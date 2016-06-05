package com.example.iqrakhan.studentapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

public class ClassesActivity extends AppCompatActivity {

    ListView lvClasses;
    ClassesAdapter classAdapter;
    List<Classes> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvClasses = (ListView) findViewById(R.id.listview_classes);
        classList = new ArrayList<>();

        final ProgressDialog progressDialog = new ProgressDialog(ClassesActivity.this);
        progressDialog.setMessage("loading data");

        RequestQueue requestQueue = Volley.newRequestQueue(ClassesActivity.this);
        String query = "SELECT * FROM class";
        String url = Dbutils.viewClassPath + URLEncoder.encode(query);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("classList");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject classObject = jsonArray.getJSONObject(i);

                        Integer classId = classObject.getInt("classId");
                        String className = classObject.getString("className");
                        String semester = classObject.getString("semester");
                        String session = classObject.getString("session");

                        Classes classes = new Classes(classId, className, semester, session);

                        classList.add(classes);
                    }

                    classAdapter = new ClassesAdapter(ClassesActivity.this, classList);
                    lvClasses.setAdapter(classAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ClassesActivity.this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.show();
        requestQueue.add(stringRequest);

        lvClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassesActivity.this, NewsFeedActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClassesActivity.this, ClassEntryActivity.class));
                finish();
            }
        });
        registerForContextMenu(lvClasses);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final RequestQueue requestQueue = Volley.newRequestQueue(ClassesActivity.this);
        if (item.getTitle().equals("Edit")){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final Classes selectedClass = classList.get(info.position);
            //Toast.makeText(ClassesActivity.this, "here working", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(ClassesActivity.this);
            builder.setTitle("Edit Class");
            builder.setMessage("Fill the following fields.");
            final View view = View.inflate(ClassesActivity.this,R.layout.update_class_layout,null);
            builder.setView(view);
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    EditText etClassName = (EditText) view.findViewById(R.id.edittext_className);
                    EditText etDeptName = (EditText) view.findViewById(R.id.edittext_deptName);
                    EditText etProgram = (EditText) view.findViewById(R.id.edittext_program);
                    EditText etSession = (EditText) view.findViewById(R.id.edittext_session);
                    EditText etSemester = (EditText) view.findViewById(R.id.edittext_semester);
                    String className = etClassName.getText().toString();
                    String deptName = etDeptName.getText().toString();
                    String program = etProgram.getText().toString();
                    String semester = etSemester.getText().toString();
                    String session = etSession.getText().toString();
                    Integer id = selectedClass.getClassId();
                    String query = "UPDATE class SET class_name='"+className+"',dept_name='"+deptName+"', program='"+program+"', session='"+session+"', semester='"+semester+"' WHERE class_id="+id+"";
                    String url = Dbutils.nonQueryPath + URLEncoder.encode(query);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1")){
                                Toast.makeText(ClassesActivity.this, "Data update successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ClassesActivity.this,ClassesActivity.class));
                                finish();
                            }else if(response.equals("0")){
                                Toast.makeText(ClassesActivity.this, "There is some problem. :(", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ClassesActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ClassesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(stringRequest);
                }
            });
            builder.setNegativeButton("Cancel",null);
            builder.show();

        }else if(item.getTitle().equals("Delete")){
           // Toast.makeText(ClassesActivity.this, "niceeeeee", Toast.LENGTH_SHORT).show();
            //delete option selected
            //you should display a confirmation dialog before deleting data
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final Classes selectedClass = classList.get(info.position);
            AlertDialog.Builder builder = new AlertDialog.Builder(ClassesActivity.this);
            builder.setTitle("Confirmation")
                    .setMessage("Do you really want to delete this task?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // db.delete("task", "id=" + selectedTask.getId(), null);
                            String query = "DELETE FROM class WHERE class_id="+selectedClass.getClassId()+"";
                            String url = Dbutils.nonQueryPath + URLEncoder.encode(query);
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("1")){
                                        Toast.makeText(ClassesActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ClassesActivity.this,ClassesActivity.class));
                                        finish();
                                    }else if(response.equals("0")){
                                        Toast.makeText(ClassesActivity.this, "There is some problem. :(", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(ClassesActivity.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ClassesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            requestQueue.add(stringRequest);
                        }
                    })
                    .setNegativeButton("No", null);

            builder.show();


        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Classes Structure");
        menu.add("Edit");
        menu.add("Delete");

    }
}
