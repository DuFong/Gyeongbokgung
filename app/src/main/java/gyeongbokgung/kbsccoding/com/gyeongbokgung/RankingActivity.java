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


public class RankingActivity extends AppCompatActivity {

    private static String TAG = "흐름";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<PersonalData> mArrayList;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //  mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // mTextViewResult.setMovementMethod(new ScrollingMovementMethod());



        mArrayList = new ArrayList<>();
        mAdapter = new UsersAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        //   Button button_all = (Button) findViewById(R.id.button_main_all);
        //  button_all.setOnClickListener(new View.OnClickListener() {
        //     public void onClick(View v) {

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + "gyeongbokgung.dothome.co.kr" + "/getjson_rank_D.php", "");
        //   }
        // });

    }



    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RankingActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
//            mTextViewResult.setText(result);
          //  Log.d(TAG, "response - " + result);

            if (result == null){

                //   mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
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

                return sb.toString().trim();


            } catch (Exception e) {

       //         Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){


        //  String TAG_COUNTRY ="country";
        String TAG_JSON="gyeongbokgung";
        String TAG_RANK = "userRank";
        String TAG_NAME = "userName";
        String TAG_SCORE="userScore";


        try {
     //       Log.d(TAG,"~~try 들어옴");
            JSONObject jsonObject = new JSONObject(mJsonString.substring(mJsonString.indexOf("{"), mJsonString.lastIndexOf("}") + 1));
            //  JSONObject jsonObject = new JSONObject(mJsonString);
            Log.d(TAG,"~~JSONObject: "+jsonObject.toString());

            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
   //         Log.d(TAG,"~~array 성공");
            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                int rank = item.getInt(TAG_RANK);
                String name = item.getString(TAG_NAME);
                int score = item.getInt(TAG_SCORE);
                Log.d(TAG,"~~rank:"+rank+", name"+name+", score"+score);
                PersonalData personalData = new PersonalData();

                personalData.setMember_rank(rank);
                personalData.setMember_name(name);
                personalData.setMember_score(score);

                mArrayList.add(personalData);
                Log.d(TAG,"~~mArrayList 추가");
                mAdapter.notifyDataSetChanged();
                Log.d(TAG,"~~notify성공");
            }
            Log.d(TAG, "리스트 삽입 끝났니?");

        } catch (JSONException e) {

//            Log.d(TAG, "showResult : ", e);
//            Log.d(TAG,"~~catch로 들어옴 ",e);
        }

    }

}