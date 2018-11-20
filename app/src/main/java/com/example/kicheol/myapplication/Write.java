package com.example.kicheol.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Write extends Fragment implements MainActivity.onKeyBackPressedListener{
    EditText write_content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.write, container, false);

        Button btn_Write = (Button) rootView.findViewById(R.id.btn_write_input);
        Button btn_Back = (Button) rootView.findViewById(R.id.btn_write_back);
        write_content = rootView.findViewById(R.id.edit_write_content);

        btn_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id =  getArguments().getString("id");
                String table_num = getArguments().getString("table_num");

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = sdf.format(date);

                Write_Input wi = new Write_Input();
                wi.execute(table_num, id, getTime, write_content.getText().toString());
            }
        });

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });


        return rootView;
    }




////////////////통신
    class Write_Input extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {                //배열에 db값 넣기
            super.onPostExecute(s);

            onDetach();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tmp1 = params[0];
                String tmp2 = params[1];
                String tmp3 = params[2];
                String tmp4 = params[3];





                String data = URLEncoder.encode("tmp1","UTF-8")+"="+URLEncoder.encode(tmp1,"UTF-8"); // "tmp1"=tmp1;
                data += "&" + URLEncoder.encode("tmp2", "UTF-8") + "=" + URLEncoder.encode(tmp2, "UTF-8");
                data += "&" + URLEncoder.encode("tmp3", "UTF-8") + "=" + URLEncoder.encode(tmp3, "UTF-8");
                data += "&" + URLEncoder.encode("tmp4", "UTF-8") + "=" + URLEncoder.encode(tmp4, "UTF-8");

                Log.e("aa",data);



                URL url = new URL("http://kimki.iptime.org/write_input.php");
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
                    Log.e("aaa","dd" + sb.toString() + " dd");
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
