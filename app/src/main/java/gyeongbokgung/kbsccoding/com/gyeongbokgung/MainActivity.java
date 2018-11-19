package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        } else
        {
            Intent i = new Intent(this, MapsActivity.class);
            i.putExtra("alreadyLogin",1);
            startActivity(i);
            finish();
        }
    }
}
