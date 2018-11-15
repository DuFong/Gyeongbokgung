package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExplanationActivity extends AppCompatActivity {
       @BindView(R.id.btn_next)
       Button mNext;
       @BindView(R.id.tv_explanation)
       TextView mExplain;
    private String explanation;
    private String TAG = "ExplanationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);
        ButterKnife.bind(this);
        explanation = DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getExplanation();
        Log.d(TAG, explanation + DBHandler.currentUserData.getMember_currentQuest());
        //  mExplain.setText(explanation);
    //    TextView mExplain = (TextView) findViewById(R.id.tv_explanation);
     //   Button btnNext = (Button) findViewById(R.id.btn_next);

        mExplain.setText("배경지식 : "+explanation);


    }



    @OnClick(R.id.btn_next)
    void next() {
        Intent intent = new Intent(getApplicationContext(), CompleteActivity.class);
        startActivity(intent);
        finish();
    }
}