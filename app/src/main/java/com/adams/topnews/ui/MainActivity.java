package com.adams.topnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adams.topnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //member variable
    @BindView(R.id.continueButton)
    Button mContinueButton;
    @BindView(R.id.textView)
    TextView mTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Binding views
        ButterKnife.bind(this);

        //setting clickListener
        mContinueButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this, "Welcome To Top News",Toast.LENGTH_LONG).show();

        //passing intent between activity
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}