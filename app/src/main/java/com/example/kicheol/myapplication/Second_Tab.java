package com.example.kicheol.myapplication;

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
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Second_Tab extends Fragment {
    ListView listView;
    MySecondListAdapter myListAdapter;
    ArrayList<Friend_list_Item> list_Board;
    String user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.second_tab, container, false);

        listView = rootView.findViewById(R.id.second_tab_list);
        list_Board  = new ArrayList<Friend_list_Item>();
        Friend_Setting bs = new Friend_Setting();
        user_id = getArguments().getString("id");
        bs.execute(user_id,"0");

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String friend_id = list_Board.get(position).getId();

                Fragment board = new Board_Friend();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();


                String ab = getArguments().getString("id");
                Bundle bundle = new Bundle();  //인자 넘겨주기
                bundle.putString("friend_id", friend_id);
                bundle.putString("id", ab);
                board.setArguments(bundle);

                transaction.addToBackStack(null);
                transaction.add(R.id.board_layout2, board).commit();

                return false;
            }
        });



        return rootView;
    }

    /////////////////통신
    class Friend_Setting extends AsyncTask<String, Void, String> {

        String param;
        @Override
        protected void onPostExecute(String s) {                //배열에 db값 넣기
            super.onPostExecute(s);

                try {
                    JSONObject jSon = new JSONObject(s);
                    JSONArray jArray = jSon.getJSONArray("result");

                    String id;

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jo = jArray.getJSONObject(i);
                        id = jo.getString("id");
                        list_Board.add(new Friend_list_Item(id));
                    }

                } catch (Exception e) {
                    ;
                }

                myListAdapter = new MySecondListAdapter(getActivity(), list_Board);
                listView.setAdapter(myListAdapter);


        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tmp1 = params[0];
                String tmp2 = params[1];
                param = tmp2;
                String data = URLEncoder.encode("tmp1","UTF-8")+"="+URLEncoder.encode(tmp1,"UTF-8"); // "tmp1"=tmp1;
                URL url;
                    url = new URL("http://kimki.iptime.org/friend_list.php");



                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoInput(true); // Allow Inputs
                con.setDoOutput(true); // Allow Outputs
                con.setUseCaches(false); // Don't use a Cached Copy
                //con.setRequestMethod("POST");                          //개쓰레기

                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                wr.write(data);           //데이터
                wr.flush();              //전송

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8")); //데이터 받기
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line= reader.readLine()) != null){
                    sb.append(line + "\n");
                }

                con.disconnect();

                return sb.toString().trim();         //리턴값 정하기

            } catch(Exception e) {};
            return null;
        }
    }
}
