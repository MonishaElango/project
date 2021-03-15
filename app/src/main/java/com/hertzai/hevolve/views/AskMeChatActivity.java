package com.hertzai.hevolve.views;



import android.animation.Animator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.zagum.speechrecognitionview.RecognitionProgressView;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hertzai.hevolve.R;
import com.hertzai.hevolve.adapter.AskMeChatRecyclerAdapter;
import com.hertzai.hevolve.gson.AskMessageItem;
import com.hertzai.hevolve.helpers.HevolveLog;
import com.hertzai.hevolve.viewModel.ChatBotViewModel;
import com.hertzai.hevolve.views.base.HevolveAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AskMeChatActivity extends HevolveAppCompatActivity implements HeaderActivity.onHeaderClickInterface, RecognitionListener, MessageDialogFragment.Listener {
    private ImageView sendButton;
    private RecyclerView mChatRecyclerView;
    private EditText chatET;
    private TextView chatTV;
    private View headerLL;
    private TextView startTV;
    private String historyList ;


    private ArrayList<AskMessageItem> messageList1;
    private AskMeChatRecyclerAdapter mAdapter;

    private LinearLayoutManager LinearLayoutManager;
    private RecognitionProgressView recognitionProgressView;
    private View background;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private ImageView micButton;
    private Boolean isMicOn = true;

    private String video_link = "";

    private ImageView playerImageView;


    private AskMeChatActivity.PlaybackStateListener playbackStateListener;
    private static final String TAG = HomeActivity.class.getName();
    private PlayerView mPlayerViewAskMe;
    private SimpleExoPlayer mPlayerAskMe;
    private boolean playWhenReadyAskMe = true;
    private int currentWindowAskMe = 0;
    private long playbackPositionAskMe = 0;
    private String userAgent;
    private DefaultHttpDataSourceFactory factory;
    private DataSource.Factory dataSourceFactory ;


    private String LOG_TAG = "VoiceRecognitionActivity";

    private String clientId = "NO2gEez2FYTbKXe6iUgI0GyKQVMXQxZt";
    private String clientSecret = "nzO6EVKPAdaWiwTrEwU5lMaVwccUKdoW";
    private String myToken = "";
    private String tokenType = "";
    private int expires_in;
    private String TokenName ="";


    private SpeechService mSpeechService;
    // Resource caches
    private int mColorHearing;
    private int mColorNotHearing;

    private ChatBotViewModel mViewModel;

    private VoiceRecorder mVoiceRecorder;
    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {

        @Override
        public void onVoiceStart() {
            if (mSpeechService != null) {
                mSpeechService.startRecognizing(mVoiceRecorder.getSampleRate());
            }
        }



        @Override
        public void onVoice(byte[] data, int size) {
            if (mSpeechService != null) {
                mSpeechService.recognize(data, size);
            }
        }

        @Override
        public void onVoiceEnd() {

            if (mSpeechService != null) {
                mSpeechService.finishRecognizing();
            }
        }

    };

    private final ServiceConnection mServiceConnection = new ServiceConnection() {



        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            mSpeechService = SpeechService.from(binder);
            mSpeechService.addListener(mSpeechServiceListener);
            chatET.setVisibility(View.VISIBLE);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mSpeechService = null;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askme_chat);

        headerLL = findViewById(R.id.headerLL);
        micButton = findViewById(R.id.micBtn);
        background = findViewById(R.id.background);
        messageList1 = new ArrayList<>();
        sendButton = findViewById(R.id.AskSendIcon);
        recognitionProgressView = findViewById(R.id.recognition_view);
        mChatRecyclerView = findViewById(R.id.AskChatRV);
        startTV = findViewById(R.id.AskStartTV);
        chatET = findViewById(R.id.AskChat_et);
        chatTV = findViewById(R.id.AskMessage_tv);

        playerImageView = findViewById(R.id.videoImgView_askme);


        mPlayerViewAskMe = findViewById(R.id.video_view_askme);

        mViewModel = new ViewModelProvider(this).get(ChatBotViewModel.class);
        setUpLoader(mViewModel);

        final Resources resources = getResources();
        final Resources.Theme theme = getTheme();
        mColorHearing = ResourcesCompat.getColor(resources, R.color.status_hearing, theme);
        mColorNotHearing = ResourcesCompat.getColor(resources, R.color.status_not_hearing, theme);


        initViews();
        initiateSpeech();
        startVoiceRecorder();



        //voicePrint();

        int[] colors = {
                ContextCompat.getColor(AskMeChatActivity.this, R.color.color1),
                ContextCompat.getColor(AskMeChatActivity.this, R.color.color2),
                ContextCompat.getColor(AskMeChatActivity.this, R.color.color3),
                ContextCompat.getColor(AskMeChatActivity.this, R.color.color4),
                ContextCompat.getColor(AskMeChatActivity.this, R.color.color5)
        };

        recognitionProgressView.setSpeechRecognizer(speech);
        recognitionProgressView.setColors(colors);
        recognitionProgressView.setCircleRadiusInDp(2);
        recognitionProgressView.setSpacingInDp(2);
        recognitionProgressView.setIdleStateAmplitudeInDp(2);
        recognitionProgressView.setRotationRadiusInDp(10);
        recognitionProgressView.play();
        recognitionProgressView.setRecognitionListener(this);

        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        background = findViewById(R.id.background);
        if (savedInstanceState == null) {
            background.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                });
            }

        }


        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                if (isMicOn) {
                    isMicOn = false;
                    speech.startListening(recognizerIntent);
                    recognitionProgressView.setVisibility(View.VISIBLE);
                } else {
                    speech.stopListening();
                    recognitionProgressView.setVisibility(View.INVISIBLE);
                    isMicOn = true;
                }
            }
        });
    }

    @Override
    public void initObservers() {
        mViewModel.getSpeakBookLiveData().observe(this,askMeAssis -> {
            hideProgress();
            if(askMeAssis.getVideo_link()!=null)
            {
                video_link = askMeAssis.getVideo_link();
                initializePlayer(video_link);

                messageList1.add(new AskMessageItem(askMeAssis.getText(), true,video_link));
                mAdapter.notifyDataSetChanged();
                mChatRecyclerView.scrollToPosition(messageList1.size() - 1);
            }

        });
    }


    private void initializePlayer(String url) {
        if (mPlayerAskMe == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
            trackSelector.setParameters(
                    trackSelector.buildUponParameters().setMaxVideoSizeSd());
            mPlayerAskMe = new SimpleExoPlayer.Builder(this)
                    .setTrackSelector(trackSelector)
                    .build();
        }


        mPlayerViewAskMe.setVisibility(View.VISIBLE);
        playerImageView.setVisibility(View.GONE);
        mPlayerViewAskMe.setPlayer(mPlayerAskMe);
        Uri playerUri = Uri.parse(url);


        mPlayerAskMe.setPlayWhenReady(playWhenReadyAskMe);
        userAgent = Util.getUserAgent(this, getString(R.string.app_name));
        factory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);

        dataSourceFactory = new DefaultDataSourceFactory(this, null, factory);

        MediaSource mediaSource = buildMediaSource(playerUri, null);

        mPlayerAskMe.prepare(mediaSource);

