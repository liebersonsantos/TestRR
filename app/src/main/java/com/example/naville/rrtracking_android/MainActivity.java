package com.example.naville.rrtracking_android;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naville.rrtracking_android.adapters.InstrumentAdapter;
import com.example.naville.rrtracking_android.model.Image;
import com.example.naville.rrtracking_android.model.Instrument;
import com.example.naville.rrtracking_android.model.InstrumentResponse;
import com.example.naville.rrtracking_android.model.User;
import com.example.naville.rrtracking_android.network.RestClient;
import com.example.naville.rrtracking_android.presenter.PresenterMainActivity;
import com.example.naville.rrtracking_android.task.LoginInterface;
import com.example.naville.rrtracking_android.util.Constants;
import com.example.naville.rrtracking_android.util.CustomAlertDialog;
import com.example.naville.rrtracking_android.util.ImageUtil;
import com.example.naville.rrtracking_android.util.MyGeocoder;
import com.example.naville.rrtracking_android.util.MyLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.relativeAnimation)
    RelativeLayout relativeLayout;
    @BindView(R.id.spnViewPickPlace)
    Spinner spinnerInstruments;
    @BindView(R.id.gps_card_id)
    CardView cardViewGps;
    @BindView(R.id.cardView2)
    CardView cardView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.btnCloseCardView)
    Button btnCloseCard;
    @BindView(R.id.instrumentImage)
    ImageView imageInstrument;
    @BindView(R.id.progress_image_instrument)
    ProgressBar progressBarImage;
    @BindView(R.id.instrumentName)
    TextView textInstrumentName;
    @BindView(R.id.instrumentIMEI)
    TextView textIMEI;

    private GoogleMap mMap;
    private LatLng myLocation;
    private static final String TAG = "LOCATION";
    private List<Instrument> instrumentList;
    private List<Image> imageList;
    private ProgressDialog progressDialog;
    private InstrumentAdapter instrumentAdapter;
    private boolean load = false;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private LatLng latLngInstrumentPin;
    private User user = new User();
    private Bundle bundle;

    static int animatioDuration = 200;
    Boolean clicked = false;

    PresenterMainActivity presenterMainActivity = new PresenterMainActivity(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        MyLocation.fusedLocation(this);
        MyLocation.getMyLocation(this);


        bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("USER")){
            user = bundle.getParcelable("USER");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressDialog = new ProgressDialog(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


    }

    private void initAdapterDetail() {

    }

    @OnClick(R.id.gps_card_id)
    void getMyCurrentLocation() {

        myLocation = new LatLng(MyLocation.lat, MyLocation.lng);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18));
        MyGeocoder.getMyAddress(this, MyLocation.lat, MyLocation.lng);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);

        getMenuInflater().inflate(R.menu.custom_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.custom_menu_search) {
            if (!clicked) {
                dialogInstruments();
                btnAnimationLayoutDown();
                cardViewGps.setVisibility(View.INVISIBLE);

                callApiPlusSpinner();

                clicked = !clicked;
            } else {
                btnAnimationLayoutUp();
                clicked = !clicked;
                cardViewGps.setVisibility(View.VISIBLE);
                hideCardView();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callApiPlusSpinner() {
        RestClient.getInstanceLogin(this).gettingAllInstruments().enqueue(new Callback<InstrumentResponse>() {
            @Override
            public void onResponse(Call<InstrumentResponse> call, Response<InstrumentResponse> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    List<String> instrumentName = new ArrayList<>();
                    instrumentList = response.body().getInstrumentList();

                    for (int i = 0; i < instrumentList.size(); i++) {
                        instrumentName.add(instrumentList.get(i).getInstrumentName());
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, instrumentName);
                    spinnerInstruments.setAdapter(arrayAdapter);

                    spinnerInstruments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (load) {

                                Instrument instrument = instrumentList.get(position);

                                try {
                                    if (instrument.getTrackerLatitude() != null && instrument.getTrackerLongitude() != null) {
                                        latLngInstrumentPin = new LatLng(instrument.getTrackerLatitude(), instrument.getTrackerLongitude());
                                        mMap.addMarker(new MarkerOptions().position(latLngInstrumentPin));
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngInstrumentPin, 18));
                                        showInstrumentDetail(coordinatorLayout, cardView, instrument);
                                    } else {
                                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Nenhum rastreador cadastrado para esse instrumento", Snackbar.LENGTH_LONG);
                                        View snackBarView = snackbar.getView();
                                        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        snackbar.show();

                                    }
                                } catch (Exception e) {
                                    Log.i(TAG, "onItemSelected: " + e.getMessage());
                                }

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    load = true;
                } else {

                    Toast.makeText(MainActivity.this, "ERRO AO BUSCAR LISTA DE INSTRUMENTOS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InstrumentResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Verifique sua conexão com a rede", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void dialogInstruments() {
        progressDialog.setMessage("Verificando Instrumentos");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.seeProfile) {
            CustomAlertDialog.customDialogProfileAnimated(this, user);
        } else if (id == R.id.about) {
            presenterMainActivity.aboutScreen();
        } else if (id == R.id.exit) {
            presenterMainActivity.lougoutMainActivity();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void btnAnimationLayoutDown() {

        relativeLayout.post(() -> {
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(relativeLayout, "y", toolbar.getHeight());
            animatorY.setDuration(animatioDuration);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animatorY);
            animatorSet.start();

        });

    }

    public void btnAnimationLayoutUp() {
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(relativeLayout, "y", -16);
        animatorY.setDuration(animatioDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

    }

    public void showInstrumentDetail(final View father, final View son, Instrument instrument) {

        List<String> images = instrument.getImagesList();

        Log.e(TAG, "showInstrumentDetail: " + images.toString());

        try {
            if (images != null){
                ImageUtil.loadImage(Constants.URL_IMAGE + images.get(0) , imageInstrument, progressBarImage, R.drawable.backgroundsanfona);

            }else {
                Log.i(TAG, "showInstrumentDetail: " + Constants.URL_IMAGE + images.get(0));
                Toast.makeText(this, "Verificar imagens no servidor", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.i(TAG, "showInstrumentDetail: " + e.getMessage());
        }


        textInstrumentName.setText(instrument.getInstrumentName());
        textIMEI.setText(instrument.getTrackerIMEINumber());

        imageInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.detail_instruments, null);

                RecyclerView recyclerView = view.findViewById(R.id.recycler_view_instrument_detail);
                TextView textNameDetail = view.findViewById(R.id.text_instrument_name_detail);
                TextView textImeiDetail = view.findViewById(R.id.text_instrument_imei_detail);
                TextView textAdressDetail = view.findViewById(R.id.text_instrument_adress_detail);
                TextView textLastLocationDetail = view.findViewById(R.id.text_instrument_last_location_detail);
                Button btnCloseDetail = view.findViewById(R.id.btn_close_detail);

                instrumentAdapter = new InstrumentAdapter(images);

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(instrumentAdapter);

                builder = new AlertDialog.Builder(MainActivity.this);


                textNameDetail.setText(instrument.getInstrumentName());
                textImeiDetail.setText(instrument.getTrackerIMEINumber());
                textAdressDetail.setText(MyGeocoder.address);
                textLastLocationDetail.setText(instrument.getTrackerLastLocation());

                btnCloseDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                builder.setView(view).setCancelable(false);
                dialog = builder.create();
                dialog.show();

            }
        });


        btnCloseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCardView();
            }
        });

        final Float[] n_t = {0f};
        son.post(new Runnable() {

            @Override
            public void run() {

                float  father_h = father.getHeight();
                float  father_w = father.getWidth();

                float son_h = son.getHeight();
                float son_w = son.getWidth();

                n_t[0] = father_h - son_h;

                ObjectAnimator animatorX = ObjectAnimator.ofFloat(son, "y", n_t[0]);
                animatorX.setDuration(animatioDuration);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animatorX);
                animatorSet.start();

                Log.w("No Post", String.valueOf(father_h) + " - " + String.valueOf(son_h));
                Log.w("Resultado", String.valueOf(n_t[0]));
            }
        });

    }

    private void hideCardView() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(cardView, "y", 30000);
        animatorX.setDuration(animatioDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();

    }

}
