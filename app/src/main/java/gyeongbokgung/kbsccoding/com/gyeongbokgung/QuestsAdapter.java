package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.QuestsViewHolder>{
    private Context context;
    private ArrayList<Quest> quests;

    // Constructor
    public QuestsAdapter(Context context, ArrayList<Quest> quests) {
        this.context = context;
        this.quests = quests;
    }

    @Override
    public QuestsAdapter.QuestsViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quest, null);
        QuestsViewHolder viewHolder = new QuestsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(QuestsViewHolder holder, final int position) {
        holder.textView.setText(quests.get(position).getTitle());
        holder.imageView.setImageResource(quests.get(position).drawable);
        holder.imageView.setColorFilter(Color.parseColor("#BDBDBD"),PorterDuff.Mode.MULTIPLY);

        // 클릭 이벤트 발생 시
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), position + " clicked!", Toast.LENGTH_SHORT).show();
                Context context = view.getContext();
                Intent intent = new Intent(context, QuestDetailActivity.class);
         //       intent.putExtra("quests", quests);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }

    // Viewholder
    public static class QuestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        public QuestsViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
            relativeLayout = v.findViewById(R.id.relativeLayout);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Log.d("클릭!:",String.valueOf(position));
            Toast.makeText(view.getContext(), getLayoutPosition() + " clicked!", Toast.LENGTH_SHORT).show();
        }

    }


}
