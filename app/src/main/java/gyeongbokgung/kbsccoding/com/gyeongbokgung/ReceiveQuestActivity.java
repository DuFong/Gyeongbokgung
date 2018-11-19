package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiveQuestActivity extends AppCompatActivity {
    @BindView(R.id.chapter_text)
    TextView chapter;
    @BindView(R.id.title_text)
    TextView title;
    @BindView(R.id.quest_description)
    TextView description;
    @BindView(R.id.confirm_quest)
    Button confirm;

    private MapsActivity end_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_quest);
        ButterKnife.bind(this);
        end_activity = (MapsActivity)MapsActivity.mapsActivity; // 변수에 MapsActivity 를 담는다.
        setElement();
    }

    private void setElement(){
        chapter.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitle());
        title.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getSubTitle());
        description.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getDescription());
    }

    @OnClick(R.id.confirm_quest)
    void confirm(){
        end_activity.finish();
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "뒤로 갈 수 없습니다. 확인버튼을 누르세요~", Toast.LENGTH_SHORT).show();
        // super.onBackPressed();  뒤로가기 막기
    }
}
