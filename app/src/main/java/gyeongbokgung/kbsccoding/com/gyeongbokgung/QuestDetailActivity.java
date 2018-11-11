package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestDetailActivity extends AppCompatActivity {
    private static final String TAG = QuestDetailActivity.class.getSimpleName();

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_subtitle)
    TextView mSubitle;
    @BindView(R.id.tv_description)
    TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_detail);

        // 데이터 세팅
        Intent intent = getIntent();
        //ArrayList<Quest> quests = (ArrayList<Quest>) intent.getSerializableExtra("quests");
        int position = (int)intent.getSerializableExtra("position");

        ButterKnife.bind(this);
        mTitle.setText(DBHandler.questDataList.get(position).getTitle());
        mSubitle.setText(DBHandler.questDataList.get(position).getSubTitle());
        mDescription.setText(DBHandler.questDataList.get(position).getDescription());

    }

}
