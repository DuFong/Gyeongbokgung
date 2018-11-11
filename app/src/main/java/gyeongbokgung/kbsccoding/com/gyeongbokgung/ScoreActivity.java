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
    String userID;
    String Score;
    PersonalData nowPerson = new PersonalData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        nowPerson=(PersonalData)getIntent().getSerializableExtra("nowperson");
        userID=nowPerson.getMember_id();
        Log.d(TAG,"~~~~~~~확인: "+nowPerson.getMember_currentQuest());
        Log.d(TAG,"~~~넘어온 score: "+nowPerson.getMember_score()+" rank:: "+nowPerson.getMember_rank());


    }

    @OnClick(R.id.btn_submit)
    void submit(){
        //성공했는지 확인 후 점수 update
        //해당하는 회원정보 찾기
        //점수 update
        //String userScore = mScore.getText().toString();
        String idx = "1";
        if(valid==true){

            GetDataQuest task_quest = new GetDataQuest();
            task_quest.execute("http://" + getString(R.string.ip_adrress)+ "/query_quest.php", idx);

            Log.d(TAG,"이제 점수 추가");
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
    private class GetDataQuest extends AsyncTask<String, Void, String> {

        // ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // progressDialog = ProgressDialog.show(MainActivity.this,
            //        "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // progressDialog.dismiss();
            //mTextViewResult.setText(result);

            Log.d(TAG2, "response - " + result);

            if (result == null){

                //mTextViewResult.setText(errorString);
                Log.d(TAG2,"null로 들어옴 :(errorString): "+errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "idx=" + params[1];
/////////////////////////////////////////////////////////////////////////////////////////////
            Log.d(TAG2,"param: "+params[1]);
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setConnectTimeout(50000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG2, "response code - " + responseStatusCode);

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
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG2,"~~~결과뚜뚜뚜:"+sb.toString().trim());
                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG2, "GetData : Error "+e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="gyeongbokgung";
        /*String TAG_ID = "userID";
        String TAG_NAME = "userName";
        String TAG_PASSWORD="userPassword";
        String dbpw="";
        String dbid="";
        String dbname="";
        int dbscore=0;
        int dbrank=0;
        int dbidx=0;*/

        int idx=0;
        String Title= "";
        String Subtitle="";
        String Description = "";
        String Description_sum="";
        String Goal="";
        String Hint = "";
        int Point =0;
        int Type =2;


        try {
            Log.d(TAG2,"~~~1");
            Log.d(TAG2,"~~~mJsonString"+mJsonString);
            // JSONObject jsonObject = new JSONObject(mJsonString);
            JSONObject jsonObject = new JSONObject(mJsonString.substring(mJsonString.indexOf("{"), mJsonString.lastIndexOf("}") + 1));
            Log.d(TAG2,"~~~2");
            //  Log.d(TAG,"~~~~~@@@:"+jsonObject.toString());
            // Log.d(TAG,"~~~~!!!!!:"+jsonObject.get("userPassword").toString());
            // Log.d(TAG,"~~~~~@@@:"+jsonObject.toString());
            // Log.d(TAG,"~~~~~####:"+jsonObject.getString(TAG_PASSWORD));
            //JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            Log.d(TAG2, String.valueOf(jsonArray.length()));
            Log.d(TAG2,"~~~3");
            for(int i=0;i<jsonArray.length();i++) {

                JSONObject item = jsonArray.getJSONObject(i);
                Log.d(TAG2,"~~여기");
                idx=item.getInt("idx");
                Log.d(TAG2,"idx : "+idx);
                Title= item.getString("Title");
                Log.d(TAG2,"title : "+Title);
                Subtitle=item.getString("Subtitle");
                Log.d(TAG2,"Subtitle : "+Subtitle);
                Description = item.getString("Description");
                Log.d(TAG2,"Description : "+Description);
                Description_sum=item.getString("Description_sum");
                Log.d(TAG2,"Description_sum : "+Description_sum);
                Goal=item.getString("Goal");
                Log.d(TAG2,"Goal: "+Goal);
                //Hint=item.getString("Hint");
                Point=item.getInt("Point");
                Log.d(TAG2,"Point: "+Point);
                Type=item.getInt("type");
                Log.d(TAG2,"Type: "+Type);
                Log.d("TAG2","~~db에서 가져온 "+idx+", "+Title+" ,");
            }

            Quest nowQuest = new Quest(idx,Title,Subtitle,Description,Description_sum,Goal,Hint,Point,Type);
            Score=String.valueOf(nowQuest.getPoint());
            Log.d(TAG2,"score 점수 업데이트");
            InsertData task=new InsertData();
            task.execute("http://" + getString(R.string.ip_adrress)+ "/update.php", userID,Score);
        } catch (JSONException e) {

            Log.d(TAG2, "catch로 들어옴 showResult : "+e);
        }

    }


}




