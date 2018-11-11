package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class QuestsViewActivity extends AppCompatActivity {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final String TAGQ = "Quest DB Connect";
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;

    private QuestsAdapter mAdapter;
    private ArrayList<Quest> mArrayList;
  //  private String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questsview);

        // 레이아웃매니저 세팅
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 디바이더 세팅
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // 어댑터 세팅
        mAdapter = new QuestsAdapter(getApplicationContext(), DBHandler.questDataList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
