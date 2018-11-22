package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HintActivity extends AppCompatActivity {
    @BindView(R.id.tv_hint)
    TextView mHint;
    private String score;

    private String TAG="HintActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        ButterKnife.bind(this);
        mHint.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getHint());
        score = String.valueOf((DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getPoint())/2);
        InsertData task=new InsertData();
        task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_hint.php", DBHandler.currentUserData.getMember_id(),score);
        DBHandler.currentUserData.setMember_score(DBHandler.currentUserData.getMember_score()+Integer.parseInt(score));
        Log.d(TAG,"hint 점수 깍임"+DBHandler.currentUserData.getMember_score());

        if(DBHandler.currentUserData.getMember_numTutorial() == 4) {
            DBHandler.currentUserData.setMember_numTutorial(5);
            DBHandler.isTutorial[4] = true;
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO
                    DBHandler.showTutorial();
                }
            }, 1000);
            DBHandler.isGetChildView = true;
        }
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
            String userScore = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "userID=" + userID + "&userScore=" + userScore;

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
