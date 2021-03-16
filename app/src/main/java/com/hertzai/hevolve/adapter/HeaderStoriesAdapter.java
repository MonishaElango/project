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
import com.hertzai.hevolve.views.StoriesActivity;

import java.util.ArrayList;

public class HeaderStoriesAdapter extends RecyclerView.Adapter<HeaderStoriesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HeaderViewModelStories> headerList ;
    private ArrayList<StoriesItemModel> storyList;


    public HeaderStoriesAdapter(Context context, ArrayList<HeaderViewModelStories> headerList) {
        this.context = context;
        this.headerList = headerList;

    }

    @NonNull
    @Override
    public HeaderStoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_stories_header,parent,false);
        return new HeaderStoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderStoriesAdapter.ViewHolder holder, int position) {
                   holder.textViewHeaderText1.setText("Did you Know");
                   holder.textViewHeaderText2.setText("AI learning");
                   holder.imageViewHeader.setImageResource(R.drawable.santa_bg);


       // LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
         // holder.storiesRV.setLayoutManager(layoutManager);
         // holder.storiesRV.setAdapter(holder.mAdapter);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHeaderText1,textViewHeaderText2;
        ImageView imageViewHeader;
        RecyclerView storiesRV;
        HeaderStoriesAdapter mHeaderAdapter;
        StoriesAdapter mAdapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                  storiesRV = itemView.findViewById(R.id.imageVideoRecyclerview);
                textViewHeaderText1 = itemView.findViewById(R.id.text_bytes_2);
                textViewHeaderText2 = itemView.findViewById(R.id.text_bytes_3);
                imageViewHeader = itemView.findViewById((R.id.image_bytes));
            }
        }
    public interface HeaderInterface{
        void onStoriesClicked(int position);
    }
    }

