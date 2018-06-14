package com.firetvapps.hangman.home.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.firetvapps.hangman.R;
import com.firetvapps.hangman.utils.AppConstants;

public class MainActivity extends AppCompatActivity{

    private FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView(){
        switchFragmentOptions(HomeFragment.newInstance(), AppConstants.TAG_HOME_FRAGMENT, true);
    }

    public void switchFragmentOptions(Fragment newChangeFragment, String fragmentTAG, boolean addToBackStack) {
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();

        //To avoid the calling of service when same selected tab is clicked again & again
        if (getSupportFragmentManager().findFragmentById(R.id.home_container) != null && getSupportFragmentManager().findFragmentByTag(fragmentTAG) != null
                && (getSupportFragmentManager().findFragmentById(R.id.home_container) == getSupportFragmentManager().findFragmentByTag(fragmentTAG))) {

            return;
        }


        //Add to BackStack with TAG
        if (addToBackStack) {
            fragTransaction.addToBackStack(fragmentTAG);
        }
        fragTransaction.replace(R.id.home_container, newChangeFragment, fragmentTAG);
        fragTransaction.commit();

        //BackStack listener to update the MENU Tab selection on back press
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager.BackStackEntry backStackEntry = getSupportFragmentManager().getBackStackEntryAt
                        (getSupportFragmentManager().getBackStackEntryCount() - 1);

                //String FragmentTag = backStackEntry.getName();

            }
        });
    }
}
