package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private int nowType=0;
    private String TAG ="ExpandableListAdapter";
    private String num;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = groupItem , i1 = childItem
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_quest_summary,null);
        }
        TextView listHeader = view.findViewById(R.id.listHeader);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String)getChild(i,i1);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_quest_detail, null);
        }
        TextView txtListChild = view.findViewById(R.id.listItem);
        Button buttonHint = view.findViewById(R.id.hint_button);
        if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getHint().equals("null")){
            buttonHint.setVisibility(View.INVISIBLE);
        }
        else{
            buttonHint.setVisibility(View.VISIBLE);
        }
        Button buttonRestore = view.findViewById(R.id.restore_button);
        txtListChild.setText(childText);
        buttonHint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // db에서 문제 id로 switch문 작성
                Intent intent=null;
                if(DBHandler.currentUserData.getMember_currentQuest()==6){
                    intent = new Intent(context, HintImageActivity.class);
                }
                else{
                    intent = new Intent(context, HintActivity.class);
                    Log.d("힌트사용", "사용");
                }
                //Intent intent = new Intent(getApplicationContext(), HintImageActivity.class);
                context.startActivity(intent);
            }
        });
        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowType=DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getType();
                Intent intent = null;
                if(nowType == 0 ){
                    intent = new Intent(context, RestoreLocationActivity.class);
                }
                else if(nowType == 1) {
                    intent = new Intent(context, RestoreActivity.class);
                }
                else if(nowType == 2){
                    intent = new Intent(context, Restore2Activity.class);
                }
                else if(nowType == 3){
                    intent = new Intent(context, Restore3Activity.class);
                }
                else {
                    Log.d("ExpandableListAdapter","type 설정 이상");
                }
                context.startActivity(intent);
            }
        });
        Log.d("numTorial", Integer.toString(DBHandler.currentUserData.getMember_numTutorial()));

        // 튜토리얼창 안보이게 하기
        if(DBHandler.currentUserData.getMember_numTutorial() == 1) {
            TextView box = MapsActivity.mapView.findViewById(R.id.box1);
            TextView line1 = MapsActivity.mapView.findViewById(R.id.line1_1);
            TextView line2 = MapsActivity.mapView.findViewById(R.id.line1_2);
            TextView explain = MapsActivity.mapView.findViewById(R.id.explain1);

            box.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            explain.setVisibility(View.GONE);

            DBHandler.currentUserData.setMember_numTutorial(2);
            //DB에도 업데이트
            InsertData task=new InsertData();
            task.execute("http://" + "gyeongbokgung.dothome.co.kr"+ "/update_tutorial.php", DBHandler.currentUserData.getMember_id(),num);

            //     SaveSharedPreference2.setNumTutorial(this.context);

      /*      if(DBHandler.numTutorial == 2) {
                TextView box = MapsActivity.mapView.findViewById(R.id.box2);
                TextView line1 = MapsActivity.mapView.findViewById(R.id.line2_1);
                TextView line2 = MapsActivity.mapView.findViewById(R.id.line2_2);
                TextView explain = MapsActivity.mapView.findViewById(R.id.explain2);

                box.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                explain.setVisibility(View.GONE);
            }*/
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
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

}
