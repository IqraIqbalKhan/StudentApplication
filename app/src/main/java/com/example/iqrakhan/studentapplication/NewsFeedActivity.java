package com.example.iqrakhan.studentapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , BaseSliderView.OnSliderClickListener {

    ListView lvNewsFeed;
    NewsAdapter newsAdapter;
    List<News> newsList;
    private SliderLayout mDemoSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lvNewsFeed = (ListView) findViewById(R.id.listview_news);

        View headerView = View.inflate(NewsFeedActivity.this,R.layout.slider_header_layout, null);
        lvNewsFeed.addHeaderView(headerView);

        newsList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(NewsFeedActivity.this);
        String query = "SELECT * FROM news";
        String url = Dbutils.queryPath + URLEncoder.encode(query);
        // Log.i("myTag",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    if (jsonArray.length() == 0) {
                        Toast.makeText(NewsFeedActivity.this, "no news found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject newsJsonObject = jsonArray.getJSONObject(i);
                        int newsId = newsJsonObject.getInt("newsId");
                        String newsHeading = newsJsonObject.getString("newsHeading");
                        String newsDetail = newsJsonObject.getString("newsDetail");
                        String imagePath = newsJsonObject.getString("imagePath");

                        News news = new News(newsId, newsHeading, newsDetail, imagePath);
                        newsList.add(news);
                    }
                    newsAdapter = new NewsAdapter(NewsFeedActivity.this, newsList);
                    lvNewsFeed.setAdapter(newsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(NewsFeedActivity.this, "error: " + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewsFeedActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

        mDemoSlider = (SliderLayout) headerView.findViewById(R.id.slider);
        HashMap<String, Integer> url_maps = new HashMap<String, Integer>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        url_maps.put("Career Counceling Seminar",R.drawable.career_counseling);
        url_maps.put("CSC Distribution Ceremony", R.drawable.csc_distribution);
        url_maps.put("Android WorkShop",R.drawable.android);
        url_maps.put("Thesis Seminar", R.drawable.thesis_statement);
        url_maps.put("Annual Launch Ceremony", R.drawable.annual_launch);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(2000);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);



        Intent receivedIntent = getIntent();
        // String imagePath = receivedIntent.getStringExtra("imagePath");
        String username = receivedIntent.getStringExtra("username");


        //   ImageView imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        TextView tvUsername = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textview_username);
        tvUsername.setText(username);

        //   Picasso.with(NewsFeedActivity.this).load(Dbutils.imagesBasePath + imagePath).fit().into(imageView);


    }
    @Override
    protected void onPause() {
        super.onPause();
        mDemoSlider.stopAutoCycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDemoSlider.startAutoCycle();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
