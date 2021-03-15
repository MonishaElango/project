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
    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;
    import com.google.gson.JsonObject;
    import com.google.gson.JsonParser;
    import com.hertzai.hevolve.R;
    import com.hertzai.hevolve.adapter.AssessChatRecyclerAdapter;
    import com.hertzai.hevolve.api.AssessChatApi;
    import com.hertzai.hevolve.gson.Assess_ResponseMessage;
    import com.hertzai.hevolve.gson.TokenResponseAssessment;
    import com.hertzai.hevolve.models.appModel.QuestionNo;
    import com.hertzai.hevolve.models.appModel.Revision_No;
    import com.squareup.okhttp.Credentials;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.concurrent.TimeUnit;

    import okhttp3.Interceptor;
    import okhttp3.OkHttpClient;
    import okhttp3.Request;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class AssessmentChatActivity extends HeaderActivity implements HeaderActivity.onHeaderClickInterface, RecognitionListener {
        private ImageView sendButton;
        private ImageView micButton;
        private TextView startText;
        private RecyclerView mChatRecyclerView;
        private View headerLL;
        private EditText chatET;
        private TextView optionTV;
        private ArrayList<QuestionNo> messageList;
        private ArrayList<Revision_No> messageList1;

        private AssessChatRecyclerAdapter mAdapter;
        private LinearLayoutManager LinearLayoutManager;
        private String assessment = "false";
        private androidx.appcompat.widget.Toolbar myToolbar1;
        private Integer questionNumber = 0;
        private String subject = "";
        private String student_id = "";
        private Integer assessmentId = 0;
        private String qlist = "";

        private Boolean isMicOn = true;
        private RecognitionProgressView recognitionProgressView;
        private View background;
        private SpeechRecognizer speech = null;
        private Intent recognizerIntent;
        private SpeechRecognizer speechRecognizer;
        private String LOG_TAG = "VoiceRecognitionActivity";
        private ArrayList<String> optionList = new ArrayList<>();
        private boolean mPermissionToRecord;
        private AudioEmitter mAudioEmitter;

        private String clientId = "NO2gEez2FYTbKXe6iUgI0GyKQVMXQxZt";
        private String clientSecret = "nzO6EVKPAdaWiwTrEwU5lMaVwccUKdoW";
        private String myToken = "";
        private String tokenType = "";
        private int expires_in;
        private String TokenName ="";

        private String video_link = "";
        private AskMeChatActivity.PlaybackStateListener playbackStateListener;
        private static final String TAG = RevisionChatActivity.class.getName();
        private PlayerView mPlayerViewAssess;
        private SimpleExoPlayer mPlayerAssess;
        private boolean playWhenReadyAssess = true;
        private int currentWindowAssess = 0;
        private long playbackPositionAssess= 0;
        private String userAgent;
        private DefaultHttpDataSourceFactory factory;
        private DataSource.Factory dataSourceFactory ;

        private ImageView playerImageView;



        private static final String FRAGMENT_MESSAGE_DIALOG = "message_dialog";

        private static final String STATE_RESULTS = "results";


        private SpeechService mSpeechService;
        // Resource caches
        private int mColorHearing;
        private int mColorNotHearing;

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


            setContentView(R.layout.activity_assess_chat);

            messageList = new ArrayList<>();
            sendButton = findViewById(R.id.sendIcon);
            startText = findViewById(R.id.startTV);
            headerLL = findViewById(R.id.headerLL);
            mChatRecyclerView = findViewById(R.id.chatRV);
            chatET = findViewById(R.id.chat_et);
            optionTV=findViewById(R.id.option_tv);
            micButton = findViewById(R.id.micBtn);
    //        myToolbar1 =  findViewById(R.id.my_toolbar1);
    //        myToolbar1.setTitle("Assess My Skill");
    //        setSupportActionBar(myToolbar1);
            recognitionProgressView = findViewById(R.id.recognition_view1);
            mPlayerViewAssess = findViewById(R.id.video_view_assessment);
            playerImageView = findViewById(R.id.videoImgView_assess);


            initiateSpeech();
            initViews();
            startVoiceRecorder();


            int[] colors = {
                    ContextCompat.getColor(AssessmentChatActivity.this, R.color.color1),
                    ContextCompat.getColor(AssessmentChatActivity.this, R.color.color2),
                    ContextCompat.getColor(AssessmentChatActivity.this, R.color.color3),
                    ContextCompat.getColor(AssessmentChatActivity.this, R.color.color4),
                    ContextCompat.getColor(AssessmentChatActivity.this, R.color.color5)
            };


            overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
            background = findViewById(R.id.background1);
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




            recognitionProgressView.setSpeechRecognizer(speech);
            recognitionProgressView.setColors(colors);
            recognitionProgressView.setCircleRadiusInDp(2);
            recognitionProgressView.setSpacingInDp(2);
            recognitionProgressView.setIdleStateAmplitudeInDp(2);
            recognitionProgressView.setRotationRadiusInDp(10);
            recognitionProgressView.play();
            recognitionProgressView.setRecognitionListener(this);

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




        private void initializePlayer(String url) {
            if (mPlayerAssess == null) {
                DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
                trackSelector.setParameters(
                        trackSelector.buildUponParameters().setMaxVideoSizeSd());
                mPlayerAssess= new SimpleExoPlayer.Builder(this)
                        .setTrackSelector(trackSelector)
                        .build();
            }
              mPlayerViewAssess.setVisibility(View.VISIBLE);
               playerImageView.setVisibility(View.GONE);
            mPlayerViewAssess.setPlayer(mPlayerAssess);
            Uri playerUri = Uri.parse(url);
            mPlayerAssess.setPlayWhenReady(playWhenReadyAssess);
            userAgent = Util.getUserAgent(this, getString(R.string.app_name));
            factory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            dataSourceFactory = new DefaultDataSourceFactory(this, null, factory);
            MediaSource mediaSource = buildMediaSource(playerUri, null);

            mPlayerAssess.prepare(mediaSource);

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


        private void releasePlayer() {
            if (mPlayerAssess != null) {
                playbackPositionAssess = mPlayerAssess.getCurrentPosition();
                currentWindowAssess = mPlayerAssess.getCurrentWindowIndex();
                playWhenReadyAssess= mPlayerAssess.getPlayWhenReady();
                mPlayerAssess.removeListener(playbackStateListener);
                mPlayerAssess.release();
                mPlayerAssess= null;
            }
        }


        final SpeechService.Listener mSpeechServiceListener = new SpeechService.Listener() {
            @Override
            public void onSpeechRecognized(final String text, final boolean isFinal) {
                if (isFinal) {
                    mVoiceRecorder.dismiss();
                }
                if (!TextUtils.isEmpty(text)) {
                    runOnUiThread(new Runnable() {
              public final void run() {
                  if (!isMicOn) {
                      if (isFinal) {
                      ArrayList<String> matches = new ArrayList<>();
                      matches.add(text);
                      if (matches != null && matches.size() > 0) {
                          messageList.add(new QuestionNo("", matches, optionList, false,video_link));

                          mAdapter.notifyDataSetChanged();
                          startText.setVisibility(View.GONE);
                          mChatRecyclerView.setVisibility(View.VISIBLE);
                          questionNumber += 1;
                          mChatRecyclerView.scrollToPosition(messageList.size() - 1);
                          JSONObject obj = new JSONObject();
                          JSONArray array = new JSONArray();

                          try {


                              array.put(matches);
                              obj.put("text", array);
                              /* obj.put("user_id" , username);*/
                              obj.put("assessment" , assessment);
                              obj.put("subject",subject);
                              obj.put("student_id","2");
                              obj.put("assessment_id", assessmentId.toString());
                              obj.put("qlist",qlist);
                              obj.put("question_no" , questionNumber.toString());

                          } catch (JSONException e) {
                              e.printStackTrace();
                          }

                          JsonParser parser = new JsonParser();
                          JsonObject mainObj = new JsonObject();

                          mainObj = (JsonObject) parser.parse(obj.toString());

                          sendRequest(mainObj);


                      } else {
                          Toast.makeText(AssessmentChatActivity.this, "Couldn't recognize your voice", Toast.LENGTH_SHORT).show();
                      }
                      String text2 = "";
                      for (String results : matches)
                          text2 += results + "\n";

                      chatET.setText(text2);
                      }
                  }
                  else{
                      Toast.makeText(AssessmentChatActivity.this, "Mic off", Toast.LENGTH_SHORT).show();
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
        }



        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private void circularRevealActivity () {
                int cx = background.getRight() - getDips(60);
                int cy = background.getBottom() - getDips(500);

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

            private int getDips ( int dps){
                Resources resources = getResources();
                return (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dps,
                        resources.getDisplayMetrics());
            }

            @Override
            public void onBackPressed () {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = background.getWidth() - getDips(44);
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

        private void initViews(){

          //  updateHeader(headerLL , true,"Assess My Skill",this);
            mAdapter = new AssessChatRecyclerAdapter(this, messageList, new AssessChatRecyclerAdapter.ChatInterface() {
                @Override
                public void onOptionClicked(String option) {

                    updateText(option);
                }
            });
            LinearLayoutManager = new LinearLayoutManager(this);
            mChatRecyclerView.setLayoutManager(LinearLayoutManager);
            mChatRecyclerView.setAdapter(mAdapter);

            sendButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    updateText(chatET.getText().toString());
                }
            });
        }


        void updateText(String message){
            ArrayList<String> qlistString = new ArrayList<>();

            ArrayList<String> messageStringList = new ArrayList<>();
            messageStringList.clear();
            messageStringList.add(message);
            if(message.length() > 0){
                chatET.setText("");
                messageList.add(new QuestionNo("",messageStringList ,optionList, false,video_link));
            //    mAdapter.notifyDataSetChanged();
                startText.setVisibility(View.GONE);
                mChatRecyclerView.setVisibility(View.VISIBLE);

                mChatRecyclerView.scrollToPosition(messageList.size()-1);
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();


                try{

                    array.put(message);
                    obj.put("text", array);
                    /* obj.put("user_id" , username);*/
                    obj.put("assessment" , assessment);
                    obj.put("subject",subject);
                    obj.put("student_id","2");
                    obj.put("assessment_id", assessmentId.toString());
                    obj.put("qlist",qlist);
                    obj.put("question_no" , questionNumber.toString());

                }catch ( JSONException e){
                    e.printStackTrace();
                }

                JsonParser parser = new JsonParser();
                JsonObject mainObj = new JsonObject();

                mainObj = (JsonObject)  parser.parse(obj.toString());
                JsonObject myToken = new JsonObject();

                myToken = (JsonObject)  parser.parse(obj.toString());

                sendRequest(mainObj);



            }else{
                Toast.makeText(AssessmentChatActivity.this,"Please enter some text" , Toast.LENGTH_SHORT).show();
            }
        }




        private void sendRequest(JsonObject message){


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://assess.mcgroce.com")
                   .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AssessChatApi service = retrofit.create(AssessChatApi.class);

            Call<Assess_ResponseMessage> stringCall = service.getReply(message);
            stringCall.enqueue(new Callback<Assess_ResponseMessage>() {
                @Override
                public void onResponse(Call<Assess_ResponseMessage> call, Response<Assess_ResponseMessage> response){
                        if (!response.toString().equals("") || response.isSuccessful()) {
                            Assess_ResponseMessage jsonResponse = response.body();

                            if(jsonResponse != null ){
                                System.out.println(jsonResponse);
                                assessment  = jsonResponse.getAssessment();
                                student_id = jsonResponse.getStudent_id();
                                assessmentId = jsonResponse.getAssessment_id();
                                qlist = jsonResponse.getQlist();
                                subject =jsonResponse.getSubject();

                                questionNumber = Integer.parseInt(jsonResponse.getQuestionNo().toString());
                                messageList.add(new QuestionNo(questionNumber.toString(),jsonResponse.getAnswerList(),jsonResponse.getOptionLists(), true,jsonResponse.getVideo_link()));

                                if(jsonResponse.getOptionLists()!=null) {
                                    if(assessment.equals("True")) {
                                        optionList.addAll(jsonResponse.getOptionLists());
                                    }
                                    else{
                                        optionList.clear();
                                    }
                                }
                                if(jsonResponse.getVideo_link()!=null)
                                {
                                    video_link = jsonResponse.getVideo_link();
                                    initializePlayer(video_link);

                                }

                                mChatRecyclerView.scrollToPosition(messageList.size() - 1);
                                mAdapter.notifyDataSetChanged();

                            }else{
                                Toast.makeText(AssessmentChatActivity.this,"Not getting any response",Toast.LENGTH_LONG).show();

                            }
                        }


                }

                @Override
                public void onFailure(Call<Assess_ResponseMessage> call, Throwable t) {
                    Toast.makeText(AssessmentChatActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
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
    }