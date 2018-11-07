package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class StagelistActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {
    RecyclerView recyclerView;
    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stagelist);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList();
        arrayList.add(new StageData("Item 1", R.drawable.ic_format_align_justify_black_24dp, "#09A9FF"));
        arrayList.add(new StageData("Item 2", R.drawable.ic_format_align_justify_black_24dp, "#3E51B1"));
        arrayList.add(new StageData("Item 3", R.drawable.ic_format_align_justify_black_24dp, "#673BB7"));
        arrayList.add(new StageData("Item 4", R.drawable.ic_format_align_justify_black_24dp, "#4BAA50"));
        arrayList.add(new StageData(
                "Item 5",R.drawable.ic_format_align_justify_black_24dp, "#F94336"));
        arrayList.add(new StageData("Item 6", R.drawable.ic_format_align_justify_black_24dp, "#0A9B88"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        /*AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);*/


        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(StageData item) {

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();

    }
}
