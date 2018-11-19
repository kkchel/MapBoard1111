package com.example.kicheol.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class First_Tap extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    MapFragment mMapFragment;
    View rootView;
    GoogleMap mMap;
    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.firtst_tap, container, false);

        MapView mapView = (MapView) rootView.findViewById(R.id.map);
        //mapView.setClickable(false);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
//        Button btn = (Button) rootView.findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment board = new Board();
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//
//
//                Bundle bundle = new Bundle();  //인자 넘겨주기
//                bundle.putString("table_num", "1");
//                board.setArguments(bundle);
//
//
//                transaction.add(R.id.board_layout, board).commit();
//            }
//
//        });
        return rootView;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {                     //지도 꾸미기
        // Add a marker and move the camera
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {      //오래클릭하면 마커만들기
            @Override
            public void onMapLongClick(final LatLng latLng) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                alertDialogBuilder.setTitle("게시판 생성을 생성하시겠습니까?");        //제목
                alertDialogBuilder//.setMessage("프로그램을 종료할 것입니까?")  //내용
                        .setCancelable(false)
                        .setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        Create_Board cr = new Create_Board();
                                        cr.execute(Double.toString(latLng.latitude),Double.toString(latLng.longitude),"create");

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


                MarkerOptions markerOptions = new MarkerOptions();            //마커생성
                markerOptions.position(latLng);                                //마커위치
                markerOptions.icon(BitmapDescriptorFactory.fromResource( R.drawable.panel));
                markerOptions.snippet("수도");                                 //설명
                mMap.addMarker(markerOptions);

            }
        });
        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();            //마커생성
        markerOptions.position(SEOUL);                                //마커위치
        markerOptions.title("서울");                                   //제목
        markerOptions.icon(BitmapDescriptorFactory.fromResource( R.drawable.panel));
        markerOptions.snippet("수도");                                 //설명
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));       //카메라위치 옮기기
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }




    //Button btn = (Button) rootView.findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment board = new Board();
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//
//
//                Bundle bundle = new Bundle();  //인자 넘겨주기
//                bundle.putString("table_num", "1");
//                board.setArguments(bundle);
//
//
//                transaction.add(R.id.board_layout, board).commit();
//            }
//
//        });
/////////////////////////////////////////////////////////////////////////////////////////////////////


//        protected void onResume() {
//            super.onResume();
//
//            mMapFragment.getMapAsync(this);
//
//        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //데이터 처리
        if(requestCode == 1){
            //
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {                 //마커 눌렀을때 이벤트

        Create_Board cr = new Create_Board();
        cr.execute(Double.toString(marker.getPosition().latitude),Double.toString(marker.getPosition().longitude),"search");

        return false;
    }




    ////////////통신/////////////////
    class Create_Board extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String id = "";
            try {
                JSONObject jSon = new JSONObject(s);
                JSONArray jArray = jSon.getJSONArray("result");
                JSONObject jo = jArray.getJSONObject(0);
                id = jo.getString("id");

            }catch(Exception e){}
            Fragment board = new Board();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();


            Bundle bundle = new Bundle();  //인자 넘겨주기
            bundle.putString("table_num", id);
            board.setArguments(bundle);

            transaction.addToBackStack(null);
            transaction.add(R.id.board_layout, board).commit();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tmp1 = params[0];
                String tmp2 = params[1];
                String tmp3 = params[2];                 //url 뭐쓸지 정하기


                String data = URLEncoder.encode("tmp1","UTF-8")+"="+URLEncoder.encode(tmp1,"UTF-8"); // "tmp1"=tmp1;
                data += "&" + URLEncoder.encode("tmp2", "UTF-8") + "=" + URLEncoder.encode(tmp2, "UTF-8");
                data += "&" + URLEncoder.encode("tmp3", "UTF-8") + "=" + URLEncoder.encode(tmp3, "UTF-8");
                URL url = new URL("http://kimki.iptime.org/");
                if(tmp3.equals("create")) {
                    url = new URL("http://kimki.iptime.org/create_board.php");
                }else if(tmp3.equals("search")){
                    url = new URL("http://kimki.iptime.org/search_board.php");
                }

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
