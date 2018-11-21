package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.pm10.library.CircleIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class QuestsViewActivity extends AppCompatActivity {

    private static final String TAG = QuestsViewActivity.class.getSimpleName();
    private static final String TAGQ = "Quest DB Connect";
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    public SectionedRecyclerViewAdapter sectionAdapter;
    public ArrayList<Quest> mArrayList;
    //  private String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests_view);

        // 레이아웃매니저 세팅
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 디바이더 세팅
//        DividerItemDecoration dividerItemDecoration =
//                new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
//        mRecyclerView.addItemDecoration(dividerItemDecoration);


        // 데이터 세팅
        mArrayList = new ArrayList<Quest>();
        for (int i = 0; i < DBHandler.questDataList.size(); i++) {
            Quest q = DBHandler.questDataList.get(i);
            Log.d(TAG, "i는?" + DBHandler.questDataList.get(i));
            mArrayList.add(q);
        }

        // Toolbar 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_close);

//        Map<String, Quest> ssTitle = new HashMap<>();
//        for (int i = 0; i < mArrayList.size(); i++) {
//            ssTitle.put(mArrayList.get(i).getSubTitle(), mArrayList.get(i));
//        }
//
//        Multimap<String, Quest> multiMap = ArrayListMultimap.create();
//        for (String subTitle : ssTitle.keySet()) {
//            multiMap.put(ssTitle.get(subTitle).getTitle(), ssTitle.get(subTitle));
//        }

        // 중복이 제거된 subID
        Map<Integer, Quest> ssTitle = new HashMap<>();
        for (int i = 0; i < mArrayList.size(); i++) {
            ssTitle.put(mArrayList.get(i).getSubID(), mArrayList.get(i));
        }

        //
        Multimap<Integer, Quest> multiMap = MultimapBuilder.treeKeys().linkedListValues().build();
        for (Integer subId : ssTitle.keySet()) {
            multiMap.put(ssTitle.get(subId).getTitleID(), ssTitle.get(subId));
        }

        // 어댑터 세팅
        sectionAdapter = new SectionedRecyclerViewAdapter();
        for (Integer titleId : multiMap.keySet()) {
            List<Quest> quests = (List<Quest>) multiMap.get(titleId);

            Log.d(TAG, "타타타"+quests.get(0).getTitleID());
            Collections.sort(quests, new Comparator<Quest>() {
                @Override
                public int compare(Quest t1, Quest t2) {
                    if(t1.getSubID()>t2.getSubID()){
                        return 1;
                    } else if(t1.getSubID()<t2.getSubID()){
                        return -1;
                    } else{
                        return 0;
                    }
                }
            });
            for(int i=0;i<quests.size();i++){
                Log.d(TAG,"rowId=" +quests.get(i).getRowID());
            }
            QuestSection section = new QuestSection(getApplicationContext(), titleId, quests);
            sectionAdapter.addSection(section);
        }

        mRecyclerView.setAdapter(sectionAdapter);

        int currentQuest = DBHandler.currentUserData.getMember_currentQuest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                // 튜토리얼
                if(DBHandler.currentUserData.getMember_numTutorial() == 9) {
                    DBHandler.currentUserData.setMember_numTutorial(10);
                    DBHandler.showTutorial();
                }
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public class QuestSection extends StatelessSection {
        Context context;
        Integer titleId;
        List<Quest> list;
        boolean expanded = false;

        public QuestSection(Context context, Integer titleId, List<Quest> list) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.section_item)
                    .headerResourceId(R.layout.section_header)
                    .build());

            this.context = context;
            this.titleId = titleId;
            this.list = list;
            expanded = false;
        }


        @Override
        public int
        getContentItemsTotal() {
            return expanded ? list.size() : 0;
        }

        @Override
        public RecyclerView.ViewHolder
        getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void
        onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            QuestSection.ItemViewHolder itemHolder = (QuestSection.ItemViewHolder) holder;

            final String subTitle = list.get(position).getSubTitle();
            itemHolder.tvItem.setText(subTitle);

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 튜토리얼
                    if(DBHandler.currentUserData.getMember_numTutorial() == 8){
                        DBHandler.currentUserData.setMember_numTutorial(9);
                        Handler delayHandler = new Handler();
                        delayHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // TODO
                                DBHandler.showTutorial();
//                                headerHolder.rootView.setBackgroundColor(getResources().getColor(R.color.white)); // 다희야 이부분 원래색으로 돌려줘 ㅎㅎ
//                                nestedScrollView.setBackgroundColor(getResources().getColor(R.color.gridBackgroundColor));
                                sectionAdapter.notifyDataSetChanged();
                            }
                        }, 1000);
                    }
                    Intent intent = new Intent(QuestsViewActivity.this, QuestDetailActivity.class);
                    intent.putExtra("title", subTitle); // 데이터 송신
                    startActivity(intent);
                }
            });

            // 현재
            if ( list.get(position).getRowID()>=DBHandler.currentUserData.getMember_currentQuest()) {
                Log.d(TAG, list.get(position).getRowID()+"<<<"+DBHandler.currentUserData.getMember_currentQuest());
                itemHolder.rootView.setBackgroundColor(getResources().getColor(R.color.grey));
                itemHolder.ivItem.setImageDrawable(getDrawable(R.drawable.ic_lock));
                itemHolder.rootView.setClickable(false);
            }
        }

        @Override
        public RecyclerView.ViewHolder
        getHeaderViewHolder(View view) {
            return new QuestSection.HeaderViewHolder(view);
        }

        @Override
        public void
        onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            DBHandler.explain[8] = findViewById(R.id.explain8);
            DBHandler.explain[8].setVisibility(View.GONE);
            final QuestSection.HeaderViewHolder headerHolder = (QuestSection.HeaderViewHolder) holder;
            headerHolder.tvTitle.setText(list.get(0).getTitle());
            headerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("@@클릭", "??");
                    expanded = !expanded;
                    headerHolder.ivArrow.setImageResource(
                            expanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down
                    );

                    sectionAdapter.notifyDataSetChanged();
                }
            });

            // 튜토리얼
            if(!list.get(0).getTitle().equals("튜토리얼") && DBHandler.currentUserData.getMember_numTutorial() == 8){
                DBHandler.explain[8].setVisibility(View.VISIBLE);
                DBHandler.showTutorial();
                // 다희야 리스트 회색으로 바꿔줘 ->ㅇㅋ
                headerHolder.rootView.setBackgroundColor(getResources().getColor(R.color.grey));
            } else
                headerHolder.rootView.setBackgroundColor(getResources().getColor(R.color.white));

        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            private final View rootView;
            private final TextView tvTitle;
            private final ImageView ivTitle;
            private final ImageView ivArrow;

            HeaderViewHolder(View view) {
                super(view);

                rootView = view;
                tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                ivTitle = (ImageView) view.findViewById(R.id.ivTitle);
                ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            private final View rootView;
            private final TextView tvItem;
            private final ImageView ivItem;

            ItemViewHolder(View view) {
                super(view);

                rootView = view;
                tvItem = (TextView) view.findViewById(R.id.tvItem);
                ivItem = (ImageView) view.findViewById(R.id.ivItem);
            }
        }
    }

}
