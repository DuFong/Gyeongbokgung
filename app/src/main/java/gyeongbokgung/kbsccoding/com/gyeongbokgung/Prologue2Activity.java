package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Prologue2Activity extends AppCompatActivity {
    private Animation animation_trans;
    private TextView main_text;
    private Timer timer;
    private TimerTask after_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prologue2);
        main_text = findViewById(R.id.main_text2);
        animation_trans = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        main_text.startAnimation(animation_trans);
        timer = new Timer();
        Runtimer();
    }

    public void Runtimer() {
        after_timer = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Prologue3Activity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(after_timer, 20000);

    }
}
