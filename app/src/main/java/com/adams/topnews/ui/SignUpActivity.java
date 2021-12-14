package com.adams.topnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adams.topnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = SignUpActivity.class.getSimpleName();

    //member variable
    @BindView(R.id.otherSignin) TextView mOtherSignIn;
//    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signupTextView)
    TextView mSignupTextView;
    @BindView(R.id.signupButton)
    Button mSignupButton;
    @BindView(R.id.emailEditText)
    EditText mEmailEditText;
    @BindView(R.id.passwordEditText)
    EditText mPasswordEditText;
//    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
//    @BindView(R.id.userNameEditText) EditText mUserNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Binding views
        ButterKnife.bind(this);

        //setting clickListener
        mSignupButton.setOnClickListener(this);
        mOtherSignIn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if (view == mOtherSignIn){
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        //passing intent to login activity
        if (view == mSignupButton){
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
//        //create new user
//        if (view == mSignupButton){
//            createNewUser();
//        }
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);

    }
    //user creation
//    private void createNewUser(){
//
//        final String name = mUserNameEditText.getText().toString().trim();
//        final String email = mEmailEditText.getText().toString().trim();
//        String password = mPasswordEditText.getText().toString().trim();
//        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
//    }
}