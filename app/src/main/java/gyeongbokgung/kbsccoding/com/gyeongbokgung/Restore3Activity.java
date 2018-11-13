package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Restore3Activity extends AppCompatActivity {
    // 답이 3개인 문제에 대한 RestoreActivity.

    @BindView(R.id.input_reply3_1)
    EditText mAnswer3_1;
    @BindView(R.id.input_reply3_2)
    EditText mAnswer3_2;
    @BindView(R.id.input_reply3_3)
    EditText mAnswer3_3;
    @BindView(R.id.input_button3)
    Button mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore3);
        ButterKnife.bind(this);
    }
}
