package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static gyeongbokgung.kbsccoding.com.gyeongbokgung.DBHandler.isLogin;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (경복궁) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(37.579617, 126.97704099999999);
    private static final int DEFAULT_ZOOM = 18;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted; // GPS 이용가능 여부

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    // 화면상단에 메인퀘스트 표시
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    //  private Button buttonHint;
    //  private Context context;
    //  public static ArrayList<Quest> questDataList;   //퀘스트 데이터
    private String mJsonString;
    private int position = 0;
    private int loginState = 0;

    // FloatingActionMenu
    public static FloatingActionMenu fab_menu;
    public static com.github.clans.fab.FloatingActionButton fab_quest;
    public static com.github.clans.fab.FloatingActionButton fab_ranking;
    public static com.github.clans.fab.FloatingActionButton fab_logout;
    private Handler mUiHandler = new Handler();

    // current location btn
    private ImageButton btn_current_location;
    public static Activity mapsActivity;  // CompleteActivity에서 finsh하기 위함

    private TextView box, line1, line2, explain, darkBackground;
    public static View mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// Retrieve location and camera position from saved instance state.
        Intent intent = getIntent();
        loginState = intent.getIntExtra("alreadyLogin", 0);

        if (loginState == 1) {

            GetData_set task2 = new GetData_set();
            task2.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/query_DD.php", SaveSharedPreference.getUserName(getApplicationContext()));
            Log.d("연재짱","111111");
            isLogin=true;

        }
        setContentView(R.layout.activity_maps);
        mapView = this.getWindow().getDecorView();

        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        mapsActivity = MapsActivity.this;
     /*   if (DBHandler.currentUserData.getMember_currentQuest() == 0) {
            GetData task = new GetData();
            task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/getQuest_DD.php", "");
        } else {*/
            listView = findViewById(R.id.lvExp);
            initData();
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
            listView.setAdapter(listAdapter);
            //DBHandler.showTutorial();
            Log.d("함수","showtutorial 실행됨");
       // }

     /*   if(DBHandler.currentUserData.getMember_numTutorial()==0 || isLogin){

            Log.d("연재확인", String.valueOf(DBHandler.currentUserData.getMember_numTutorial())+isLogin);
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), ReceiveQuestActivity.class);
                    startActivity(i);
                    isLogin=false;
                }
            }, 4000);
        }*/
        // 화면상단 메인퀘스트 표시
      /*  listView = findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);*/

        /*LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_quest_detail,null);
        buttonHint = view.findViewById(R.id.hint_button);
        //Log.d("힌트사용", "버튼연결됨");
        buttonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // db에서 문제 id로 switch문 작성
                Intent intent = new Intent(getApplicationContext(), HintActivity.class);
                Log.d("힌트사용", "사용");
                //Intent intent = new Intent(getApplicationContext(), HintImageActivity.class);
                startActivity(intent);
            }
        });*/

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // FAB Menu 세팅
        fab_menu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fab_quest = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_quest);
        fab_ranking = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_ranking);
        fab_logout = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_logout);

        createCustomAnimation();
        fab_ranking.setOnClickListener(clickListener);
        fab_quest.setOnClickListener(clickListener);
        fab_logout.setOnClickListener(clickListener);

        // 튜토리얼 상황
         DBHandler.showTutorial();

        /*
        if(DBHandler.currentUserData.getMember_currentQuest() == 0){    // 퀘스트번호 0번
        //    DBHandler.numTutorial = SaveSharedPreference2.getNumTutorial(this);
            if(DBHandler.currentUserData.getMember_numTutorial() == 1) {
               // mapView.setBackgroundColor(getResources().getColor(R.color.darkBackground));

                box = findViewById(R.id.box1);
                explain = findViewById(R.id.explain1);
                darkBackground = findViewById(R.id.dark_background_quest);

                box.setVisibility(View.VISIBLE);
                explain.setVisibility(View.VISIBLE);
                darkBackground.setVisibility(View.VISIBLE);

                explain = findViewById(R.id.explain2);
                explain.setVisibility(View.GONE);
            }

            else {
                box = findViewById(R.id.box1);
                explain = findViewById(R.id.explain1);
                darkBackground = findViewById(R.id.dark_background_quest);

                box.setVisibility(View.GONE);
                explain.setVisibility(View.GONE);
                darkBackground.setVisibility(View.GONE);

                explain = findViewById(R.id.explain2);
                explain.setVisibility(View.GONE);
            }
        }
        else {
            box = findViewById(R.id.box1);
            explain = findViewById(R.id.explain1);
            darkBackground = findViewById(R.id.dark_background_quest);

            box.setVisibility(View.GONE);
            explain.setVisibility(View.GONE);
            darkBackground.setVisibility(View.GONE);

            explain = findViewById(R.id.explain2);
            explain.setVisibility(View.GONE);
        } */

        // current location button 세팅
        btn_current_location = (ImageButton) findViewById(R.id.btn_current_location);
        btn_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "내위치내위치ㅡㅡ");
                getDeviceLocation();
            }
        });
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fab_menu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fab_menu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fab_menu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fab_menu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fab_menu.getMenuIconView().setImageResource(fab_menu.isOpened()
                        ? R.drawable.ic_menu : R.drawable.ic_close);

                // 튜토리얼 상황
                Log.d("애니메이션함수", "실행!");
                if (DBHandler.currentUserData.getMember_numTutorial() == 6) {
                    DBHandler.currentUserData.setMember_numTutorial(7);
                    DBHandler.isTutorial[6] = true;
                    DBHandler.showTutorial();
                }
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fab_menu.setIconToggleAnimatorSet(set);
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));
            if (!success)
                Log.e(TAG, "Style parsing failed.");

        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

      if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getType() == 0 && DBHandler.currentUserData.getMember_currentQuest() != 14 && DBHandler.currentUserData.getMember_currentQuest() != 18){
            Log.d("마커나오나","알로하");

            LatLng destination = new LatLng(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getLatitude(),
                    DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getLongitude());

            onAddMarker(destination);
            }

    }


    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastKnownLocation = task.getResult();
                            // Set the map's camera position to the current location of the device.
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                            // Construct a CameraPosition focusing on current location
                            // and animate the camera to that position.
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()))
                                    .zoom(DEFAULT_ZOOM)
                                    .tilt(50)
                                    .build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition((cameraPosition)));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                            Log.d(TAG, "Current location Lat:" + mLastKnownLocation.getLatitude() + ", Lng:" + mLastKnownLocation.getLongitude());
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }

                    }
                });
                // 위치기반문제일 경우 카메라를 현재 내위치 말고 가야하는 위치로 표시
                if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getType() == 0){
                    LatLng destination = new LatLng(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getLatitude(),
                            DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, DEFAULT_ZOOM));
                }
            }

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * 런타임에 기기 위치 권한 요청
     */
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * 콜백 메서드
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "permission success", Toast.LENGTH_SHORT).show();
                    mLocationPermissionGranted = true;
                    if (ActivityCompat
                            .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
//                    mMap.setMyLocationEnabled(true);
                } else {
                    // permission denied
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        updateLocationUI();
    }

    private void showCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {

        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            // Prompt the user for permission.
            getLocationPermission();
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    // 화면 상단 메인퀘스트에 들어갈 텍스트
    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        Log.d("왜안나와", "init" + DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getSubTitle());

        if(DBHandler.currentUserData.getMember_currentQuest() < 16 && DBHandler.currentUserData.getMember_currentQuest() > 12)
            listDataHeader.add("강녕전과 교태전의 특별함");
        else
            listDataHeader.add(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getSubTitle());
        List<String> showQuest = new ArrayList<>();
        showQuest.add(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getSumDescription());

        listHash.put(listDataHeader.get(0), showQuest);
    }

    private class GetData_set extends AsyncTask<String, Void, String> {

        // ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // progressDialog = ProgressDialog.show(MainActivity.this,
            //        "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // progressDialog.dismiss();
            //mTextViewResult.setText(result);

            Log.d(TAG, "response - " + result);

            if (result == null) {

                //mTextViewResult.setText(errorString);
                Log.d(TAG, "null로 들어옴 :(errorString): " + errorString);
            } else {

                mJsonString = result;
                showResult_set();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "userID=" + params[1];
/////////////////////////////////////////////////////////////////////////////////////////////
            Log.d(TAG, "param: " + params[1]);
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setConnectTimeout(50000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, "~~~결과뚜뚜뚜:" + sb.toString().trim());
                return sb.toString().trim();


            } catch (Exception e) {
                //  Log.d("로그인", "ID없음");
                Log.d(TAG, "GetData : Error " + e);
                errorString = e.toString();

                return null;
            }

        }

    }

    private void showResult_set() {

        String TAG_JSON = "gyeongbokgung";
        String TAG_ID = "userID";
        String TAG_NAME = "userName";

        String TAG_PASSWORD = "userPassword";
        String dbpw = "";
        String dbid = "";
        String dbname = "";
        int dbscore = 0;
        int dbrank = 0;
        int dbidx = 0;
        int dbcurrent = 0;
        int dbnumTutorial = 0;

        try {
            Log.d(TAG, "~~~1s");
            Log.d(TAG, "~~~mJsonStrings" + mJsonString);
            // JSONObject jsonObject = new JSONObject(mJsonString);
            JSONObject jsonObject = new JSONObject(mJsonString.substring(mJsonString.indexOf("{"), mJsonString.lastIndexOf("}") + 1));
            Log.d(TAG, "~~~2s");
            //  Log.d(TAG,"~~~~~@@@:"+jsonObject.toString());
            // Log.d(TAG,"~~~~!!!!!:"+jsonObject.get("userPassword").toString());
            // Log.d(TAG,"~~~~~@@@:"+jsonObject.toString());
            // Log.d(TAG,"~~~~~####:"+jsonObject.getString(TAG_PASSWORD));
            //JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            Log.d(TAG, String.valueOf(jsonArray.length()));
            Log.d(TAG, "~~~3s");

            for (int i = 0; i < jsonArray.length(); i++) {
                Log.d("로그인s", "for문 들어옴");

                JSONObject item = jsonArray.getJSONObject(i);
                Log.d("로그인s", "for문 들어옴item");
                String idx = item.getString("idx");
                System.out.println(item.getString("userPassword"));

                dbpw = item.getString("userPassword");
                dbid = item.getString("userID");
                dbname = item.getString("userName");
                dbidx = item.getInt("idx");
                Log.d(TAG, "userScore 전");
                dbscore = item.getInt("userScore");
                Log.d(TAG, "userScore 후");
                dbrank = item.getInt("userRank");
                dbcurrent = item.getInt("currentQuest");
                dbnumTutorial = item.getInt("numTutorial");


                System.out.println(item.getString("userName"));

            }

            PersonalData personalData = new PersonalData();

            personalData.setMember_id(dbid);
            personalData.setMember_name(dbname);

            DBHandler.currentUserData.setMember_id(dbid);
            DBHandler.currentUserData.setMember_name(dbname);
            DBHandler.currentUserData.setMember_password(dbpw);
            DBHandler.currentUserData.setMember_score(dbscore);
            DBHandler.currentUserData.setMember_rank(dbrank);
            DBHandler.currentUserData.setMember_idx(dbidx);
            DBHandler.currentUserData.setMember_currentQuest(dbcurrent);

            DBHandler.currentUserData.setMember_numTutorial(dbnumTutorial);
            Log.d(TAG, "currentUserData 업데이트됨 !!s");

            // SaveSharedPreference.getInstance(LoginActivity.this).saveUserInfo(DBHandler.currentUserData);

        } catch (JSONException e) {

            Log.d(TAG, "catch로 들어옴 showResult : s" + e);
        }

    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.fab_quest:
                    // 튜토리얼
                    if (DBHandler.currentUserData.getMember_numTutorial() == 7) {
                        DBHandler.currentUserData.setMember_numTutorial(8);
                        DBHandler.isTutorial[7] = true;
                    }

                    intent = new Intent(MapsActivity.this, QuestsViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fab_ranking:
                    // 튜토리얼
                    intent = new Intent(MapsActivity.this, RankingActivity.class);
                    startActivity(intent);

                    Handler delayHandler = new Handler();
                    delayHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (DBHandler.currentUserData.getMember_numTutorial() == 10) {
                                DBHandler.currentUserData.setMember_numTutorial(11);
                                DBHandler.isTutorial[10] = true;
                                DBHandler.showTutorial();
                                // 팝업창 띄우기
                                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                                builder.setTitle("튜토리얼 완료");
                                builder.setMessage("모든 튜토리얼이 완료되었습니다. 이제 여러분의 힘으로 경복궁 복원을 완료해주시길 바랍니다.");
                                //오른쪽 버튼
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                builder.show();
                            }
                        }
                    }, 1000);
                    break;
                case R.id.fab_logout:
                    SaveSharedPreference.clearUserName(getApplicationContext());
                    intent = new Intent(MapsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void onAddMarker(LatLng location) {
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(location);

        this.mMap.addMarker(makerOptions).showInfoWindow();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("종료 확인");
        builder.setMessage("정말 종료하시겠습니까?");
        //오른쪽 버튼
        builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
        // super.onBackPressed();  뒤로가기 막기
    }
}
