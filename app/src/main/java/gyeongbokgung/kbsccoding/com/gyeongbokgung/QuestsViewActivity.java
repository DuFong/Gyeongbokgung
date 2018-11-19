package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Context;
import android.content.Intent;
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
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class QuestsViewActivity extends AppCompatActivity {

    private static final String TAG = MapsActivity.class.getSimpleName();
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
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        // 데이터 세팅
        mArrayList = DBHandler.questDataList;

        // Toolbar 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_close);

        // 테스트용 데이터 세팅
//        mArrayList = new ArrayList<>();
//        mArrayList.add(new Quest(0, 0, 0, "튜토리얼", "조선총독부 철거!", "일본은 우리나라의 국권을 빼앗고 강제로 통치하기 위해 경복궁 앞에 조선총독부를 세웠습니다. 우선 조선총독부를 철거해야 합니다. 광화문으로 이동하시오.", "광화문으로 이동하시오.", "광화문 근처 이동 시", "", "", 10, 0));
//        mArrayList.add(new Quest(0, 0, 1, "튜토리얼", "조선총독부 철거!", "광화문에서 경복궁을 바라보시오. 현재 앞에는 OOOOO가 경복궁을 가로막고 있습니다. ", "현재 OOOOO가 경복궁을 가로막고 있습니다.", "조선총독부", "우린 이것을 철거해야 한다.", "일제 강점기에 조선을 지배했던 식민 통치 기구이다. 35년간 사실상 한반도의 정부 노릇을 했으며, 민족정기를 말살하고 조선인을 탄압하는 정치를 폈다.\n" +
//                "조선의 법궁인 경복궁 안에 세웠던 조선 총독부\n" +
//                "조선의 임금들이 나랏일을 보던 근정전을 가로막고 서 있었다. 이 건물은 해방 후 중앙청과 국립중앙박물관으로 사용되다가, 1995년 김영삼 정부 때 완전히 철거되어 지금은 사라지고 없다.\n" +
//                "조선 총독부 건물은 위에서 내려다보면 일(日) 자 모양을 하고 있었다. 조선의 민족정기를 훼손하고 조선이 일제의 지배 아래에 있다는 것을 보여 주기 위해 의도적으로 세운 것이다. 실제로 조선 총독부는 일제 강점기 35년 동안 우리의 말과 역사 교육을 금지하고, 창씨개명과 신사 참배를 강요하는 등 민족 말살 정책을 폈다.", 10, 0));
//        mArrayList.add(new Quest(1, 1, 2, "행사의 장", "궁의 광대", "매표소에서 표를 끊어 경복궁으로 입장하십시오.", "", "", "", "", 10, 0));
//        mArrayList.add(new Quest(1, 1, 3, "행사의 장", "궁의 광대", "경복궁은 왕이 정치를 하고 생활하던 엄숙한 공간이다. 하지만 이 엄숙함 속에서 해학적인 요소가 존재한다. 지금부터 OOO을 보여주고 있는 웃긴 놈을 찾으시오.", "", "", "", "이 조각상은 천록이라 불리는 상상의 동물이다. 옛 문헌에 보면 \"천록은 아주 선한 짐승이다. 왕의 밝은 은혜가 아래로 두루 미치면 나타난다.\"고 하는 상서로운 짐승이다. 이 중 하나의 천록은 혓바닥을 내밀고 있는데 이는 경복궁 건축이 치밀하면서도 여유롭다는 것을 나타낸다.", 1, 0));
//        mArrayList.add(new Quest(1, 2, 4, "행사의 장", "반반", "지정된 위치로 이동하시오.", "", "", "", "", 1, 0));
//        mArrayList.add(new Quest(1, 2, 5, "행사의 장", "반반", "인왕산과 북악산.....", "", "", "", "", 1, 0));
//        mArrayList.add(new Quest(1, 2, 6, "행사의 장", "반반", "석견, 새끼석견...", "", "", "", "", 1, 0));

        Map<String, Quest> ssTitle = new HashMap<>();
        for (int i = 0; i < mArrayList.size(); i++) {
            ssTitle.put(mArrayList.get(i).getSubTitle(), mArrayList.get(i));
        }

        Multimap<String, Quest> multiMap = ArrayListMultimap.create();
        for (String subTitle : ssTitle.keySet()) {
            multiMap.put(ssTitle.get(subTitle).getTitle(), ssTitle.get(subTitle));
        }
        // 어댑터 세팅
        sectionAdapter = new SectionedRecyclerViewAdapter();

        for (String title : multiMap.keySet()) {
            List<Quest> quests = ((ArrayListMultimap<String, Quest>) multiMap).get(title);
            QuestSection section = new QuestSection(getApplicationContext(), title, quests);
            sectionAdapter.addSection(section);
        }

        mRecyclerView.setAdapter(sectionAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
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

        String title;
        List<Quest> list;
        boolean expanded = false;

        public QuestSection(Context context, String title, List<Quest> list) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.section_item)
                    .headerResourceId(R.layout.section_header)
                    .build());
            this.context = context;
            this.title = title;
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
                    Intent intent = new Intent(QuestsViewActivity.this, QuestDetailActivity.class);
                    intent.putExtra("title", subTitle); // 데이터 송신
                    startActivity(intent);
                }
            });
            // list.get(position)
        }

        @Override
        public RecyclerView.ViewHolder
        getHeaderViewHolder(View view) {
            return new QuestSection.HeaderViewHolder(view);
        }

        @Override
        public void
        onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            final QuestSection.HeaderViewHolder headerHolder = (QuestSection.HeaderViewHolder) holder;
            headerHolder.tvTitle.setText(title);
            headerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("@@클릭","??");
                    expanded = !expanded;
                    headerHolder.ivArrow.setImageResource(
                            expanded?R.drawable.ic_arrow_up:R.drawable.ic_arrow_down
                    );
                    sectionAdapter.notifyDataSetChanged();
                }
            });
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

            ItemViewHolder(View view) {
                super(view);

                rootView = view;
                tvItem = (TextView) view.findViewById(R.id.tvItem);
            }
        }
    }

}
