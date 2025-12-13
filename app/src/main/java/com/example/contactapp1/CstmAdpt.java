package com.example.contactapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CstmAdpt extends BaseAdapter {
    private Context context;
    private List<User> UsersList;
    private LayoutInflater inflater;
    public CstmAdpt(Context context, List<User> usersList) {
        this.context = context;
        this.UsersList = usersList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return UsersList.size();
    }
    @Override
    public Object getItem(int position) {
        return UsersList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_item, null);
        }
        TextView NameTextV = convertView.findViewById(R.id.NameTeextV);
        TextView TelTextV = convertView.findViewById(R.id.TelTeextV);
        NameTextV.setText("Name: " + UsersList.get(position).Name);
        TelTextV.setText("Tel: " + UsersList.get(position).Tel);
        return convertView;
    }
    public void deleteItem(int position) {
        BaseCont dataBase=new BaseCont(context,"ContactAppBase.db",null,1);
        dataBase.DeleteContact(UsersList.get(position).Name,UsersList.get(position).Tel);
        UsersList.remove(position);
        notifyDataSetChanged();
    }
}
