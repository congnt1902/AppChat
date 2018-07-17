package com.kydon.appchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    ArrayList<String> arrUserName;
    LayoutInflater inflater;

    public ContactAdapter(ArrayList<String> arrUserName, LayoutInflater inflater) {
        this.arrUserName = arrUserName;

        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return arrUserName.size();
    }

    @Override
    public Object getItem(int i) {
        return arrUserName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_contact, null, false);
            holder = new ViewHolder();

            holder.txtUserName = view.findViewById(R.id.txtUserName);
            holder.imgAvt = view.findViewById(R.id.imgAvt);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txtUserName.setText(arrUserName.get(i));
        holder.imgAvt.setImageResource(R.drawable.avt);

        return view;
    }

    public void addUserOnline(String user) {
        if (user != null || !arrUserName.contains(user)) {
            arrUserName.add(user);
            notifyDataSetChanged();
        }


    }

    public void removeUserOffline(String user) {
        if (user != null) {
            arrUserName.remove(user);
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView imgAvt;
        TextView txtUserName;
    }
}
