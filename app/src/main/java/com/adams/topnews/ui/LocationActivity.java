package com.adams.topnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adams.topnews.Constants;
import com.adams.topnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener{
    // member variables to store reference to the sharedPreference
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @BindView(R.id.locationTextView)
    TextView mLocationTextView;
    @BindView(R.id.locationEditText)
    EditText mLocationEditText;
    @BindView(R.id.findHomesButton)
    Button mFindHomesButton;
    @BindView(R.id.savedNewsButton) Button mSavedNewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        //binding Views
        ButterKnife.bind(this);

        //sharedPreference
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        //setting clickListener
        mFindHomesButton.setOnClickListener(this);
        mSavedNewsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if (view == mFindHomesButton){
            String location = mLocationEditText.getText().toString();
            //method to take user inputted-zipcode as an argument.
            if (!(location).equals("")){
                addToSharedPreferences(location);
            }
            Intent intent = new Intent(LocationActivity.this, NewsListActivity.class);
            startActivity(intent);
        }
    }
    //call upon editor to write information to the shared preference.and finally calling apply
    private void addToSharedPreferences(String location){
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}