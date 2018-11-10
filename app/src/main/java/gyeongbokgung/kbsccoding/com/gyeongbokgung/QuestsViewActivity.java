package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
}
