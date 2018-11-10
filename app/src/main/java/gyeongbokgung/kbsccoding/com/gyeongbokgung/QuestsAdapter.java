package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.QuestsViewHolder> {
    private Context context;
    private ArrayList<Quest> quests = new ArrayList<Quest>();
    protected ItemListener listener;

    public QuestsAdapter(Context context, ArrayList<Quest> quests, QuestsAdapter.ItemListener listener) {
        this.context = context;
        this.quests = quests;
        this.listener = listener;
    }

    public static class QuestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        Quest item;
        private ItemListener listener;

        public QuestsViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
        }

        public void setData(Quest item) {
            this.item = item;
            textView.setText(item.getTitle());
            imageView.setImageResource(item.drawable);
            relativeLayout.setBackgroundColor(Color.parseColor(item.color));
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(item);
            }
        }
    }

    @Override
    public QuestsAdapter.QuestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quest, parent, false);
        QuestsViewHolder viewHolder = new QuestsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuestsViewHolder holder, int position) {
        holder.setData((Quest) quests.get(position));
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }

    public interface ItemListener {
        void onItemClick(Quest item);
    }
}
