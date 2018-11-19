package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AfterEndingActivity extends FragmentActivity implements OnMapReadyCallback{
    GoogleMap mMap;

    private long lastTimeClickMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_ending);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.ending_map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 구글 맵 객체를 불러온다.
        mMap = googleMap;

        LatLng mDefaultLocation = new LatLng(37.579617, 126.97704099999999);
        LatLng chapter1 = new LatLng(37.578526, 126.976993);
        LatLng chapter0 = new LatLng(37.575996, 126.976917);

        onAddMarker(chapter1,"근정전");
        onAddMarker(chapter0, "광화문");

        //카메라를 경복궁 위치로 옮긴다.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation,14));
        }

    private void onAddMarker(LatLng location, String str){
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(location)
                .title(str);

        this.mMap.addMarker(makerOptions).showInfoWindow();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle().equals("근정전")) {

                Log.d(marker.getTitle(), "김현규대가리");
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
            }
                Log.d(marker.getTitle(),"김현규대가리");

                //Toast.makeText(AfterEndingActivity.this, "해당 전각을 한 번 더 클릭하시면 해당 설명으로 이동합니다.", Toast.LENGTH_SHORT);
                return false;
            }
        });

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AfterEndingActivity.this);
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