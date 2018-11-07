package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

    private ArrayList<PersonalData> mList = null;
    private Activity context = null;


    public UsersAdapter(Activity context, ArrayList<PersonalData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView rank;
        protected TextView name;
        protected TextView score;


        public CustomViewHolder(View view) {
            super(view);
            this.rank = (TextView) view.findViewById(R.id.textView_list_rank);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.score = (TextView) view.findViewById(R.id.textView_list_score);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        Log.d("UsersAdapter","~~~position"+position);
        viewholder.name.setText(mList.get(position).getMember_name());

        viewholder.rank.setText(String.valueOf(mList.get(position).getMember_rank()));
        viewholder.score.setText(String.valueOf(mList.get(position).getMember_score()));
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}