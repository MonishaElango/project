package com.hertzai.hevolve.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hertzai.hevolve.R;
import com.hertzai.hevolve.constants.HevolveConstants;
import com.hertzai.hevolve.helpers.FormValidator;
import com.hertzai.hevolve.helpers.HevolveLog;
import com.hertzai.hevolve.viewModel.LoginSignUpViewModel;
import com.hertzai.hevolve.views.base.HevolveAppCompatActivity;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends HevolveAppCompatActivity {

    @BindView(R.id.signup_first_name_et)
    EditText mFirstNameET;
    @BindView(R.id.client_name_et)
    EditText mClientNameET;
    @BindView(R.id.who_pays_et)
    EditText mPayET;
    @BindView(R.id.phone_et)
    EditText mPhoneET;
    @BindView(R.id.age_et)
    EditText mAgeET;
    @BindView(R.id.batch_name_et)
    EditText mBatchNameET;
    @BindView(R.id.school_name_et)
    EditText mSchoolNameET;
    @BindView(R.id.signup_email_et)
    EditText mSignUpEmailET;
    @BindView(R.id.rdGroup)
    RadioGroup mGenderGrp;

    @BindView(R.id.sign_in_tv)
    TextView mSignInTV;
    @BindView(R.id.create_new_account_btn)
    Button mCreateAccountBtn;
    private String selecteditem;


    String[] language = { "Tamil","English","Hindi" };
    String[] english_prof = { "Novice","Intermediate","Advanced" };


    private LoginSignUpViewModel mViewModel;



    public static final int PASSWORD_MIN_LENGTH = 8;

    protected String errorMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
       mViewModel = new ViewModelProvider(this).get(LoginSignUpViewModel.class);
       setUpLoader(mViewModel);

        String[] lanSpinner = new String[] {
                "Tamil","English","Hindi"
        };
        Spinner s = (Spinner) findViewById(R.id.profSpinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lanSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng)
            {
                if (adapter.getItemAtPosition(i).equals("Select Your Language")){

                }else {
                    String item = adapter.getItemAtPosition(i).toString();
                }
                // do something upon option selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });

        String[] profSpinner = new String[] {
                "Novice","Intermediate","Advanced"
        };
        Spinner s1 = (Spinner) findViewById(R.id.language_Spinner);


        ArrayAdapter<String> profadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, profSpinner);
        profadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(profadapter);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng)
            {
                if (adapter.getItemAtPosition(i).equals("Select Your Level")){

                }else {
                    String item = adapter.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdGroup);
        radioGroup.setOnClickListener(v -> {
            // get selected radio button from radioGroup
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            View radioButton = findViewById(selectedId);

        });
        initViews();
//        initLiveData();
    }

    //@Override
    public void initObservers() {
        mViewModel.getSignUpLiveData().observe(this, user ->{
            hideProgress();
            HevolveLog.d("userData", user.toString());
            //Save LoggedIn user data to shared preference
           getSharedPrefManager().setPreference(HevolveConstants.STUDENT_ID, user.getStudent_id());

            //Navigate to MainActivity
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
          //  createAnalytics(AnalyticsConstants.signUpEvent, user.getUserName(), null, null);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.fade_out);
            finish();

        });

        mViewModel.getErrorData().observe(this,error->{
            showSnackbar(error);
        });

    }

    private void initViews(){


        String haveAnAccount = "Already have an account? Sign in.";
        SpannableString ha = new SpannableString(haveAnAccount);
         ClickableSpan clickableSigninSpan = new ClickableSpan() {
             @Override
             public void onClick(@NonNull View widget) {
                 Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                 startActivity(intent);
                 overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                 finish();
             }
             @Override
             public void updateDrawState(@NonNull TextPaint ds) {
                 super.updateDrawState(ds);
                 ds.setUnderlineText(false);
             }
         };

         ha.setSpan(clickableSigninSpan,25,32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignInTV.setText(ha);
        mSignInTV.setMovementMethod(LinkMovementMethod.getInstance());


    }


//    private void initLiveData(){
//
//
//        mViewModel.getErrorModelLiveData().observe(this,errorModel -> {
//            showSnackbar(errorModel.getMessage());
//        });
//
//    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.create_new_account_btn)
    void onCreateAccountClicked(){
        hideKeyboard();
        if(validateFormData()){
            signUp();
        }
        else
            showSnackbar(errorMsg);
    }




//    @OnClick(R.id.sign_in_tv)
//    void onSignInClicked(){
//        Intent intent = new Intent(this,LoginActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
//        finish();
//    }


    private  boolean validateFormData(){
        boolean formOk = true;

        String firstname = mFirstNameET.getText().toString();
      //  String lastname = mLastNameET.getText().toString();
        int checkedRadioButtonId = mGenderGrp.getCheckedRadioButtonId();

        String email = mSignUpEmailET.getText().toString().trim();

        String phone = mPhoneET.getText().toString().trim();
//        String confirmPassword = mConfirmPasswordET.getText().toString().trim();
        String batchname = mBatchNameET.getText().toString().trim();
        String schoolname = mSchoolNameET.getText().toString().trim();
        String whopays = mPayET.getText().toString().trim();
        String clientname = mClientNameET.getText().toString().trim();

        errorMsg = "";
        if (!FormValidator.requiredField(firstname, 1)) {
            formOk = false;
            errorMsg = "Please enter YourName!";
        }
       // if (!FormValidator.requiredField(lastname, 1)) {
        //    formOk = false;
         //   errorMsg = "Please enter LastName!";
      //  }
        //
        else if (!FormValidator.validateEmail(email)) {
            formOk = false;
            errorMsg = "Please enter valid email!";
        }
        else if (!FormValidator.requiredClientName(clientname, 5)) {
            formOk = false;
            errorMsg = "Please enter valid clientname!";
        }


        else if (!FormValidator.requiredFieldPhone(phone , 10)) {
            formOk = false;
            errorMsg = "Please enter valid Phone!";
        }
        else if (!FormValidator.requiredFieldBatch(batchname , 4)) {
            formOk = false;
            errorMsg = "Please enter valid batch!";
        }
        else if (!FormValidator.requiredFieldSchool(schoolname , 5)) {
            formOk = false;
            errorMsg = "Please enter valid School!";
        }
        else if (!FormValidator.requiredFieldWhoPays(whopays , 5)) {
            formOk = false;
            errorMsg = "Please enter valid Who Pays!";
        }else if(checkedRadioButtonId == -1){
            formOk = false;
            errorMsg = "Please select the gender";
        }

        return formOk;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void signUp(){
        showProgress();

          JSONObject jsonObject = new JSONObject();
        JSONObject userObject = new JSONObject();

        Spinner spin = (Spinner) findViewById(R.id.profSpinner);
        Spinner spin1 = (Spinner) findViewById(R.id.language_Spinner);
        RadioGroup gender1 = (RadioGroup) findViewById(R.id.rdGroup);

        int checkedRadioButtonId = mGenderGrp.getCheckedRadioButtonId();


        String name = mFirstNameET.getText().toString().trim();
        String email_address = mSignUpEmailET.getText().toString();
        String age = mAgeET.getText().toString();
//      String gender = mGenderGrp.getTransitionName().trim();
        String english_proficiency = spin.getSelectedItem().toString();
        String preferred_language = spin1.getSelectedItem().toString();
        String phone_number = mPhoneET.getText().toString().trim();
        String batch_name = mBatchNameET.getText().toString().trim();
        String school_name = mSchoolNameET.getText().toString().trim();
        String client_name = mClientNameET.getText().toString().trim();
        String who_pays_for_course = mPayET.getText().toString().trim();

        try {

           userObject.put("name",name);
            userObject.put("email_address",email_address);
            userObject.put("phone_number",phone_number);
            userObject.put("age",age);
         //   userObject.put(gender,gender);
            userObject.put("who_pays_for_course",who_pays_for_course);

            if (checkedRadioButtonId == R.id.rdbMale){
                userObject.put("gender","male");
            }else if(checkedRadioButtonId == R.id.rdbFemale){
                userObject.put("gender","female");
            }
            userObject.put("preferred_language",preferred_language);
            userObject.put("school_name",school_name);
            userObject.put("client_name",client_name);
            userObject.put("batch_name",batch_name);
            userObject.put("english_proficiency",english_proficiency);




            JsonParser jsonParser = new JsonParser();
            JsonObject gsonObject = (JsonObject) jsonParser.parse(userObject.toString());


            mViewModel.signUp(gsonObject);
  }
        catch (JSONException e){
           e.printStackTrace();
        }

    }

    public void onRadioButtonClicked(View view) {
    }
}
