package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HintActivity extends AppCompatActivity {
    @BindView(R.id.tv_hint)
    TextView mHint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        ButterKnife.bind(this);
        mHint.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getHint());

        if(DBHandler.currentUserData.getMember_numTutorial() == 4) {
            DBHandler.currentUserData.setMember_numTutorial(5);
            DBHandler.isTutorial[4] = true;
            DBHandler.showTutorial();
        }
    }
}
