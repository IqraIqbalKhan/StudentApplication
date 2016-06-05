package com.example.iqrakhan.studentapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Iqra Khan on 5/5/2016.
 */
public class NewsAdapter extends BaseAdapter {

    Context context;
    List<News> newsList;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public News getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      if(convertView == null){
          convertView = View.inflate(context, R.layout.news_item_layout, null);
      }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview);
        TextView tvNewsHeading = (TextView) convertView.findViewById(R.id.textview_news_heading);
        TextView tvNewsDetail = (TextView) convertView.findViewById(R.id.textview_news_detail);

        News currentNews = getItem(position);
        Picasso.with(context).load(Dbutils.imagesBasePath + currentNews.getImagePath()).into(imageView);
        tvNewsHeading.setText(currentNews.getNewsHeading());
        tvNewsDetail.setText(currentNews.getNewsDetail());

        return convertView;
    }
}
