package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RestoreActivity extends AppCompatActivity {
    private TextView quest_text;
    private EditText input_text;
    private Button input_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore);
        quest_text = findViewById(R.id.view_input_reply);
        input_text = findViewById(R.id.input_reply);
        input_button = findViewById(R.id.input_button);

        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(input_text.getText().toString() ==

            }
        });
    }

}
