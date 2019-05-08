package com.example.myvrapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Temple> {
   private final Activity context;
    List<Temple> temples;

    public CustomListAdapter(Activity context,List<Temple> temples)
    {
        super(context, R.layout.templerow_list, temples);
        this.context = context;
        this.temples = temples;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.templerow_list, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.TempleName);
        TextView cityTextField = (TextView) rowView.findViewById(R.id.TempleCity);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.TempleImage);

        //this code sets the values of the objects to values from the arrays
        Temple temple = temples.get(position);
        nameTextField.setText(temple.getName());
        cityTextField.setText(temple.getCity());
//        imageView.setImageResource(temple.getImage());
        Picasso.with(getContext()).load(temple.getImage().toString()).into(imageView);
        return rowView;

    };

}
