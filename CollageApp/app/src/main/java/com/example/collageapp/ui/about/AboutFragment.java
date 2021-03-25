package com.example.collageapp.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.collageapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class AboutFragment extends Fragment implements OnMapReadyCallback {
    private ViewPager viewPager;
    private BranchAdaptor branchAdaptor;
    private ArrayList<BranchModal> branchModals;
    private Button openMapsBtn;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "AIzaSyC1-GFIPwYRtSr4smziSnq6kLQ2voPj1IA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        branchModals = new ArrayList<>();

        branchModals.add(new BranchModal(R.drawable.ic_computer, "Computer Department", "The department of Computer Engineering started its journey in the year 2010 with an initial intake of 60 students to cater to the requirement of trained Engineers in the field of Computers.To achieve this, we have competent, dedicated and highly motivated staff to train our students. Besides the regular class room teaching, we conduct special trainings in soft skills programs (personality development, communication skills, group discussions, debate, etc.) and foreign language programs (Japanese and German) which are a continuous process for development of students to facilitate better placement opportunities. "));
        branchModals.add(new BranchModal(R.drawable.ic_civil,"Civil Deaprtment","The department of Computer Engineering started its journey in the year 2010 with an initial intake of 60 students to cater to the requirement of trained Engineers in the field of Computers.To achieve this, we have competent, dedicated and highly motivated staff to train our students. Besides the regular class room teaching, we conduct special trainings in soft skills programs (personality development, communication skills, group discussions, debate, etc.) and foreign language programs (Japanese and German) which are a continuous process for development of students to facilitate better placement opportunities. "));
        branchModals.add(new BranchModal(R.drawable.ic_mechanical,"Mechanical Deaprtment","The department of Computer Engineering started its journey in the year 2010 with an initial intake of 60 students to cater to the requirement of trained Engineers in the field of Computers.To achieve this, we have competent, dedicated and highly motivated staff to train our students. Besides the regular class room teaching, we conduct special trainings in soft skills programs (personality development, communication skills, group discussions, debate, etc.) and foreign language programs (Japanese and German) which are a continuous process for development of students to facilitate better placement opportunities. "));

        branchAdaptor = new BranchAdaptor(getContext(),branchModals);

        viewPager = view.findViewById(R.id.viewPager);

        viewPager.setAdapter(branchAdaptor);

        mMapView = view.findViewById(R.id.map);
        openMapsBtn = view.findViewById(R.id.openMapsBtn);


        initGoogleMap(savedInstanceState);

        openMapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUri = "http://maps.google.com/maps?q=loc:" + 18.621205963908565+ "," + 73.9119558262285 +  " (" + "Label which you want" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                startActivity(intent);
            }
        });
        return view;
    }
    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);

        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this::onMapReady);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float zoomLevel = 16.0f;
        googleMap.addMarker(new MarkerOptions().position(new LatLng(18.621205963908565, 73.9119558262285)).title("Marker"));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(18.621205963908565, 73.9119558262285), zoomLevel));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}