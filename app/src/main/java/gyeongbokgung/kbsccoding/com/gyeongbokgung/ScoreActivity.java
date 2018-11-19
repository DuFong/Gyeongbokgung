package gyeongbokgung.kbsccoding.com.gyeongbokgung;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ScoreActivity extends AppCompatActivity {

    private static String TAG = "ScoreActivity";
    private static String TAG2 = "Quest!!!";

    @BindView(R.id.btn_submit)
    Button mSubmit;
    boolean valid =true;
    private String mJsonString;
    @BindView(R.id.et_score)
    EditText mScore;
    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_submit)
    void submit(){
        //성공했는지 확인 후 점수 update
        //해당하는 회원정보 찾기
        //점수 update
        //String userScore = mScore.getText().toString();
        String idx = "1";
        if(valid==true){

           // score = Integer.toString(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getPoint());
            score = String.valueOf(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getPoint());
            Log.d(TAG2,"score 점수 업데이트");
            InsertData task=new InsertData();
            task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_DD.php", DBHandler.currentUserData.getMember_id(),score);
            DBHandler.currentUserData.setMember_score(DBHandler.currentUserData.getMember_score()+Integer.parseInt(score));
            Log.d(TAG,"이제 점수 추가");
            Log.d("점수점수", Integer.toString(DBHandler.currentUserData.getMember_score()));
            //task.execute("http://" + getString(R.string.ip_adrress)+ "/update.php", userID,Score);


        }
    }

    class InsertData extends AsyncTask<String, Void,String > {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ScoreActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

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




