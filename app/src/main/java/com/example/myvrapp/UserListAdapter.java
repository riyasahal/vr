package com.example.myvrapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<UserInformation> {
    private final Activity context;
    private List<UserInformation> userInformationList;

    public UserListAdapter(Activity context, List<UserInformation> userInformationList) {
        super(context, R.layout.userrow_list, userInformationList);
        this.context = context;
        this.userInformationList = userInformationList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.userrow_list, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.userDisplayName);
        TextView emailTextField = (TextView) rowView.findViewById(R.id.userEmail);

        //this code sets the values of the objects to values from the arrays
        UserInformation userInformation = userInformationList.get(position);
        nameTextField.setText(userInformation.getName().toUpperCase());
        emailTextField.setText(userInformation.getEmail());
        return rowView;

    };

}
