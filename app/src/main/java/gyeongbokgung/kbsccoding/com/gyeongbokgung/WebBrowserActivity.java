package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebBrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        Intent get_name = getIntent();
        String name = get_name.getStringExtra("marker_name");


            if(name.equals("근정전")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.royalpalace.go.kr/content/data/data_03_03.asp"));
                startActivity(intent);
                finish();
            }
            else if(name.equals("광화문")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.royalpalace.go.kr/content/data/data_03.asp"));
                startActivity(intent);
                finish();
            }
            else if(name.equals("사정전")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.royalpalace.go.kr/content/data/data_03_04.asp"));
                startActivity(intent);
                finish();
            }
            else if(name.equals("강년전과교태전")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.royalpalace.go.kr/content/data/data_03_05.asp"));
                startActivity(intent);
                finish();
            }
            else if(name.equals("흥례문")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.royalpalace.go.kr/content/data/data_03_02.asp"));
                startActivity(intent);
                finish();
            }
        }

    }
