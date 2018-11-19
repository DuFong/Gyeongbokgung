package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestoreLocationActivity extends AppCompatActivity {
    private GpsInfo gps;
    private LatLng mylocation;
    private LatLng destination;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_location);
        gps = new GpsInfo(RestoreLocationActivity.this);
        if(compareLocaton())
        {
            // 도착지에 잘 도착했을때
            Toast.makeText(getBaseContext(), "해당 위치까지 잘 오셨습니다.", Toast.LENGTH_LONG).show();
            score = String.valueOf(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getPoint());
            InsertData task=new InsertData();
            task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_DD.php", DBHandler.currentUserData.getMember_id(),score);
            DBHandler.currentUserData.setMember_score(DBHandler.currentUserData.getMember_score()+Integer.parseInt(score));
            DBHandler.currentUserData.setMember_currentQuest(DBHandler.currentUserData.getMember_currentQuest()+1);
            gps.stopUsingGPS();

            //TEST용 나중에 밑에 if문 삭제하고 else문 내용만 남기자
            if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitleID() == 2 && DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()-1).getTitleID() != DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getTitleID())
            {
                Intent intent = new Intent(getApplicationContext(), VideoChapter1Activity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), ReceiveQuestActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else
        {
            // 도착지에 도착하지 않았을때.
            Toast.makeText(getBaseContext(), "해당 위치에 위치하지 않았습니다.", Toast.LENGTH_LONG).show();
            gps.stopUsingGPS();
            finish();
        }

    }

    // 해당 지점 근처에 위치했는지 검사하는 함수. return true->해당 지점에 도착했을때 ,, return false->해당지점 도착x
    private boolean compareLocaton(){
        // 현재 나의 위도 경도를 변수에 저장.
        mylocation = new LatLng(gps.getLatitude(),gps.getLongitude());
        //******* 일단 야매로 도착지 위도 경도 넣기 ******* 나중에 꼭 수정하기********
        destination = new LatLng(37.494630,126.960156);

        // 거리 차이가 10m 이하 일때는 도착지에 도착했다.
        if(distance(mylocation.latitude, mylocation.longitude,destination.latitude,destination.longitude) < 10)
            return true;
        else return false;


    }


    // 두 지점의 거리 차이를 계산하는 함수
    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        // 거리를 meter 단위로 변환.
        dist = dist * 1609.344;

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
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

                // onSignupFailed();
                return new String("Error: " + e.getMessage());
            }

        }

    }

}
