package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        ButterKnife.bind(this);
        mSubtitle.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getSubTitle());
        mDesc.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getDescription());
    }
    @OnClick(R.id.btn_finish)
    void fin() {
        DBHandler.currentUserData.setMember_currentQuest(DBHandler.currentUserData.getMember_currentQuest()+1);
        Log.d(TAG, String.valueOf(DBHandler.currentUserData.getMember_currentQuest()));
        finish();
    }
}
