package com.example.mapa;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.slider.Slider;

public class MainActivity
        extends AppCompatActivity
        implements OnMapReadyCallback {

    GoogleMap mapa;

    Double lat, lng;

    EditText txtLat, txtLong;

    Circle circulo = null;

    Slider sliderRadio;

    float radio=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtLat = findViewById(R.id.editTextNumberDecimal);
        txtLong = findViewById(R.id.editTextNumberDecimal2);
        sliderRadio = findViewById(R.id.slider);

        sliderRadio.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                radio = slider.getValue();
                updateInterfaz();
            }

            @Override
            public void onStopTrackingTouch(@NonNull  Slider slider) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa= googleMap;

        mapa.getUiSettings().setZoomControlsEnabled(true);
        CameraUpdate camUpd1 = CameraUpdateFactory
                .newLatLngZoom(new LatLng(-1.02372, -79.46519), 13);
                        mapa.moveCamera(camUpd1);

                        //PUNTO DE LA UTEQ

        LatLng punto = new LatLng(-1.01292, -79.46890);
        mapa.addMarker(new MarkerOptions().position(punto)
                .title("Marker in Sydney"));

        //CIRCULO DE LA UTEQ

        CircleOptions circleOptions = new CircleOptions()
                .center(punto)
                .radius(200) //En Metros
                .strokeColor(Color.RED)
                .fillColor(Color.argb(50, 150, 50, 50));

        Circle circulo = mapa.addCircle(circleOptions);


        mapa.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = mapa.getCameraPosition().target;
                lat = center.latitude;
                lng =center.longitude;
                updateInterfaz();

            }
        });



    }
    private void updateInterfaz(){
        txtLat.setText(String.format("%.4f", lat));
        txtLong.setText(String.format("%.4f", lng));

        //PintarCirculo()
    }

    private void PintarCirculo(){

    CircleOptions circleOptions = new CircleOptions()
            .center(new LatLng(lat,lng))
            .radius(radio*100) //En Metros
            .strokeColor(Color.RED)
            .fillColor(Color.argb(50, 150, 50, 50));

    circulo = mapa.addCircle(circleOptions);

    }

}