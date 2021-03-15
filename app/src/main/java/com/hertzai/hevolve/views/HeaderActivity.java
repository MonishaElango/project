package com.hertzai.hevolve.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hertzai.hevolve.R;

public class HeaderActivity  extends AppCompatActivity {


    ImageView backIV;

    void updateHeader(View view , Boolean isShowBack, onHeaderClickInterface  Interface){


        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Interface.onBackClicked();
            }
        });

    }

    interface onHeaderClickInterface{
        void onBackClicked();
    }
}