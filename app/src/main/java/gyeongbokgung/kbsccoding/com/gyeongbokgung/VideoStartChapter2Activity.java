package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class VideoStartChapter2Activity extends AppCompatActivity {
    private Uri uri;
    private VideoView videoView;
    private Timer timer;
    private TimerTask after_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_start_chapter2);
        videoView = findViewById(R.id.video_start_chapter2);
        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.startchapter2;
        uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        timer = new Timer();
        Runtimer();
    }

    public void Runtimer() {
        after_timer = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ReceiveQuestActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(after_timer, 5000);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "뒤로 갈 수 없습니다. 동영상을 끝까지 봐주세요.", Toast.LENGTH_SHORT).show();
        // super.onBackPressed();  뒤로가기 막기
    }
}
