package com.example.edittextspinner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends BaseAdapter {
    List<String> accounts;
    Context context;

    public AccountAdapter(List<String> accounts, Context context) {
        this.accounts = accounts == null ? new ArrayList<String>() : accounts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public String getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView= convertView != null ? convertView : View.inflate(context, android.R.layout.simple_list_item_1, null);
        TextView tv = itemView.findViewById(android.R.id.text1);
        tv.setText(accounts.get(position));
        return itemView;
    }

    public void addItem(String account){

        accounts.add(account);

        notifyDataSetChanged();
    }


}
