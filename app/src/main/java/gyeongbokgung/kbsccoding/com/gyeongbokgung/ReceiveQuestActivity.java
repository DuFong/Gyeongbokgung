package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

    private String mJsonString_quest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_quest);
        ButterKnife.bind(this);
        setElement();
    }

    private void setElement(){
        Typeface regular;
        if(DBHandler.currentUserData.getMember_currentQuest() == 9 || DBHandler.currentUserData.getMember_currentQuest() == 11) {
            regular = Typeface.createFromAsset(this.getAssets(), "font/chinese2.ttf");
            title.setTypeface(regular, Typeface.NORMAL);
        }
        else {
            regular = Typeface.createFromAsset(this.getAssets(), "font/hanna.ttf");
            title.setTypeface(regular, Typeface.NORMAL);
        }
        try {
            Log.d("김현귮", String.valueOf(DBHandler.currentUserData.getMember_currentQuest()));
            chapter.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitle());
            title.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getSubTitle());
            description.setText("(" + DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getPoint() + "점)\n" + DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getDescription());
        }
        catch(Exception e){
          /*  Log.d("김현귮", String.valueOf(e));
            GetData task = new GetData();
            task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/getQuest_DD.php", "");
             setElement();*/
             Toast.makeText(getApplicationContext(),"다시 실행해보세요.",Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.confirm_quest)
    void confirm(){
        /*if(DBHandler.currentUserData.getMember_numTutorial()==0) {
            InsertData task = new InsertData();
            task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), String.valueOf(0));
            DBHandler.currentUserData.setMember_numTutorial(1);
        }
        else{*/

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
       // if(DBHandler.currentUserData.getMember_numTutorial() < 11)
        //     DBHandler.showTutorial();
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

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReceiveQuestActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null) {

                //   mTextViewResult.setText(errorString);
            } else {
                Log.d(TAG, "!!!result Quest" + result);
                mJsonString_quest = result;

                showResult();


            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "country=" + params[1];
//////////////////////////////////////////////////////////////////////////////////////////////

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                //        Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, "!!!!!!Quest sb.tostring" + sb.toString().trim());
                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult() {

        //  String TAG_COUNTRY ="country";
        String TAG_JSON = "gyeongbokgung";
        String TAG_IDX = "idx";
        String TAG_TITLE = "Title";
        String TAG_SUBTITLE = "Subtitle";
        String TAG_DESC = "Description";
        String TAG_DESCSUM = "Description_sum";
        String TAG_GOAL = "Goal";
        String TAG_HINT = "Hint";
        String TAG_POINT = "Point";
        String TAG_TYPE = "type";
        String TAG_titleID = "titleID";

        try {
            Log.d(TAG, "quest try들어옴");
            Log.d(TAG, "!!!mJsonString" + mJsonString_quest);
            JSONObject jsonObject = new JSONObject(mJsonString_quest.substring(mJsonString_quest.indexOf("{"), mJsonString_quest.lastIndexOf("}") + 1));
            //  JSONObject jsonObject = new JSONObject(mJsonString_quest);
            Log.d(TAG, "!!!~~JSONObject: " + jsonObject.toString());


            // 튜토리얼 상황
            //  DBHandler.showTutorial();

            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            //         Log.d(TAG,"~~array 성공");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);
                int titleID = item.getInt("titleID");
                int subID = item.getInt("subID");
                int rowID = item.getInt("rowID");
                String title = item.getString("title");
                String subTitle = item.getString("subTitle");
                String description = item.getString("description");
                String sumDescription = item.getString("sumDescription");
                String goal = item.getString("goal");
                String goal2 = item.getString("goal2");
                String goal3 = item.getString("goal3");
                String hint = item.getString("hint");
                String explanation = item.getString("explanation");
                int point = item.getInt("point");
                int type = item.getInt("type");
                double latitude = item.getDouble("latitude");
                double longitude = item.getDouble("longitude");

                Quest quest = new Quest(titleID, subID, rowID, title, subTitle, description, sumDescription, goal, goal2, goal3, hint, explanation, point, type, latitude, longitude);

                DBHandler.questDataList.add(quest);
                Log.d(TAG, "questDataList 추가");
                Log.d("라라라", "quest:" + quest.toString());
                Log.d("라라라", String.valueOf(DBHandler.currentUserData.getMember_currentQuest()));
                Log.d(TAG, DBHandler.questDataList.get(0).getTitle());
                Log.d(TAG, "현재 퀘스트뭐닝? " + DBHandler.currentUserData.getMember_currentQuest());
            }
            Log.d(TAG, "리스트 삽입 끝났니?");

        } catch (JSONException e) {
            Log.d("왜안나와", String.valueOf(e));

        }

    }

}
