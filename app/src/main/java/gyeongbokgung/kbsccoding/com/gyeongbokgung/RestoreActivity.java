package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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


public class RestoreActivity extends AppCompatActivity {

    @BindView(R.id.input_reply)
    EditText mAnswer;
    @BindView(R.id.input_button)
    Button mInput;
    @BindView(R.id.view_input_reply)
    TextView mQuest;

    private String TAG = "Restore Activity";
    private String score;
    private String explanation;
 //   ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore);

        ButterKnife.bind(this);
        mQuest.setText(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getDescription());
    }
    @OnClick(R.id.input_button)
    void input_answer() {

     //   if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getType()==0){
     //       Log.d(TAG,"if로 들어옴");
     //       setContentView(R.layout.activity_restore2);}
        String answer = mAnswer.getText().toString();
        String DBanswer = DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getGoal();
        Log.d(TAG,answer+DBanswer);
        if(answer.equals(DBanswer)){
            // 튜토리얼
            if(DBHandler.currentUserData.getMember_numTutorial() == 5) {
                DBHandler.currentUserData.setMember_numTutorial(6);
                DBHandler.isTutorial[5] = true;
            }
            //답이 맞았을 때
            Toast.makeText(getBaseContext(), "정답입니다.", Toast.LENGTH_LONG).show();
            Log.d(TAG,"정답if문으로 들어옴");
            score = String.valueOf(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getPoint());
            Log.d(TAG,"score 점수 업데이트");
            InsertData task=new InsertData();
            task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_DD.php", DBHandler.currentUserData.getMember_id(),score);
            DBHandler.currentUserData.setMember_score(DBHandler.currentUserData.getMember_score()+Integer.parseInt(score));
            Log.d(TAG,"이제 점수 추가");
            Log.d("점수점수", Integer.toString(DBHandler.currentUserData.getMember_score()));
            Log.d(TAG,"current 업데이트 확인"+DBHandler.currentUserData.getMember_currentQuest());
            //점수 올리기
            explanation=DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getExplanation();
            //APP current도 올려줘야함!
            if(DBHandler.currentUserData.getMember_currentQuest()==4){
                Intent intent = new Intent(getApplicationContext(),ExplanationImageActivity.class);
                startActivity(intent);
                finish();
            }
            if (explanation.equals("null")){
                Intent intent = new Intent(getApplicationContext(),CompleteActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(getApplicationContext(), ExplanationActivity.class);
                startActivity(intent);
                finish();
            }
        }
        //답이 맞으면 배경지식 띄어주는 화면, 그 다음에 확인버튼 누르면 complete됐다고 띄어야됨
        //user의 점수 올리기
        //currentQuest를 하나 증가시켜줘야함 ( DB도, APP도 )
        //문제도 바꿔야됨 ( currentQuest로 설정해놨으니 자동으로 바뀌는지 확인)

        //type에 따라서 설정해줘야함 (0은 위치인식, 1은 정답 한개, 2는 정답 두개 ...)

        else {      // 답이 틀렸을 때
            Toast.makeText(getBaseContext(), "틀렸습니다. 다시 시도하세요.", Toast.LENGTH_LONG).show();
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
