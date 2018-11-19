package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AfterEndingActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
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

        makeMarker(chapter0,"광화문");
        makeMarker(chapter1,"근정전");

        //카메라를 서울 위치로 옮긴다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mDefaultLocation));
    }

    private void makeMarker(LatLng location, String str){
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(location)
                .title(str);

        mMap.addMarker(makerOptions);
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
