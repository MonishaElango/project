package com.hertzai.hevolve.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hertzai.hevolve.R;
import com.hertzai.hevolve.models.appModel.HeaderViewModelStories;
import com.hertzai.hevolve.models.appModel.StoriesItemModel;

import java.util.ArrayList;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder>{
    private int Selectedposition=0;
    private ArrayList<HeaderViewModelStories>  mHeaderList;
    private RecyclerView headerRV;

      private  HeaderStoriesAdapter mAdapter;
    private Context context;
    private ArrayList<StoriesItemModel> mList;
    private StoriesInterface mListener;

    public StoriesAdapter(Context context, ArrayList<StoriesItemModel> mList,  StoriesInterface mListener) {
        this.context = context;
        this.mList = mList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public StoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageView.setImageResource(mList.get(position).getTeachers());
        holder.textView.setText(mList.get(position).getBytes());

       holder. textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.storiesItemCLicked(position);

            }
        });

    }

    public interface StoriesInterface{
        void storiesItemCLicked(int position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
            imageView = itemView.findViewById(R.id.img_item);


        }
    }
}


