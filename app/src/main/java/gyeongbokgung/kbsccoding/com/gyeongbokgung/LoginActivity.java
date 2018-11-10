package gyeongbokgung.kbsccoding.com.gyeongbokgung;



import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
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
import java.nio.channels.InterruptedByTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.et_userid)
    EditText mUserid;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.btn_login)
    Button mLogin;
    @BindView(R.id.tv_signup)
    TextView mSignup;
    private String mJsonString;
    PersonalData nowPerson = new PersonalData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    void login() {
        Log.d(TAG, "Sign In");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        mLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        //  progressDialog.show();

        String user_id = mUserid.getText().toString();
        String password = mPassword.getText().toString();

        // TODO: Implement your own authentication logic here.
        GetData task = new GetData();

        task.execute( "http://" + getString(R.string.ip_adrress)+ "/query.php", user_id);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //     onLoginSuccess();
//                        onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    private class GetData extends AsyncTask<String, Void, String> {

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

                mJsonString = result;
                showResult();
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

                Log.d(TAG, "GetData : Error "+e);
                errorString = e.toString();

                return null;
            }

        }
    }
    private void showResult(){

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

        try {
            Log.d(TAG,"~~~1");
            Log.d(TAG,"~~~mJsonString"+mJsonString);
            // JSONObject jsonObject = new JSONObject(mJsonString);
            JSONObject jsonObject = new JSONObject(mJsonString.substring(mJsonString.indexOf("{"), mJsonString.lastIndexOf("}") + 1));
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

                JSONObject item = jsonArray.getJSONObject(i);
                System.out.println(item.getString("userPassword"));
                dbpw=item.getString("userPassword");
                dbid=item.getString("userID");
                dbname=item.getString("userName");
                dbidx=item.getInt("idx");
                dbscore=item.getInt("userScore");
                dbrank=item.getInt("userRank");


                System.out.println(item.getString("userName"));
            }
            PersonalData personalData = new PersonalData();

            personalData.setMember_id(dbid);
            personalData.setMember_name(dbname);

            nowPerson.setMember_id(dbid);
            nowPerson.setMember_name(dbname);
            nowPerson.setMember_password(dbpw);
            nowPerson.setMember_score(dbscore);
            nowPerson.setMember_rank(dbrank);
            nowPerson.setMember_idx(dbidx);



            String password = mPassword.getText().toString();
            if(password.equals(dbpw)){
                Log.d("TAG","login Success");
                onLoginSuccess();
            }
            else{
                Log.d("TAG","login Failed");
                Log.d(TAG,"password: "+password+" ,DBpassword: "+dbpw);
                onLoginFailed();
            }
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
    public boolean validate() {
        boolean valid = true;
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            _passwordText.setError(null);
//        }
//
        return valid;
    }

    public void onLoginSuccess() {
        mLogin.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

        Log.d(TAG,"~~~nowID"+nowPerson.getMember_id());
        String nid= nowPerson.getMember_id();
        Log.d(TAG,"~~~~nid:"+nid);
        //  intent.putExtra("nowPersonID",nid);
        //intent.putExtra("nowPersonID",nid);
        intent.putExtra("nowperson",nowPerson);
        Log.d(TAG,"넘겨쥼");
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        //mLogin.setEnabled(true);
    }

    @OnClick(R.id.tv_signup)
    void signup() {
        // Start the Signup activity

        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);

        startActivityForResult(intent, REQUEST_SIGNUP);
    }


}
