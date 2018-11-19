package com.example.kicheol.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class Board extends Fragment {

    ListView listView;
    MyBoardListAdapter myListAdapter;
    ArrayList<Board_Item> list_Board;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.board, container, false);

        listView = (ListView)rootView.findViewById(R.id.board_listView);
        list_Board = new ArrayList<Board_Item>();

        String table_num = "2"; //= getArguments().getString("table_num");    //테이블번호 받기

        Board_setting bs = new Board_setting();             //db에서 데이터 받아서 리스트에 넣기
        bs.execute(table_num);


        FragmentTransaction ft = getFragmentManager().beginTransaction();



        return rootView;
    }

    class Board_setting extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject jSon = new JSONObject(s);
                JSONArray jArray = jSon.getJSONArray("result");

                String id;
                String date;
                String content;
                for(int i = 0; i < jSon.length(); i++){
                    JSONObject jo = jArray.getJSONObject(i);
                    id = jo.getString("id");
                    date = jo.getString("date");
                    content = jo.getString("content");
                    list_Board.add(new Board_Item(id,date,content));

                    Log.e("asdf", jo.getString("id"));
                }
                Log.e("asdf", "dd" + jSon.length());
            } catch(Exception e){
                ;
            }

            myListAdapter = new MyBoardListAdapter( getActivity(), list_Board);
            listView.setAdapter(myListAdapter);

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tmp1 = params[0];

                String data = URLEncoder.encode("tmp1","UTF-8")+"="+URLEncoder.encode(tmp1,"UTF-8"); // "tmp1"=tmp1;

                URL url = new URL("http://kimki.iptime.org/table_set.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoInput(true); // Allow Inputs
                con.setDoOutput(true); // Allow Outputs
                con.setUseCaches(false); // Don't use a Cached Copy
                con.setRequestMethod("POST");

                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                wr.write(data);           //데이터
                wr.flush();              //전송

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8")); //데이터 받기
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line= reader.readLine()) != null){
                    sb.append(line + "\n");
                    Log.e("asdf",sb.toString());
                }

                con.disconnect();

                return sb.toString();         //리턴값 정하기

            } catch(Exception e) {};
            return null;
        }
    }
}
