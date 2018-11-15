package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class Restore3Activity extends AppCompatActivity {
    // 답이 3개인 문제에 대한 RestoreActivity.

    @BindView(R.id.input_reply3_1)
    EditText mAnswer3_1;
    @BindView(R.id.input_reply3_2)
    EditText mAnswer3_2;
    @BindView(R.id.input_reply3_3)
    EditText mAnswer3_3;
    @BindView(R.id.input_button3)
    Button mInput;
    private String TAG = "Restore3 Activity";
    private String score;
    private String explanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore3);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.input_button3)
    void input_answer() {

        String answer1 = mAnswer3_1.getText().toString();
        String answer2 = mAnswer3_2.getText().toString();
        String answer3 = mAnswer3_3.getText().toString();
        String DBanswer1 = DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getGoal();
        String DBanswer2 =  DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getGoal2();
        String DBanswer3 =  DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getGoal3();
        Log.d(TAG,"1번입력 : "+answer1+" 1번 정답 : "+DBanswer1+"2번 입력 : "+answer2+"2번 정답 : "+DBanswer2+"3번 입력 : "+answer3+"3번 정답 : "+DBanswer3);
        if((answer1.equals(DBanswer1)) && (answer2.equals(DBanswer2)) && (answer3.equals(DBanswer3))){
            //답이 맞았을 때

            //순서!!!!! ( 1번 정답이 2번정답이랑 맞아도 상관 없는거?)
            Toast.makeText(getBaseContext(), "정답입니다.", Toast.LENGTH_LONG).show();
            score = String.valueOf(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getPoint());
            Log.d(TAG,"score 점수 업데이트");
            InsertData task=new InsertData();
            task.execute("http://" + getString(R.string.ip_adrress)+ "/update.php", DBHandler.currentUserData.getMember_id(),score);
            DBHandler.currentUserData.setMember_score(DBHandler.currentUserData.getMember_score()+Integer.parseInt(score));
            Log.d(TAG,"이제 점수 추가");
            Log.d("점수점수", Integer.toString(DBHandler.currentUserData.getMember_score()));
            Log.d(TAG,"current 업데이트 확인"+DBHandler.currentUserData.getMember_currentQuest());
            //점수 올리기
            explanation=DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getExplanation();
            //APP current도 올려줘야함!
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


    }
    class InsertData extends AsyncTask<String, Void,String > {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Restore3Activity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

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
