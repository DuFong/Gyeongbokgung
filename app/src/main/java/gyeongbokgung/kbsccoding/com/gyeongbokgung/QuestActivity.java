package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestActivity extends AppCompatActivity {

    @BindView(R.id.btn_submit)
    Button mSubmit;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_submit)
    void submit() {
        //성공했는지 확인하고 점수 update해야함
        //해당 회원정보 찾고 (아이디로) - LoginActivity
        //점수 update - 근데 이때도 문제 table에서 해당문제 점수 배점을 가져오ㅏ서 업데이트 해줘야함...
        if(valid==true){

        }



    }
}
