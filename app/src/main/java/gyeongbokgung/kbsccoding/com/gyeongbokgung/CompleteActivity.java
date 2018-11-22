package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteActivity extends AppCompatActivity {
    @BindView(R.id.btn_finish)
    Button btnFin;
    @BindView(R.id.tv_subtitle_com)
    TextView mSubtitle;
    @BindView(R.id.tv_desc_com)
    TextView mDesc;
    private String TAG = "CompleteActivity";
    private MapsActivity end_activity; //MapsActivity스텍을 담을 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        ButterKnife.bind(this);
        mSubtitle.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getSubTitle());
        mDesc.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getDescription());
        end_activity = (MapsActivity)MapsActivity.mapsActivity; // 변수에 MapsActivity 를 담는다.
   /*     viewKonfetti2 = (KonfettiView)findViewById(R.id.viewKonfetti);
        viewKonfetti2.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(10, 5))
                .setPosition(-50f, viewKonfetti2.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);


        viewKonfetti.build()
                .addColors(Color.parseColor("#12fce6"), Color.parseColor("#ff0dc6c9"), Color.parseColor("#009688"))
                .setDirection(90.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(5000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(14, 6f))
                .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                .stream(300, 5000L);
*/
    }
    @OnClick(R.id.btn_finish)
    void fin() {
        DBHandler.currentUserData.setMember_currentQuest(DBHandler.currentUserData.getMember_currentQuest()+1);
        Log.d(TAG, String.valueOf(DBHandler.currentUserData.getMember_currentQuest()));
        if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()-1).getTitleID() == DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitleID())
        {
            Intent intent = new Intent(getApplicationContext(), OpenTheDoorActivity.class);
            startActivity(intent);
            finish();
        }
        // 튜토리얼이 끝나고 1장 오프닝
        else if (DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitleID() == 1) {
            Intent intent = new Intent(getApplicationContext(), VideoStartChapter1Activity.class);
            startActivity(intent);
            finish();
        }
        // 1장 엔딩
        else if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitleID() == 2)    //나머지 챕터 엔딩영상 만들면 주석해제하자;
        {
            Intent intent = new Intent(getApplicationContext(), VideoChapter1Activity.class);
            startActivity(intent);
            finish();
        }
        // 2장 엔딩
        else if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitleID() == 3)    //나머지 챕터 엔딩영상 만들면 주석해제하자;
        {
            Intent intent = new Intent(getApplicationContext(), VideoChapter2Activity.class);
            startActivity(intent);
            finish();
        }
        else if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitleID() == 4)    //나머지 챕터 엔딩영상 만들면 주석해제하자;
        {
            end_activity.finish();
            Intent intent = new Intent(getApplicationContext(), VideoEndingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "뒤로 갈 수 없습니다. 확인버튼을 누르세요~", Toast.LENGTH_SHORT).show();
       // super.onBackPressed();  뒤로가기 막기
    }
}
