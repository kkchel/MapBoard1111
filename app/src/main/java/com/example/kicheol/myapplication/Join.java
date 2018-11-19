package com.example.kicheol.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Join extends Activity {
    EditText edit_id;
    EditText edit_pw;
    EditText edit_name;

    private String urlPath;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);



        edit_id = (EditText) findViewById(R.id.edit_join_id);
        edit_pw = (EditText) findViewById(R.id.edit_join_pw);
        edit_name = (EditText) findViewById(R.id.edit_join_name);


        Button joinButton = (Button) findViewById(R.id.btn_join_complete);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tmp_id = edit_id.getText().toString();
                final String tmp_pw = edit_pw.getText().toString();
                final String tmp_name = edit_name.getText().toString();

                if( tmp_id.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하지않았습니다", Toast.LENGTH_SHORT).show();
                    return;
                } else if( tmp_pw.equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하지않았습니다", Toast.LENGTH_SHORT).show();
                    return;
                } else if( tmp_name.equals("")) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하지않았습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                Input tmp_Check = new Input();
                tmp_Check.execute(tmp_id, tmp_pw, tmp_name);
            }
        });

        Button backButton = (Button) findViewById(R.id.btn_join_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class Input extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("1")){             //성공
                setResult(RESULT_OK);
                finish();
            }
            else if(s.equals("0")){
                Toast.makeText(getApplicationContext() , "아이디가 중복입니다", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tmp1 = params[0];
                String tmp2 = params[1];
                String tmp3 = params[2];

                String data = URLEncoder.encode("tmp1","UTF-8")+"="+URLEncoder.encode(tmp1,"UTF-8"); // "tmp1"=tmp1;
                data+= "&"+URLEncoder.encode("tmp2","UTF-8")+"="+URLEncoder.encode(tmp2,"UTF-8");
                data+= "&"+URLEncoder.encode("tmp3","UTF-8")+"="+URLEncoder.encode(tmp3,"UTF-8");

                URL url = new URL("http://kimki.iptime.org/input.php");
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
                    sb.append(line);
                }

                con.disconnect();

                return sb.toString();         //리턴값 정하기

            } catch(Exception e) {};
            return null;
        }
    }
}
