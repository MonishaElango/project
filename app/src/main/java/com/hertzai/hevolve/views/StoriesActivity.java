package com.hertzai.hevolve.views;

import android.os.Bundle;

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
        mHeaderRV = findViewById(R.id.storiesRV);
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
           mStoriesAdapter =new StoriesAdapter(this, storyList, new StoriesAdapter.StoriesInterface() {

            @Override
            public void storiesItemCLicked(int position) {



                       switch (position) {
                           case 0: {

                               headerList.add(new HeaderViewModelStories(R.drawable.santa_bg, "", "Did You Know"));
                               break;
                           }
                           case 1: {
                               headerList.add(new HeaderViewModelStories(R.drawable.newton_bg, "", "Did You Know"));
                               break;
                           }
                           case 2: {

                               headerList.add(new HeaderViewModelStories(R.drawable.panda_bg, "", "Did You Know"));
                               break;
                           }
                           case 3: {

                               headerList.add(new HeaderViewModelStories(R.drawable.abdul_kalam_bg, "", "Did You Know"));
                               break;
                           }
                           case 4: {

                               headerList.add(new HeaderViewModelStories(R.drawable.chemistry_bg, "", "Did You Know"));
                               break;
                           }
                           case 5: {

                               headerList.add(new HeaderViewModelStories(R.drawable.marie_curie_bg, "", "Did You Know"));
                               break;
                           }
                           case 6: {

                               headerList.add(new HeaderViewModelStories(R.drawable.shiva_bg, "", "Did You Know"));
                               break;
                           }
                           default:
                               headerList.add(new HeaderViewModelStories(R.drawable.shiva_bg, "", "Did You Know"));

                       }
            }
           });

           mStoriesRV.setAdapter(mStoriesAdapter);
           mStoriesAdapter.notifyDataSetChanged();
    }

}