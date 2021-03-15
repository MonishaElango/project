package com.hertzai.hevolve.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;

import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hertzai.hevolve.R;
import com.hertzai.hevolve.adapter.AdapterCardVideoSelection;
import com.hertzai.hevolve.models.appModel.MainModel;


import java.util.ArrayList;

import butterknife.ButterKnife;

import static com.google.android.exoplayer2.MediaItem.fromUri;

public class HomeActivity extends AppCompatActivity {


    private DefaultHttpDataSourceFactory factory;
    private DataSource.Factory dataSourceFactory ;
    private String userAgent;
    private Button animLoti;
    LottieAnimationView anim;
    private PlaybackStateListener playbackStateListener;
    private static final String TAG = HomeActivity.class.getName();
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private BottomNavigationView bottomNavigationView;

    private RecyclerView recylerViewImage;
    ArrayList<MainModel> mainModels;
    AdapterCardVideoSelection mainImageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mPlayerView = findViewById(R.id.video_view);
        playbackStateListener = new PlaybackStateListener();

        animLoti = (Button) findViewById(R.id.select_tv);
        anim = (LottieAnimationView) findViewById(R.id.animation_view);
        anim.setAnimation(R.raw.confetti);


        animLoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim.setVisibility(View.VISIBLE);
                
                anim.playAnimation();
            }
           });

        ButterKnife.bind(this);

        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.book:
                                // TODO
                                Intent i = new Intent(HomeActivity.this, GridViewActivity.class);
                                startActivity(i);
                                return true;

                        }
                        return false;
                    }


                });
        // playbackStateListener = new PlaybackStateListener();

        recylerViewImage = findViewById(R.id.main_card_recyclerView);
        Integer[] teacherImg = {R.drawable.santa_bg,R.drawable.newton_bg,R.drawable.panda_bg,R.drawable.abdul_kalam_bg,R.drawable.chemistry_bg,R.drawable.marie_curie_bg,R.drawable.shiva_bg};
        String[] title  = {"One","Two","Three","Four","Five","Six","Seven"};
        mainModels = new ArrayList<>();

        mainModels.add(new MainModel(R.drawable.santa_bg, "One"));
        mainModels.add(new MainModel(R.drawable.newton_bg, "Two"));
        mainModels.add(new MainModel(R.drawable.panda_bg, "Three"));
        mainModels.add(new MainModel(R.drawable.abdul_kalam_bg, "Four"));
        mainModels.add(new MainModel(R.drawable.chemistry_bg, "Five"));
        mainModels.add(new MainModel(R.drawable.marie_curie_bg, "Six"));
        mainModels.add(new MainModel(R.drawable.shiva_bg, "Seven"));


        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recylerViewImage.setLayoutManager(layoutManager);
        recylerViewImage.setItemAnimator(new DefaultItemAnimator());
        mainImageAdapter = new AdapterCardVideoSelection(HomeActivity.this, mainModels, new AdapterCardVideoSelection.RecyclerViewClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(HomeActivity.this,mainModels.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
                recylerViewImage.setAdapter(mainImageAdapter);
        if (mPlayer == null) {
            initializePlayer();
        }

    }






    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();

    }
    @Override
    public void onStop() {
        super.onStop();

            releasePlayer();

    }

    private void initializePlayer() {
        if (mPlayer == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
            trackSelector.setParameters(
                    trackSelector.buildUponParameters().setMaxVideoSizeSd());
            mPlayer = new SimpleExoPlayer.Builder(this)
                    .setTrackSelector(trackSelector)
                    .build();
        }

        mPlayerView.setPlayer(mPlayer);
        Uri playerUri = Uri.parse("http://stream.mcgroce.com/examples/video/ron_pred_fls_M6_04_16k_audio_embed.m3u8");


        mPlayer.setPlayWhenReady(playWhenReady);
        userAgent = Util.getUserAgent(this,getString(R.string.app_name));
       factory = new DefaultHttpDataSourceFactory(userAgent,null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,true);

        dataSourceFactory = new DefaultDataSourceFactory(this,null,factory);

       // HlsMediaSource hlsSrc = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(playerUri);

      //  ConcatenatingMediaSource concatenatedSource = new ConcatenatingMediaSource(hlsSrc);
        MediaSource mediaSource = buildMediaSource(playerUri,null);

        mPlayer.prepare(mediaSource);

     //   mPlayer.seekTo(currentWindow, playbackPosition);
       // mPlayer.addListener(playbackStateListener);
       // mPlayer.prepare(hlsSrc);
       // mPlayer.prepare(concatenatedSource);

    }

    private MediaSource buildMediaSource(Uri uri, @Nullable String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);

            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }
    private void releasePlayer() {
        if (mPlayer != null) {
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.removeListener(playbackStateListener);
            mPlayer.release();
            mPlayer = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    class PlaybackStateListener implements Player.EventListener{

        @Override
        public void onPlaybackStateChanged(int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d(TAG, "changed state to " + stateString);
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();

       // mPlayer.getPlaybackState();
    }


}
