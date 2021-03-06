package com.hertzai.hevolve.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hertzai.hevolve.R;
import com.hertzai.hevolve.adapter.HeaderStoriesAdapter;
import com.hertzai.hevolve.adapter.StoriesAdapter;
import com.hertzai.hevolve.models.appModel.HeaderViewModelStories;
import com.hertzai.hevolve.models.appModel.StoriesItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoriesActivity extends AppCompatActivity {
    @BindView(R.id.imageVideoRecyclerview)
    RecyclerView mStoriesRV;
    RecyclerView mHeaderRV;
    @BindView(R.id.image_bytes)
    ImageView contentIV;
    @BindView(R.id.text_bytes_3)
    TextView contentTV;

    private StoriesAdapter mStoriesAdapter;
    private HeaderStoriesAdapter mHeaderAdapter;
    private ArrayList<StoriesItemModel> storyList;
    private ArrayList<HeaderViewModelStories> headerList;
    private int Selectedposition=1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        ButterKnife.bind(this);
        mStoriesRV = findViewById(R.id.imageVideoRecyclerview);
        setUpStories();
        //setUpHeader();

    }




    private void setUpStories() {

        storyList = new ArrayList<>();
        headerList = new ArrayList<>();

        storyList.add(new StoriesItemModel(R.drawable.santa_bg, "Earn Bytes from Santa"));
        storyList.add(new StoriesItemModel(R.drawable.newton_bg, "Earn Bytes from Newton"));
        storyList.add(new StoriesItemModel(R.drawable.panda_bg, "Earn Bytes from Panda"));
        storyList.add(new StoriesItemModel(R.drawable.abdul_kalam_bg, "Earn Bytes from APJ Abdul Kalam"));
        storyList.add(new StoriesItemModel(R.drawable.chemistry_bg, "Earn Bytes from Chemistry Cook"));
        storyList.add(new StoriesItemModel(R.drawable.marie_curie_bg, "Earn Bytes from Marie Curie"));
        storyList.add(new StoriesItemModel(R.drawable.shiva_bg, "Earn Bytes from Shiva"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(StoriesActivity.this, LinearLayoutManager.VERTICAL, false);
        mStoriesRV.setLayoutManager(layoutManager);
        mStoriesRV.setItemAnimator(new DefaultItemAnimator());
        mStoriesAdapter =new StoriesAdapter(this, storyList, position -> {
            StoriesItemModel item = storyList.get(position);
            contentIV.setImageResource(item.getTeachers());
            contentTV.setText(item.getBytes());

        });

           mStoriesRV.setAdapter(mStoriesAdapter);
           mStoriesAdapter.notifyDataSetChanged();
    }

}