//        mPlayerAskMe.seekTo(currentWindowAskMe, playbackPositionAskMe);
//        mPlayerAskMe.addListener(playbackStateListener);
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

    final SpeechService.Listener mSpeechServiceListener = new SpeechService.Listener() {
        @Override
        public void onSpeechRecognized(final String text, final boolean isFinal) {
            if (isFinal) {
                mVoiceRecorder.dismiss();
            }

            //// ArrayList<String> matches = new ArrayList<>();

            if (!TextUtils.isEmpty(text)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isMicOn) {
                            if (isFinal) {
                                ArrayList<String> matches = new ArrayList<>();
                                matches.add(text);
                                if (matches != null && matches.size() > 0) {

                                    messageList1.add(new AskMessageItem(matches.get(0), false,video_link));
                                    mAdapter.notifyDataSetChanged();
                                    startTV.setVisibility(View.GONE);
                                    mChatRecyclerView.setVisibility(View.VISIBLE);
                                    mChatRecyclerView.scrollToPosition(messageList1.size() - 1);
                                    JSONObject obj = new JSONObject();

                                    try {
                                        obj.put("user", "user");
                                        //  obj.put("history", historyList);
                                        obj.put("text", messageList1.get(0).message);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    JsonParser parser = new JsonParser();
                                    JsonObject mainObj = (JsonObject) parser.parse(obj.toString());

                                    // sendRequest(mainObj);


                                } else {
                                    Toast.makeText(AskMeChatActivity.this, "Couldn't recognize your voice", Toast.LENGTH_SHORT).show();
                                }

                                String text1 = "";
                                for (String results : matches)
                                    text1 += results + "\n";
                                chatET.setText(text1);
                            }
                        }
                        else{
                            Toast.makeText(AskMeChatActivity.this, "Mic off", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    };





    private void initiateSpeech() {
        if(speech != null)
            speech.destroy();
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        if(SpeechRecognizer.isRecognitionAvailable(this))
            speech.setRecognitionListener(this);
    }

    private void startVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder = new VoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }

    private void stopVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
            mVoiceRecorder = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, SpeechService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        // Stop listening to voice
        stopVoiceRecorder();

        // Stop Cloud Speech API
        mSpeechService.removeListener(mSpeechServiceListener);
        unbindService(mServiceConnection);
        mSpeechService = null;

        super.onStop();
        releasePlayer();

    }

    private void releasePlayer() {
        if (mPlayerAskMe != null) {
            playbackPositionAskMe = mPlayerAskMe.getCurrentPosition();
            currentWindowAskMe = mPlayerAskMe.getCurrentWindowIndex();
            playWhenReadyAskMe = mPlayerAskMe.getPlayWhenReady();
            mPlayerAskMe.removeListener(playbackStateListener);
            mPlayerAskMe.release();
            mPlayerAskMe = null;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity() {
        int cx = 0;
        int cy = 0;
        int right = 0;
        int bottom =0;
        int radius1 = (int) Math.hypot(right, bottom);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                50,
                finalRadius);

        circularReveal.setDuration(500);
        background.setVisibility(View.VISIBLE);
        circularReveal.start();


    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = background.getLeft() - getDips(44);
            int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(500);
            circularReveal.start();
        } else {
            super.onBackPressed();
        }

    }


    private void initViews() {
        // updateHeader(headerLL , true,"Ask An Assitant",this);
        mAdapter = new AskMeChatRecyclerAdapter(this, messageList1);
        LinearLayoutManager = new LinearLayoutManager(this);
        mChatRecyclerView.setLayoutManager(LinearLayoutManager);
        mChatRecyclerView.setAdapter(mAdapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = chatET.getText().toString();
                if (message.length() > 0) {
                    chatET.setText("");
                    mChatRecyclerView.setVisibility(View.VISIBLE);
                    startTV.setVisibility(View.GONE);
                    messageList1.add(new AskMessageItem(message, false,video_link));
                    mAdapter.notifyDataSetChanged();
                    mChatRecyclerView.scrollToPosition(messageList1.size() - 1);
                    JSONObject obj = new JSONObject();

                    try {
                        obj.put("user", "user");
                        obj.put("text", message);

                        HevolveLog.d("CHAT_LOG", obj.toString());

                        JsonParser jsonParser = new JsonParser();
                        JsonObject gsonObject = (JsonObject) jsonParser.parse(obj.toString());
                        showProgress();
                        mViewModel.speakBook(gsonObject);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(AskMeChatActivity.this, "Please enter some text", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    @Override
    public void onBackClicked() {
        onBackPressed();
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }


    @Override
    public void onMessageDialogDismissed() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
