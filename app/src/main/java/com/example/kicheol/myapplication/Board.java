package com.example.kicheol.myapplication;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
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


public class Board extends Fragment implements MainActivity.onKeyBackPressedListener{

    ListView listView;
    MyBoardListAdapter myListAdapter;
    ArrayList<Board_Item> list_Board;
    EditText edit;
    String table_num;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.board, container, false);

        listView = (ListView)rootView.findViewById(R.id.board_listView);
        list_Board = new ArrayList<Board_Item>();
        table_num = getArguments().getString("table_num");    //테이블번호 받기


        Board_setting bs = new Board_setting();             //db에서 데이터 받아서 리스트에 넣기
        bs.execute(table_num);

        Button btn_Write = (Button) rootView.findViewById(R.id.btn_write);

        btn_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment write = new Write();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();


                Bundle bundle = new Bundle();  //인자 넘겨주기
                bundle.putString("table_num", table_num);
                String id = getArguments().getString("id");

                bundle.putString("id", id);
                write.setArguments(bundle);



                transaction.addToBackStack(null);
                transaction.add(R.id.write_layout , write).commit();


            }
        });


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);


        return rootView;
    }
/////////////////통신
    class Board_setting extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {                //배열에 db값 넣기
            super.onPostExecute(s);


            try {
                JSONObject jSon = new JSONObject(s);
                JSONArray jArray = jSon.getJSONArray("result");

                String id;
                String date;
                String content;
                for(int i = 0; i < jArray.length(); i++){
                    JSONObject jo = jArray.getJSONObject(i);
                    id = jo.getString("id");
                    date = jo.getString("date");
                    content = jo.getString("content");
                    list_Board.add(new Board_Item(id,date,content));

                }
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
/////////////////뒤로키 눌러도 안꺼지게하는거
    public void onBack() {

        getFragmentManager().popBackStack();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).pushOnBackKeyPressedListener(this);
    }
////////////////////////////////////////////////
}
