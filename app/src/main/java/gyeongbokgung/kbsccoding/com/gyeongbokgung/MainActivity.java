package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String mJsonString_quest;
    private String mJsonString_user;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetData task = new GetData();
        task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/getQuest_DD.php", "");

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
           // finish();
        } else
        {

            GetData_user task_user = new GetData_user();
            task_user.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/query_DD.php",SaveSharedPreference.getUserName(MainActivity.this));

            Intent i = new Intent(this, ReceiveQuestActivity.class);
            i.putExtra("alreadyLogin",1);
            startActivity(i);
            //튜토리얼
      //      DBHandler.showTutorial();
      //      DBHandler.setIsTutorial();
          //  finish();
        }
    }
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
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

    private class GetData_user extends AsyncTask<String, Void, String> {

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

            Log.d(TAG, "response - " + result);

            if (result == null){

                //mTextViewResult.setText(errorString);
                Log.d(TAG,"null로 들어옴 :(errorString): "+errorString);
            }
            else {

                mJsonString_user = result;
                showResult_user();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "userID=" + params[1];
/////////////////////////////////////////////////////////////////////////////////////////////
            Log.d(TAG,"param: "+params[1]);
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
                Log.d(TAG, "response code - " + responseStatusCode);

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
                Log.d(TAG,"~~~결과뚜뚜뚜:"+sb.toString().trim());
                return sb.toString().trim();


            } catch (Exception e) {
                //  Log.d("로그인", "ID없음");
                Log.d(TAG, "GetData : Error "+e);
                errorString = e.toString();

                return null;
            }

        }
    }
    private void showResult_user(){

        String TAG_JSON="gyeongbokgung";
        String TAG_ID = "userID";
        String TAG_NAME = "userName";
        String TAG_PASSWORD="userPassword";
        String dbpw="";
        String dbid="";
        String dbname="";
        int dbscore=0;
        int dbrank=0;
        int dbidx=0;
        int dbcurrent=0;
        int dbnumTutorial = 0;

        try {
            Log.d(TAG,"~~~1");
            Log.d(TAG,"~~~mJsonString"+mJsonString_user);
            // JSONObject jsonObject = new JSONObject(mJsonString);
            JSONObject jsonObject = new JSONObject(mJsonString_user.substring(mJsonString_user.indexOf("{"), mJsonString_user.lastIndexOf("}") + 1));
            Log.d(TAG,"~~~2");
            //  Log.d(TAG,"~~~~~@@@:"+jsonObject.toString());
            // Log.d(TAG,"~~~~!!!!!:"+jsonObject.get("userPassword").toString());
            // Log.d(TAG,"~~~~~@@@:"+jsonObject.toString());
            // Log.d(TAG,"~~~~~####:"+jsonObject.getString(TAG_PASSWORD));
            //JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            Log.d(TAG, String.valueOf(jsonArray.length()));
            Log.d(TAG,"~~~3");

            for(int i=0;i<jsonArray.length();i++) {
                Log.d("로그인", "for문 들어옴");

                JSONObject item = jsonArray.getJSONObject(i);
                Log.d("로그인", "for문 들어옴item");
                String idx = item.getString("idx");

                System.out.println(item.getString("userPassword"));
                dbpw=item.getString("userPassword");
                dbid=item.getString("userID");
                dbname=item.getString("userName");
                dbidx=item.getInt("idx");
                dbscore=item.getInt("userScore");
                dbrank=item.getInt("userRank");
                dbcurrent=item.getInt("currentQuest");
                dbnumTutorial=item.getInt("numTutorial");


                System.out.println(item.getString("userName"));

            }

            PersonalData personalData = new PersonalData();

            personalData.setMember_id(dbid);
            personalData.setMember_name(dbname);

            DBHandler.currentUserData.setMember_id(dbid);
            DBHandler.currentUserData.setMember_name(dbname);
            DBHandler.currentUserData.setMember_password(dbpw);
            DBHandler.currentUserData.setMember_score(dbscore);
            DBHandler.currentUserData.setMember_rank(dbrank);
            DBHandler.currentUserData.setMember_idx(dbidx);
            DBHandler.currentUserData.setMember_currentQuest(dbcurrent);
            DBHandler.currentUserData.setMember_numTutorial(dbnumTutorial);


            // SaveSharedPreference.getInstance(LoginActivity.this).saveUserInfo(DBHandler.currentUserData);
           //SaveSharedPreference.setUserName(getApplicationContext(),DBHandler.currentUserData.getMember_id());

           /* for(int i=0;i<jsonArray.length();i++){
               // Log.d(TAG,"~~~2");
                JSONObject item = jsonArray.getJSONObject(i);
                //Log.d(TAG,"~~~"+item.toString());
                //Log.d(TAG,"~~~2");
                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
               // String password = item.getString(TAG_PASSWORD);
*/


            //  personalData.setMember_password(password);
////////////////////////여기해야하나?////////////////////////////
            Log.d(TAG,"personalData:"+personalData.getMember_password());
            //mArrayList.add(personalData);
            //mAdapter.notifyDataSetChanged();
            //}

        } catch (JSONException e) {

            Log.d(TAG, "catch로 들어옴 showResult : "+e);
        }

    }



}
