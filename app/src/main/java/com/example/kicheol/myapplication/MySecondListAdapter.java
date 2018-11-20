package com.example.kicheol.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MySecondListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Friend_list_Item> list_itemArrayList;
    ViewHolder_Friend_List viewholder;

    int resourceId ;
    private ListBtnClickListener listBtnClickListener ;


    public interface ListBtnClickListener {
        void OnlistBtnClick(int position);
    }

    public MySecondListAdapter(Context context, ArrayList<Friend_list_Item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
        //notifyDataSetChanged();

        //this.resourceId = resource ;
        //this.listBtnClickListener = clickListener ;


    }

    @Override
    public int getCount() {        //리스트뷰가 가지고있는 아이템 겟수
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {              //반복으로 돌려주는곳?
        if(convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.second_item,null);   // xml 불러오기
            viewholder = new ViewHolder_Friend_List();
            viewholder.nickname_textView = (TextView)convertView.findViewById(R.id.friendName);
            //viewholder.profile_imageView = (ImageView)convertView.findViewById(R.id.profile_imageView);

            convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder_Friend_List)convertView.getTag();
        }

        viewholder.nickname_textView.setText(list_itemArrayList.get(position).getId());
        //Glide.with(context).load(list_itemArrayList.get(position).getProfile_image()).into(viewholder.profile_imageView);
        return convertView;
    }




    class ViewHolder_Friend_List{
        TextView nickname_textView;
        //ImageView profile_imageView;
    }

}
