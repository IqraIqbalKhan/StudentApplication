package com.example.iqrakhan.studentapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Iqra Khan on 6/2/2016.
 */
public class ClassesAdapter extends BaseAdapter{

    Context context;
    List<Classes> classList;

    public ClassesAdapter(Context context, List<Classes> classList) {
        this.context = context;
        this.classList = classList;
    }

    @Override
    public int getCount() {
        return classList.size();
    }

    @Override
    public Classes getItem(int position) {
        return classList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.student_classes_layout, null);
        }
        TextView tvclassId = (TextView) convertView.findViewById(R.id.textview_id);
        TextView tvClassName = (TextView) convertView.findViewById(R.id.textview_classname);
        TextView tvSemester = (TextView) convertView.findViewById(R.id.textview_semester);
        TextView tvSession = (TextView) convertView.findViewById(R.id.textview_session);


        Classes currentClass = getItem(position);

        tvclassId.setText(""+currentClass.getClassId());
        tvClassName.setText(currentClass.getClassName());
        tvSemester.setText(currentClass.getSemester());
        tvSession.setText(currentClass.getSession());

        return convertView;
    }
}
