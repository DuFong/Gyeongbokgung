package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HintImageActivity extends AppCompatActivity {
    @BindView(R.id.iv_hint)
    ImageView mHintimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint_image);
        ButterKnife.bind(this);
        mHintimage.setImageResource(R.drawable.hintimage);
    }
}
