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
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestsViewActivity extends AppCompatActivity implements QuestsAdapter.ItemListener{
private RecyclerView mRecyclerView;
private ArrayList mArrayList;
private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questsview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mArrayList = new ArrayList();
        mArrayList.add(new Quest(1,"튜토리얼","","","","","",0));
        mArrayList.add(new Quest(2,"행사의 장","","","","","",0));

        QuestsAdapter adapter = new QuestsAdapter(this, mArrayList, this);
        mRecyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false );
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(Quest item){
        Toast.makeText(getApplicationContext(),"clicked" ,  Toast.LENGTH_SHORT).show();
    }

    public void animateIntent(View view){

        // Ordinary Intent for launching a new activity
        Intent intent = new Intent(this, QuestDetailActivity.class);

        // Get the transition name from the string
        String transitionName = getString(R.string.transition_string);

        // Define the view that the animation will start from
        View viewStart = findViewById(R.id.cardView);

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewStart, transitionName);

        // Start the Intent
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
