package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiveQuestActivity extends AppCompatActivity {
    @BindView(R.id.chapter_text)
    TextView chapter;
    @BindView(R.id.title_text)
    TextView title;
    @BindView(R.id.quest_description)
    TextView description;
    @BindView(R.id.confirm_quest)
    Button confirm;

    private String TAG="ReceiveQuestActivity";

    private MapsActivity end_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_quest);
        ButterKnife.bind(this);
        end_activity = (MapsActivity)MapsActivity.mapsActivity; // 변수에 MapsActivity 를 담는다.
        setElement();
    }

    private void setElement(){
        chapter.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitle());
        title.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getSubTitle());
        description.setText("("+DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getPoint()+"점)\n"+DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getDescription());
    }

    @OnClick(R.id.confirm_quest)
    void confirm(){
        if(DBHandler.currentUserData.getMember_numTutorial()==0) {
            InsertData task = new InsertData();
            task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), String.valueOf(0));
            DBHandler.currentUserData.setMember_numTutorial(1);
        }
        end_activity.finish();
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "뒤로 갈 수 없습니다. 확인버튼을 누르세요~", Toast.LENGTH_SHORT).show();
        // super.onBackPressed();  뒤로가기 막기
    }

    class InsertData extends AsyncTask<String, Void,String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //  progressDialog = ProgressDialog.show(RestoreActivity.this,
            //         "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //  progressDialog.dismiss();

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            Log.d(TAG, "POST response  - " + result);

        }


        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[1];
            String numTutorial = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "userID=" + userID + "&numTutorial=" + numTutorial;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setConnectTimeout(50000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                // onSignupFailed();
                return new String("Error: " + e.getMessage());
            }

        }

    }
}
