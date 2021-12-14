package com.adams.topnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adams.topnews.R;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.otherSignup)
    TextView mOtherSignup;
    @BindView(R.id.loginTextView)
    TextView mLoginTextView;
    @BindView(R.id.userNameEditText)
    EditText mUserNameEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.loginButton)
    Button mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Binding Views
        ButterKnife.bind(this);

        //setting clickListener
        mOtherSignup.setOnClickListener(this);

        mLoginButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if (view == mOtherSignup){
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }

        if (view == mLoginButton){
            Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
            startActivity(intent);
            finish();
        }
    }
}