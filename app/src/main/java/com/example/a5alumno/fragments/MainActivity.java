package com.example.a5alumno.fragments;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import Fragments.Fragment1;
import Fragments.Fragment2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Fragment1.OnFragment1Interface {

    private boolean mTwoPane;
    LinearLayout mLandLinearLayout;
    private static final String TAG_MAIN_ACTIVITY = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.frame_layout_main_land) == null;

        if(!mTwoPane){
            // Add fragment dynamically
            android.support.v4.app.FragmentManager fragManager = this.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragTransaction = fragManager.beginTransaction();
            fragTransaction.replace(R.id.frame_layout_main_land, new Fragment1(), "first fragment");
            fragTransaction.commit();

            final Button swapFragment_Btn = (Button) this.findViewById(R.id.swap_button);
            swapFragment_Btn.setOnClickListener(this);

            // To set up the layout background color, we use 'ContextCompat.getColor()' due to minSDK = 11
            this.mLandLinearLayout = (LinearLayout) this.findViewById(R.id.land_linear_layout);
            this.mLandLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGreen));
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.swap_button){
            android.support.v4.app.FragmentManager fragManager = this.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragTransaction = fragManager.beginTransaction();
            Fragment frag = fragManager.findFragmentByTag(this.getString(R.string.first_fragment_tag));

            if(frag != null){
                Log.e(MainActivity.TAG_MAIN_ACTIVITY, "Fragment1 es reemplazado por Fragment2");
                fragTransaction.replace(R.id.frame_layout_main_land, new Fragment2(), this.getString(R.string.second_fragment_tag));
                fragTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragTransaction.commit();
                this.mLandLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lightYellow));
            }else{
                Log.e(MainActivity.TAG_MAIN_ACTIVITY, "Fragment2 es reemplazado por Fragment1");
                fragTransaction.replace(R.id.frame_layout_main_land, new Fragment1(), this.getString(R.string.first_fragment_tag));
                fragTransaction.commit();
                this.mLandLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGreen));
            }
        }
    }

    // Implementacion del metodo abstracto de Fragment1
    @Override
    public void onCommFromFragmentOne(long rNumber) {
        Log.e(TAG_MAIN_ACTIVITY, "" + rNumber);
        Fragment2 mySecondFragment = (Fragment2) this.getSupportFragmentManager().findFragmentById(R.id.fragment_second);
        mySecondFragment.updateInfoTextView(String.valueOf(rNumber));
    }
}
