package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Prologue3Activity extends AppCompatActivity {
    private Button tutorial_start_button;
    private TextView main_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prologue3);
        tutorial_start_button = findViewById(R.id.tutorial_start);
        main_text = findViewById(R.id.main_text3);

        tutorial_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "잠시만 기다려주세요",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        }
    }

