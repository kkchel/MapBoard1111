package com.example.kicheol.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {
    EditText login_id;
    EditText login_pw;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_id = (EditText)findViewById(R.id.edit_id);
        login_pw = (EditText)findViewById(R.id.edit_pw);


        Button btn_join = (Button) findViewById(R.id.btn_join);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join.class);    //인텐트 만듬
                startActivityForResult(intent, 1001);              //인텐트 전달
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tmp_id = login_id.getText().toString();
                final String tmp_pw = login_pw.getText().toString();

                Input in = new Input();
                String result = "";
                try {
                    result = in.execute(tmp_id, tmp_pw).get();
                } catch(Exception e) {
                    e.printStackTrace();;
                }

                if(result.equals("1")){
                    Intent next = new Intent(getApplicationContext(), MainActivity.class);
                    next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    next.putExtra("id", tmp_id);
                    next.putExtra("pw", tmp_pw);
                    startActivity(next);


                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {     // 회원가입 결과값 돌아오면 실행
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_OK) {
            Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show();
        }
    }

    class Input extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {           //데이터 전송끝나고 사용
            super.onPostExecute(s);
            if (s.equals("1")) {             //성공
                ;//화면전환
            } else if (s.equals("0")) {
                Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tmp1 = params[0];
                String tmp2 = params[1];

                String data = URLEncoder.encode("tmp1", "UTF-8") + "=" + URLEncoder.encode(tmp1, "UTF-8"); // "tmp1"=tmp1;
                data += "&" + URLEncoder.encode("tmp2", "UTF-8") + "=" + URLEncoder.encode(tmp2, "UTF-8");

                URL url = new URL("http://kimki.iptime.org/check.php");            //접속할거
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoInput(true); // Allow Inputs
                con.setDoOutput(true); // Allow Outputs
                con.setUseCaches(false); // Don't use a Cached Copy
                con.setRequestMethod("POST");

                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                wr.write(data);           //데이터
                wr.flush();              //전송

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); //데이터 받기
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                con.disconnect();

                return sb.toString();         //리턴값 정하기

            } catch (Exception e) {
            }
            ;
            return null;
        }
    }
}