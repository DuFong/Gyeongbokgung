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
    private String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questsview);

        // 레이아웃매니저 세팅
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        ArrayList<Quest> quests = (ArrayList<Quest>) intent.getSerializableExtra("quests");
        int position = (int)intent.getSerializableExtra("position");
        // 데이터 세팅

    //    mArrayList = new ArrayList();

    //    mArrayList.add(new Quest(1,"튜토리얼","조선총독부 철거!","일본은 우리나라의 국권을 빼앗고 강제로 통치하기 위해 경복궁 앞에 조선총독부를 세웠습니다. 우선 조선총독부를 철거해야 합니다. 광화문으로 이동하시오.","","","",0,-1));
     //   mArrayList.add(new Quest(2,"행사의 장","궁의 광대","경복궁의 위엄...어쩌구 저쩌구..해학적인 요소를 찾으시오. OOO을 보여주고 있는 웃긴 놈","","","",0,-1));


        // 어댑터 세팅
        mAdapter = new QuestsAdapter(getApplicationContext(), quests);
        Log.d(TAG,"~~~~~확인하기!!"+quests.get(position).getTitle());
        mRecyclerView.setAdapter(mAdapter);
    }

//      @Override
//    public void onItemClick(View view, Quest item) {
//        Log.d(TAG, item.getId()+" 클릭!");
//        Toast.makeText(getApplicationContext(), item.getId() + " clicked!", Toast.LENGTH_SHORT).show();
//        // Ordinary Intent for launching a new activity
//        Intent intent = new Intent(this, QuestDetailActivity.class);
//
//        // Get the transition name from the string
//        String transitionName = getString(R.string.transition_string);
//
//        // Define the view that the animation will start from
//        View viewStart = findViewById(R.id.cardView);
//
//        ActivityOptionsCompat options =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewStart, transitionName);
//
//        // Start the Intent
//        ActivityCompat.startActivity(this, intent, options.toBundle());
//    }
}
