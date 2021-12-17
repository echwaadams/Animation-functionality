package com.adams.topnews.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adams.topnews.Constants;
import com.adams.topnews.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener{
    // member variables to store reference to the sharedPreference
//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;

    private DatabaseReference mSearchedLocationReference;

    private ValueEventListener mSearchedLocationReferenceListener;

    @BindView(R.id.locationTextView)
    TextView mLocationTextView;
    @BindView(R.id.locationEditText)
    EditText mLocationEditText;
    @BindView(R.id.findHomesButton)
    Button mFindHomesButton;
    @BindView(R.id.savedNewsButton) Button mSavedNewsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);
        //Attach ValueListener
        mSearchedLocationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()){
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated","location" + location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        //binding Views
        ButterKnife.bind(this);

        //sharedPreference
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

        //setting clickListener
        mFindHomesButton.setOnClickListener(this);
        mSavedNewsButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view){
        if (view == mFindHomesButton){
            String location = mLocationEditText.getText().toString();
            saveLocationToFirebase(location);

            //method to take user inputted-zipcode as an argument.
//            if (!(location).equals("")){
//                addToSharedPreferences(location);
//            }
            Intent intent = new Intent(LocationActivity.this, NewsListActivity.class);
            startActivity(intent);
            if (view == mSavedNewsButton) {
                Intent intent2 = new Intent(LocationActivity.this, SavedNewsListActivity.class);
                startActivity(intent2);
            }
        }
    }
    public void saveLocationToFirebase(String location){
         // Unique Node IDs
        //push method ensures each new entry is added to the node under unique, randomly generated id
        mSearchedLocationReference.push().setValue(location);
    }
    //removing listener
    @Override
    public void onDestroy(){
        super.onDestroy();
        mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
    }
//    //call upon editor to write information to the shared preference.and finally calling apply




}