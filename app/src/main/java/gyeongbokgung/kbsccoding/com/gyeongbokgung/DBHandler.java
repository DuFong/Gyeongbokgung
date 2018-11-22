package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DBHandler {
    private static final String TAG = "디비핸들러함수";

    public static ArrayList<Quest> questDataList = new ArrayList<>();
    public static PersonalData currentUserData = new PersonalData();

    public static boolean[] isTutorial = new boolean[20];
    public static boolean isGetChildView;
    public static boolean isLogin;

    public static TextView[] box = new TextView[20];
    public static TextView[] explain = new TextView[20];
    public static TextView darkBackgroundUp, darkBackgroundDown, durumariDown;

    public static void showTutorial() {

        InsertData task;
        // for문을 통해 box, explain 객체 찾기, 안보이게 하기

        TextView darkBackground = MapsActivity.mapView.findViewById(R.id.dark_background_base);
        darkBackground.setVisibility(View.GONE);

        box[1] = MapsActivity.mapView.findViewById(R.id.box1);
        explain[1] = MapsActivity.mapView.findViewById(R.id.explain1);
        explain[2] = MapsActivity.mapView.findViewById(R.id.explain2);
        explain[3] = MapsActivity.mapView.findViewById(R.id.explain2);
        explain[6] = MapsActivity.mapView.findViewById(R.id.explain6);
        explain[7] = MapsActivity.mapView.findViewById(R.id.explain7);


        darkBackgroundUp = MapsActivity.mapView.findViewById(R.id.dark_background_quest_up);
        darkBackgroundDown = MapsActivity.mapView.findViewById(R.id.dark_background_quest_down);
        durumariDown = MapsActivity.mapView.findViewById(R.id.durumari_down);

        darkBackgroundUp.setVisibility(View.GONE);
        darkBackgroundDown.setVisibility(View.GONE);
        box[1].setVisibility(View.GONE);
        explain[1].setVisibility(View.GONE);
        explain[2].setVisibility(View.GONE);
        explain[3].setVisibility(View.GONE);
        explain[6].setVisibility(View.GONE);
        explain[7].setVisibility(View.GONE);


        switch (currentUserData.getMember_numTutorial()) {
            case 1:     // 메인 바 설명
                Log.d(TAG, "케이스1");

                darkBackgroundUp.setVisibility(View.VISIBLE);
                darkBackgroundDown.setVisibility(View.GONE);
                box[1].setVisibility(View.VISIBLE);
                explain[1].setVisibility(View.VISIBLE);

                MapsActivity.fab_quest.setEnabled(false);
                MapsActivity.fab_ranking.setEnabled(false);
                MapsActivity.fab_logout.setEnabled(false);
                break;
            case 2:     // 복원하기 버튼 설명
                if(isGetChildView) {
                    Log.d(TAG, "케이스2");

                    task = new InsertData();
                    task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                    darkBackgroundUp.setVisibility(View.VISIBLE);
                    darkBackgroundDown.setVisibility(View.GONE);
                    box[1].setVisibility(View.GONE);
                    explain[1].setVisibility(View.GONE);
                    box[3].setVisibility(View.GONE);
                    explain[3].setVisibility(View.GONE);
                    box[2].setVisibility(View.VISIBLE);
                    explain[2].setVisibility(View.VISIBLE);

                    MapsActivity.fab_quest.setEnabled(false);
                    MapsActivity.fab_ranking.setEnabled(false);
                    MapsActivity.fab_logout.setEnabled(false);

                    isGetChildView = false;
                }
                break;
            case 3:     // 메인 바 설명
                if(isGetChildView) {
                    Log.d(TAG, "케이스3");

                    task = new InsertData();
                    task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                    darkBackgroundUp.setVisibility(View.VISIBLE);
                    darkBackgroundDown.setVisibility(View.GONE);
                    box[2].setVisibility(View.GONE);
                    explain[2].setVisibility(View.GONE);
                    explain[1].setText("이번 퀘스트를 수행하기 위하여\n다시 한번 퀘스트 바를 누릅니다.");
                    box[1].setVisibility(View.VISIBLE);
                    explain[1].setVisibility(View.VISIBLE);

                    MapsActivity.fab_quest.setEnabled(false);
                    MapsActivity.fab_ranking.setEnabled(false);
                    MapsActivity.fab_logout.setEnabled(false);

                    isGetChildView = false;
                }
                break;
            case 4:     // 힌트사용 설명
                if(isGetChildView) {
                    Log.d(TAG, "케이스4");

                    task = new InsertData();
                    task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                    darkBackgroundUp.setVisibility(View.VISIBLE);
                    darkBackgroundDown.setVisibility(View.GONE);
                    box[1].setVisibility(View.GONE);
                    explain[1].setVisibility(View.GONE);
                    explain[3].setText("문제풀이 형식의 퀘스트에는 힌트가 있습니다. 힌트사용 버튼을 눌러보세요.");
                    box[2].setVisibility(View.GONE);
                    explain[2].setVisibility(View.GONE);
                    box[3].setVisibility(View.VISIBLE);
                    explain[3].setVisibility(View.VISIBLE);

                    ExpandableListAdapter.buttonRestore.setEnabled(false);

                    MapsActivity.fab_quest.setEnabled(false);
                    MapsActivity.fab_ranking.setEnabled(false);
                    MapsActivity.fab_logout.setEnabled(false);

                    isGetChildView = false;
                }
                break;
            case 5:     // 복원하기 설명
                if(isGetChildView) {
                    Log.d(TAG, "케이스5");

                    task = new InsertData();
                    task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                    darkBackgroundUp.setVisibility(View.VISIBLE);
                    darkBackgroundDown.setVisibility(View.GONE);
                    box[1].setVisibility(View.GONE);
                    explain[1].setVisibility(View.GONE);
                    box[3].setVisibility(View.GONE);
                    explain[3].setVisibility(View.GONE);
                    explain[2].setText("문제를 풀 때도 복원하기 버튼을 사용합니다. 복원하기 버튼을 누르세요.");
                    box[2].setVisibility(View.VISIBLE);
                    explain[2].setVisibility(View.VISIBLE);

                    ExpandableListAdapter.buttonRestore.setEnabled(true);

                    MapsActivity.fab_quest.setEnabled(false);
                    MapsActivity.fab_ranking.setEnabled(false);
                    MapsActivity.fab_logout.setEnabled(false);

                    isGetChildView = false;
                }
                break;
            case 6:     //메뉴 설명
                if(isGetChildView) {
                    Log.d(TAG, "케이스6");

                    task = new InsertData();
                    task.execute("http://" + "gyeongbokgung.dothome.co.kr" + "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                    darkBackgroundUp.setVisibility(View.GONE);
                    darkBackgroundDown.setVisibility(View.VISIBLE);
                    box[1].setVisibility(View.GONE);
                    explain[1].setVisibility(View.GONE);
                    box[2].setVisibility(View.GONE);
                    explain[2].setVisibility(View.GONE);
                    box[3].setVisibility(View.GONE);
                    explain[3].setVisibility(View.GONE);
                    explain[6].setVisibility(View.VISIBLE);

                    MapsActivity.fab_quest.setEnabled(false);
                    MapsActivity.fab_ranking.setEnabled(false);
                    MapsActivity.fab_logout.setEnabled(false);

                    isGetChildView = false;
                }
                break;
            case 7:     // 퀘스트 목록 설명
                Log.d(TAG, "케이스7");

                task = new InsertData();
                task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                darkBackgroundDown.setVisibility(View.VISIBLE);
                explain[7].setVisibility(View.VISIBLE);

                MapsActivity.fab_quest.setEnabled(true);
                MapsActivity.fab_ranking.setEnabled(false);
                MapsActivity.fab_logout.setEnabled(false);
                break;
            case 8:     // 퀘스트 안에 들어갔을 때, 튜토리얼을 선택하도록 설명
                Log.d(TAG, "케이스8");

                task = new InsertData();
                task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                break;
            case 9:     // 튜토리얼을 선택하면 튜토리얼 설명을 없앰
                Log.d(TAG, "케이스8");

                task = new InsertData();
                task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                explain[8].setVisibility(View.GONE);
                break;
            case 10:    // 랭킹 설명
                Log.d(TAG, "케이스8");

                task = new InsertData();
                task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                darkBackgroundDown.setVisibility(View.VISIBLE);
                explain[7].setText("랭킹버튼을 선택하여 내 랭킹을\n확인할 수 있습니다.");
                explain[7].setVisibility(View.VISIBLE);

                MapsActivity.fab_quest.setEnabled(true);
                MapsActivity.fab_ranking.setEnabled(true);
                MapsActivity.fab_logout.setEnabled(false);
                break;
            case 11:
                Log.d(TAG, "케이스8");

                task = new InsertData();
                task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_tutorial.php", DBHandler.currentUserData.getMember_id(), Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

                darkBackgroundDown.setVisibility(View.GONE);
                darkBackgroundUp.setVisibility(View.GONE);
                explain[7].setVisibility(View.GONE);

                MapsActivity.fab_quest.setEnabled(true);
                MapsActivity.fab_ranking.setEnabled(true);
                MapsActivity.fab_logout.setEnabled(true);
        }
    }

   static class InsertData extends AsyncTask<String, Void,String > {

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
}
