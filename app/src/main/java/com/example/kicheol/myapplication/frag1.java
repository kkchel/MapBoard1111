package com.example.kicheol.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class frag1 extends Fragment {
    //MapFragment mMapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.asdf, container, false);

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
}
