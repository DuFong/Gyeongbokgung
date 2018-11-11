package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class QuestsViewActivity extends AppCompatActivity {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final String TAGQ = "Quest DB Connect";
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;

    private QuestsAdapter mAdapter;
    private ArrayList<Quest> mArrayList;
    private String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questsview);

        // 레이아웃매니저 세팅
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);



        // 데이터 세팅

        GetData task = new GetData();
        task.execute( "http://" + getString(R.string.ip_adrress) + "/getQuest.php", "");

        mArrayList = new ArrayList();
       // mArrayList.add(new Quest(1,"튜토리얼","","","","","",0,-1));
       // mArrayList.add(new Quest(2,"행사의 장","","","","","",0,-1));


        // 어댑터 세팅
        mAdapter = new QuestsAdapter(getApplicationContext(), mArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(QuestsViewActivity.this,
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
        String TAG_JSON="proj_manager";
        String TAG_IDX = "idx";
        String TAG_TITLE = "Title";
        String TAG_SUBTITLE="Subtitle";
        String TAG_DESC= "Description";
        String TAG_DESCSUM = "Description_sum";
        String TAG_GOAL = "Goal";
        String TAG_HINT = "Hint";
        String TAG_POINT= "Point";
        String TAG_TYPE = "type";



        try {
            //       Log.d(TAG,"~~try 들어옴");
            JSONObject jsonObject = new JSONObject(mJsonString.substring(mJsonString.indexOf("{"), mJsonString.lastIndexOf("}") + 1));
            //  JSONObject jsonObject = new JSONObject(mJsonString);
            Log.d(TAG,"~~JSONObject: "+jsonObject.toString());

            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            //         Log.d(TAG,"~~array 성공");
            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                int idx = item.getInt(TAG_IDX);
                String title = item.getString(TAG_TITLE);
                String subTitle = item.getString(TAG_SUBTITLE);
                String description = item.getString(TAG_DESC);
                String description_sum = item.getString(TAG_DESCSUM);
                String goal = item.getString(TAG_GOAL);
                String hint = item.getString(TAG_HINT);
                int point = item.getInt(TAG_POINT);
                int type = item.getInt(TAG_TYPE);

                Quest quest = new Quest(idx,title,subTitle,description,description_sum,goal,hint,point,type);


                mArrayList.add(quest);
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
//      @Override
//    public void onItemClick(View view, Quest item) {
//        Log.d(TAG, item.getId()+" 클릭!");
//        Toast.makeText(getApplicationContext(), item.getId() + " clicked!", Toast.LENGTH_SHORT).show();
//        // Ordinary Intent for launching a new activity
//        Intent intent = new Intent(this, QuestDetailActivity.class);
//
//        // Get the transition name from the string
//        String transitionName = getString(R.string.transition_string);
//
//        // Define the view that the animation will start from
//        View viewStart = findViewById(R.id.cardView);
//
//        ActivityOptionsCompat options =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewStart, transitionName);
//
//        // Start the Intent
//        ActivityCompat.startActivity(this, intent, options.toBundle());
//    }
}
