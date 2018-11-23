package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExplanationImageActivity extends AppCompatActivity {
    @BindView(R.id.iv_explanation)
    ImageView mExplanationImage;
    @BindView(R.id.tv_explanation)
    TextView mExplanationText;
    @BindView(R.id.btn_OK_EI)
    Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation_image);
        ButterKnife.bind(this);
        if(DBHandler.currentUserData.getMember_currentQuest() == 4)
            mExplanationImage.setImageResource(R.drawable._4_explanation);
        else {
            mExplanationImage.setImageResource(R.drawable.oijeon);
            mExplanationText.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getExplanation());
        }
    }
    @OnClick(R.id.btn_OK_EI)
    void check() {
        Intent intent = new Intent(getApplicationContext(),CompleteActivity.class);
        startActivity(intent);
        finish();
    }
}
