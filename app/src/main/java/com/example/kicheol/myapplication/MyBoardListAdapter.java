package com.example.kicheol.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MyBoardListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Board_Item> list_BoardArray;
    ViewHolder_Board viewholder;
    String friend_id;

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
            //convertView.setTag(viewholder);
        viewholder.id_textView.setTag(position);
        viewholder.id_textView.setText(list_BoardArray.get(position).getId());
        viewholder.content_textView.setText(list_BoardArray.get(position).getContent());
        viewholder.date_textView.setText(list_BoardArray.get(position).getDate());

            viewholder.id_textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    final int a = Integer.parseInt(""+v.getTag());


                    alertDialogBuilder.setTitle("팔로우하시겠습니까?");        //제목
                    alertDialogBuilder//.setMessage("프로그램을 종료할 것입니까?")  //내용
                            .setCancelable(false)
                            .setPositiveButton("예",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {


                                            //friend_id = convertView.getTag() id_textView.getText().toString();


                                            Friend_Insert fi = new Friend_Insert();
                                            TextView tx = ((MainActivity)context).findViewById(R.id.secret_id);
                                            fi.execute(tx.getText().toString(), list_BoardArray.get(a).getId());



                                        }
                                    })
                            .setNegativeButton("아니요",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 다이얼로그를 취소한다
                                            dialog.cancel();
                                        }
                                    });

                    // 다이얼로그 생성
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // 다이얼로그 보여주기
                    alertDialog.show();
                }
            });



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

////////////통신/////////////////
class Friend_Insert extends AsyncTask<String, Void, String> {
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jSon = new JSONObject(s);
            JSONArray jArray = jSon.getJSONArray("result");

            JSONObject jo = jArray.getJSONObject(0);

        }catch(Exception e){}


    }
    @Override
    protected String doInBackground(String... params) {
        try {

            String tmp1 = params[0];
            String tmp2 = params[1];


            String data = URLEncoder.encode("tmp1","UTF-8")+"="+URLEncoder.encode(tmp1,"UTF-8"); // "tmp1"=tmp1;
            data += "&" + URLEncoder.encode("tmp2", "UTF-8") + "=" + URLEncoder.encode(tmp2, "UTF-8");


            URL url = new URL("http://kimki.iptime.org/friend_insert.php");


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
