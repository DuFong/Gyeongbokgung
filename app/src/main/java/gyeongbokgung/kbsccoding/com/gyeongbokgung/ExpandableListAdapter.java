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

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private int nowType=0;
    private String TAG ="ExpandableListAdapter";

 //   public static View expandableView;

    public static Button buttonHint;
    public static Button buttonRestore;

 //   static int countExcuted = 0;


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
        Log.d("익스펜더블함수", "부모!!!");
        DBHandler.durumariDown.setVisibility(View.VISIBLE);

        Typeface regular = Typeface.createFromAsset(this.context.getAssets(), "font/yuryeob.ttf");
        listHeader.setTypeface(regular, Typeface.NORMAL);
        if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getRowID()==9||DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getRowID()==11)
        {
            regular = Typeface.createFromAsset(this.context.getAssets(), "font/chinese.TTF");
            listHeader.setTypeface(regular, Typeface.NORMAL);
        }return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        DBHandler.isGetChildView = true;

        DBHandler.durumariDown.setVisibility(View.GONE);

        final String childText = (String)getChild(i,i1);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_quest_detail, null);
        }

        //expandableView = view;

        TextView txtListChild = view.findViewById(R.id.listItem);
        buttonHint = view.findViewById(R.id.hint_button);
        if(DBHandler.questDataList.get(DBHandler.currentUserData.getMember_currentQuest()).getHint().equals("null")){
            buttonHint.setVisibility(View.INVISIBLE);
        }
        else{
            buttonHint.setVisibility(View.VISIBLE);
        }
        buttonRestore = view.findViewById(R.id.restore_button);
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

        DBHandler.box[2] = view.findViewById(R.id.box2);
        DBHandler.box[3] = view.findViewById(R.id.box3);

        Log.d("익스펜더블함수", "getChildView호출!");

        if(DBHandler.currentUserData.getMember_numTutorial() > 5) {
            DBHandler.box[2].setVisibility(View.GONE);
            DBHandler.box[3].setVisibility(View.GONE);
            DBHandler.explain[2].setVisibility(View.GONE);
            DBHandler.explain[3].setVisibility(View.GONE);
        }
        else if(DBHandler.currentUserData.getMember_numTutorial() == 1 || DBHandler.currentUserData.getMember_numTutorial() == 2/* && countExcuted == 1*/) {
            Log.d("익스펜더블함수", "케이스2");
            DBHandler.currentUserData.setMember_numTutorial(2);
            //isExecuted = true;
            DBHandler.showTutorial();
        }
        else if(DBHandler.currentUserData.getMember_numTutorial() == 3) {
            Log.d("익스펜더블함수", "케이스3");
            DBHandler.isTutorial[3] = true;
            DBHandler.currentUserData.setMember_numTutorial(4);
            DBHandler.showTutorial();
        }

        if(DBHandler.isTutorial[2] || DBHandler.isTutorial[3] || DBHandler.isTutorial[4] || DBHandler.isTutorial[5] || DBHandler.isTutorial[6] || DBHandler.isTutorial[7])
            DBHandler.showTutorial();   // 복원하기에 대한 설명을 없애기 위함

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
