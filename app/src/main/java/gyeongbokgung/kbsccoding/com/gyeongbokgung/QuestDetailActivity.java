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

import android.widget.ImageButton;
import android.widget.TextView;

import com.pm10.library.CircleIndicator;

import org.w3c.dom.Text;

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
    public ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_detail);

        // 데이터 수신
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        subList = new ArrayList<>();
        for(int i=0;i<DBHandler.questDataList.size();i++){
            if(DBHandler.questDataList.get(i).getSubTitle().equals(title)){
                Log.d("보노보노", String.valueOf(DBHandler.questDataList.size()));
                subList.add(DBHandler.questDataList.get(i));
            }
        }

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

        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        circleIndicator.setupWithViewPager(mViewPager);
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
            TextView tv_explanation = (TextView) rootView.findViewById(R.id.tv_explanation);
            TextView label_explanation = (TextView) rootView.findViewById(R.id.label_explanation);
            //            TextView tv_goal = (TextView) rootView.findViewById(R.id.tv_goal);

            int i = getArguments().getInt(ARG_SECTION_NUMBER)-1;
            tv_description.setText(subList.get(i).getDescription());
            if(subList.get(i).getExplanation().equals("null")){
               tv_explanation.setVisibility(View.GONE);
               label_explanation.setVisibility(View.GONE);
            } else {
                tv_explanation.setText(subList.get(i).getExplanation());
            }
//            switch (subList.get(i).getType()){
//                case 0:
//                    tv_goal.setText("Lat: "+subList.get(i).getLatitude()+"Lng: "+subList.get(i).getLongitude());
//                    break;
//                case 1:
//                    tv_goal.setText(subList.get(i).getGoal());
//                    break;
//                case 2:
//                    tv_goal.setText(subList.get(i).getGoal2());
//                    break;
//                case 3:
//                    tv_goal.setText(subList.get(i).getGoal3());
//                    break;
//            }
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
            // Return a PlaceholderFragment (defined as a static inner class below)
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return subList.size();
        }


    }

}
