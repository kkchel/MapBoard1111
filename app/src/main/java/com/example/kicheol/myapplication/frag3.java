package com.example.kicheol.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class frag3 extends Fragment {

    ListView listView;
    MyListAdapter myListAdapter;
    ArrayList<list_item> list_itemArrayList;    //나중에 DB로

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.third_menu, container, false);
        listView = (ListView)rootView.findViewById(R.id.my_listview);

        list_itemArrayList = new ArrayList<list_item>();
        list_itemArrayList.add(new list_item("https://cdn-images-1.medium.com/fit/c/36/36/0*HgJ2Psmia7PjQsp9.jpg",
                "보라돌이","제목1",new Date(System.currentTimeMillis()),"내용1"));
        list_itemArrayList.add(new list_item("https://cdn-images-1.medium.com/fit/c/36/36/0*HgJ2Psmia7PjQsp9.jpg",
                "뚜비","제목2",new Date(System.currentTimeMillis()),"내용2"));
        list_itemArrayList.add(new list_item("https://cdn-images-1.medium.com/fit/c/36/36/0*HgJ2Psmia7PjQsp9.jpg",
                "나나","제목3",new Date(System.currentTimeMillis()),"내용3"));
        list_itemArrayList.add(new list_item("https://cdn-images-1.medium.com/fit/c/36/36/0*HgJ2Psmia7PjQsp9.jpg",
                "뽀","제목4",new Date(System.currentTimeMillis()),"내용4"));

        list_itemArrayList.add(new list_item(
                ""
                , "햇님"
                ,"제목5"
                ,new Date(System.currentTimeMillis())
                ,"내용5"));

        myListAdapter = new MyListAdapter(getActivity(), list_itemArrayList);
        listView.setAdapter(myListAdapter);
        TextView a = (TextView)rootView.findViewById(R.id.tv);
        a.setText(":asdfasdf");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {                         // 클릭시 이벤트
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ;
            }
        });


        return rootView;
    }
}
