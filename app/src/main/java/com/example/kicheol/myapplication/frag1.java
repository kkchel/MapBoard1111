package com.example.kicheol.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class frag1 extends Fragment {
    //MapFragment mMapFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.asdf, container, false);

        Button btn = (Button)rootView.findViewById(R.id.button);
        Button btn1 = (Button)rootView.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Fragment board = new Board();
               FragmentTransaction transaction = getChildFragmentManager().beginTransaction();


               Bundle bundle = new Bundle();  //인자 넘겨주기
               bundle.putString("table_num","1");
               board.setArguments(bundle);


               transaction.add(R.id.board_layout, board).commit();
            }

        });



//        public void onMapReady(GoogleMap googleMap) {
//
//            // Add a marker and move the camera
//            LatLng start = new LatLng(dgpsSET_N, dgpsSET_E);
//            CameraPosition cp = new CameraPosition.Builder().target(start).zoom(18).build();
//            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
//
//            googleMap.addMarker(new MarkerOptions().position(start).title("Destination"));
//
//        }
//
//        protected void onResume() {
//            super.onResume();
//
//            mMapFragment.getMapAsync(this);
//
//        }


        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //데이터 처리
        if(requestCode == 1){
            //
        }

    }
}
