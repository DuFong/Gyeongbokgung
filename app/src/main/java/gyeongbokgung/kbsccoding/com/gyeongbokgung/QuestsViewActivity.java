package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestsViewActivity extends AppCompatActivity {

    private static final String TAG = MapsActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;

    private QuestsAdapter mAdapter;
    private ArrayList<Quest> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questsview);

        // 레이아웃매니저 세팅
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 데이터 세팅
        mArrayList = new ArrayList();
        mArrayList.add(new Quest(1,"튜토리얼","","","","","",0,-1));
        mArrayList.add(new Quest(2,"행사의 장","","","","","",0,-1));


        // 어댑터 세팅
        mAdapter = new QuestsAdapter(getApplicationContext(), mArrayList);
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
