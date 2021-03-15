package com.hertzai.hevolve.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hertzai.hevolve.R;
import com.hertzai.hevolve.helpers.FormValidator;
import com.hertzai.hevolve.helpers.HevolveLog;
import com.hertzai.hevolve.viewModel.LoginSignUpViewModel;
import com.hertzai.hevolve.views.base.HevolveAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends HevolveAppCompatActivity {
    @BindView(R.id.email_et)
    EditText mEmailET;

    @BindView(R.id.phone_login_et)
    EditText mPhoneET;
    @BindView(R.id.name_et)
    EditText mNameET;
    @BindView(R.id.sign_up_tv)
    TextView mSignUpTV;
    public static final String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";

    public static final String SHOW_LOADER_FROM = "SHOW_LOADER_FROM";
    private String errorMsg;
    private LoginSignUpViewModel mViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mViewModel = new ViewModelProvider(this).get(LoginSignUpViewModel.class);
        setUpLoader(mViewModel);

//        initLiveData();
        initViews();

    }

    // @Override
    public void initObservers() {
        //Get Success response of SignIn call
        mViewModel.getSignInLiveData().observe(this, user -> {
            HevolveLog.d("userData", user.toString());
            //Save LoggedIn user data to shared preference
         //   getSharedPrefManager().setPreference(HevolveConstants.STUDENT_ID, true);


//            FirebaseManager.patchToken(this);

            //Navigate to MainActivity
            if(user){
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.fade_out);
                finish();
            }else{
                showSnackbar("User not found");
            }


        });

    }

    private void initViews() {
        String haveAnAccount = "Don't have an account?  Sign Up now. ";
        SpannableString ha = new SpannableString(haveAnAccount);
        ClickableSpan clickableSigninSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
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

        ha.setSpan(clickableSigninSpan, 24, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignUpTV.setText(ha);
        mSignUpTV.setMovementMethod(LinkMovementMethod.getInstance());

    }


    @OnClick(R.id.login_btn)
    void onLoginClicked() {
        hideKeyboard();
        if (validateFormData()) {
            login();
        } else {
            showSnackbar(errorMsg);
        }
    }


    //Check form data
    private boolean validateFormData() {
        boolean formOk = true;
        String email = mEmailET.getText().toString().trim();

        String phone = mPhoneET.getText().toString().trim();

        errorMsg = "";
        if (!FormValidator.validateEmail(email)) {
            formOk = false;
            errorMsg = getString(R.string.invalid_email);

        } else if (!FormValidator.requiredFieldPhone(phone, 10)) {
            formOk = false;
            errorMsg = "Please enter valid phone!";
        }
            return formOk;
        }


    //Make login api call
    private void login() {
        Toast.makeText(LoginActivity.this, "Successfully loggedIN!", Toast.LENGTH_SHORT).show();


        //showProgress();
        JSONObject jsonObject = new JSONObject();
        JSONObject userObject = new JSONObject();

        String email = mEmailET.getText().toString().trim();
        String phone = mPhoneET.getText().toString().trim();


        try {
            userObject.put("email_address", email);

            userObject.put("phone_number", phone);


            JsonParser jsonParser = new JsonParser();
            JsonObject gsonObject = (JsonObject) jsonParser.parse(userObject.toString());
            mViewModel.signIn(gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}





