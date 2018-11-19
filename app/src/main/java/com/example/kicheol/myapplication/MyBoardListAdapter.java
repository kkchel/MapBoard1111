package com.example.kicheol.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyBoardListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Board_Item> list_BoardArray;
    ViewHolder_Board viewholder;

    public MyBoardListAdapter(Context context, ArrayList<Board_Item> list_BoardArray) {
        this.context = context;
        this.list_BoardArray = list_BoardArray;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return this.list_BoardArray.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return  this.list_BoardArray.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {              //반복으로 돌려주는곳?

            convertView = LayoutInflater.from(context).inflate(R.layout.board_item,null);   // xml 불러오기
            viewholder = new ViewHolder_Board();
            viewholder.id_textView = (TextView)convertView.findViewById(R.id.board_item_id);
            viewholder.content_textView = (TextView)convertView.findViewById(R.id.board_item_content);
            viewholder.date_textView = (TextView)convertView.findViewById(R.id.board_item_date);
            //viewholder.profile_imageView = (ImageView)convertView.findViewById(R.id.profile_imageView);
            convertView.setTag(viewholder);

        viewholder.id_textView.setText(list_BoardArray.get(position).getId());
        viewholder.content_textView.setText(list_BoardArray.get(position).getContent());
        viewholder.date_textView.setText(list_BoardArray.get(position).getDate());
        //Glide.with(context).load(list_BoardArray.get(position).getProfile_image()).into(viewholder.profile_imageView);   //이미지 추후
        return convertView;
    }
}

class ViewHolder_Board{
    TextView id_textView;
    TextView date_textView;
    TextView content_textView;
    //ImageView profile_imageView;
}
