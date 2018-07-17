package com.kydon.appchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class ListChatAdapter extends BaseAdapter {

    ArrayList<String> arrChat;
    LayoutInflater inflater;
    Context context;

    public ListChatAdapter(ArrayList<String> arrChat, LayoutInflater inflater) {
        this.arrChat = arrChat;

        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return arrChat.size();
    }

    @Override
    public Object getItem(int i) {
        return arrChat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_chat, null, false);
            holder = new ViewHolder();
            holder.imgAvt = view.findViewById(R.id.imgAvt);
            holder.txtMessage = view.findViewById(R.id.txtMessage);
            holder.txtUserName = view.findViewById(R.id.txtUserName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.imgAvt.setImageResource(R.drawable.avt);
        holder.txtMessage.setText(arrChat.get(i));
        //holder.txtUserName.setText(StaticVariable.username);
        return view;
    }
    private Emitter.Listener onReceiptUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject obj = (JSONObject) args[0];
            String user = obj.optString("username");

        }
    } ;
    private class ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView imgAvt;
        TextView txtMessage, txtUserName;
    }
}
