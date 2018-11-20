package com.example.kicheol.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<list_item> list_itemArrayList;
    ViewHolder viewholder;

    int resourceId ;
    private ListBtnClickListener listBtnClickListener ;


    public interface ListBtnClickListener {
        void OnlistBtnClick(int position);
    }

    public MyListAdapter(Context context, ArrayList<list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
        notifyDataSetChanged();

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

            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);   // xml 불러오기
            viewholder = new ViewHolder();
            viewholder.nickname_textView = (TextView)convertView.findViewById(R.id.nickname_textview);
            viewholder.content_textView = (TextView)convertView.findViewById(R.id.content_textview);
            viewholder.date_textView = (TextView)convertView.findViewById(R.id.date_textview);
            viewholder.title_textView  =(TextView)convertView.findViewById(R.id.title_textview);
            viewholder.profile_imageView = (ImageView)convertView.findViewById(R.id.profile_imageView);

            convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder)convertView.getTag();
        }

        viewholder.nickname_textView.setText(list_itemArrayList.get(position).getNickname());
        viewholder.title_textView.setText(list_itemArrayList.get(position).getTitle());
        viewholder.content_textView.setText(list_itemArrayList.get(position).getContent());
        viewholder.date_textView.setText(list_itemArrayList.get(position).getWrite_date().toString());
        Glide.with(context).load(list_itemArrayList.get(position).getProfile_image()).into(viewholder.profile_imageView);
        return convertView;
    }
}

class ViewHolder{
    TextView nickname_textView;
    TextView title_textView;
    TextView date_textView;
    TextView content_textView;
    ImageView profile_imageView;
}
