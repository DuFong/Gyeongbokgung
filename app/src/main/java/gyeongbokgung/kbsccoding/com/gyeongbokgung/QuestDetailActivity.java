package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestDetailActivity extends AppCompatActivity {
    public static ArrayList<Quest> subList;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_detail);

        // 데이터 수신
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        // 테스트용 데이터 세팅
        ArrayList<Quest> mArrayList = new ArrayList<>();
        mArrayList.add(new Quest(0, 0, 0, "튜토리얼", "조선총독부 철거!", "일본은 우리나라의 국권을 빼앗고 강제로 통치하기 위해 경복궁 앞에 조선총독부를 세웠습니다. 우선 조선총독부를 철거해야 합니다. 광화문으로 이동하시오.", "광화문으로 이동하시오.", "광화문 근처 이동 시", "", "", 10, 0));
        mArrayList.add(new Quest(0, 0, 1, "튜토리얼", "조선총독부 철거!", "광화문에서 경복궁을 바라보시오. 현재 앞에는 OOOOO가 경복궁을 가로막고 있습니다. ", "현재 OOOOO가 경복궁을 가로막고 있습니다.", "조선총독부", "우린 이것을 철거해야 한다.", "일제 강점기에 조선을 지배했던 식민 통치 기구이다. 35년간 사실상 한반도의 정부 노릇을 했으며, 민족정기를 말살하고 조선인을 탄압하는 정치를 폈다.\n" +
                "조선의 법궁인 경복궁 안에 세웠던 조선 총독부\n" +
                "조선의 임금들이 나랏일을 보던 근정전을 가로막고 서 있었다. 이 건물은 해방 후 중앙청과 국립중앙박물관으로 사용되다가, 1995년 김영삼 정부 때 완전히 철거되어 지금은 사라지고 없다.\n" +
                "조선 총독부 건물은 위에서 내려다보면 일(日) 자 모양을 하고 있었다. 조선의 민족정기를 훼손하고 조선이 일제의 지배 아래에 있다는 것을 보여 주기 위해 의도적으로 세운 것이다. 실제로 조선 총독부는 일제 강점기 35년 동안 우리의 말과 역사 교육을 금지하고, 창씨개명과 신사 참배를 강요하는 등 민족 말살 정책을 폈다.", 10, 0));
        mArrayList.add(new Quest(1, 1, 2, "행사의 장", "궁의 광대", "매표소에서 표를 끊어 경복궁으로 입장하십시오.", "", "", "", "", 10, 0));
        mArrayList.add(new Quest(1, 1, 3, "행사의 장", "궁의 광대", "경복궁은 왕이 정치를 하고 생활하던 엄숙한 공간이다. 하지만 이 엄숙함 속에서 해학적인 요소가 존재한다. 지금부터 OOO을 보여주고 있는 웃긴 놈을 찾으시오.", "", "", "", "이 조각상은 천록이라 불리는 상상의 동물이다. 옛 문헌에 보면 \"천록은 아주 선한 짐승이다. 왕의 밝은 은혜가 아래로 두루 미치면 나타난다.\"고 하는 상서로운 짐승이다. 이 중 하나의 천록은 혓바닥을 내밀고 있는데 이는 경복궁 건축이 치밀하면서도 여유롭다는 것을 나타낸다.", 1, 0));
        mArrayList.add(new Quest(1, 2, 4, "행사의 장", "반반", "지정된 위치로 이동하시오.", "", "", "", "", 1, 0));
        mArrayList.add(new Quest(1, 2, 5, "행사의 장", "반반", "인왕산과 북악산.....", "", "", "", "", 1, 0));
        mArrayList.add(new Quest(1, 2, 6, "행사의 장", "반반", "석견, 새끼석견...", "", "", "", "", 1, 0));


        subList = new ArrayList<>();
        for(int i=0;i<mArrayList.size();i++){
            if(mArrayList.get(i).getSubTitle().equals(title)){
                subList.add(mArrayList.get(i));
            }
        }

//        for(int i=0;i<subList.size();i++){
//            Log.d(i+"번째???",subList.get(i).getDescription());
//        }

        // Toolbar 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        toolbar.setTitle(title);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_left);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_quest_detail, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quest_detail, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            TextView tv_description = (TextView) rootView.findViewById(R.id.tv_description);
            int i = getArguments().getInt(ARG_SECTION_NUMBER)-1;
            tv_description.setText(subList.get(i).getDescription());
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return subList.size();
        }
    }
}
