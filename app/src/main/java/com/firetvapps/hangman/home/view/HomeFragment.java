package com.firetvapps.hangman.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firetvapps.hangman.R;
import com.firetvapps.hangman.utils.AppConstants;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button play;
    private Button options;
    private Button exit;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }

    private void initView(View view){
        play = view.findViewById(R.id.btn_play);
        options = view.findViewById(R.id.btn_option);
        exit = view.findViewById(R.id.btn_exit);

        play.setOnClickListener(this);
        options.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btn_play:
                ((MainActivity)getActivity()).switchFragmentOptions(PLayFragment.newInstance(), AppConstants.TAG_PLAY_FRAGMENT, true);
                break;
            case R.id.btn_exit:
                getActivity().finish();
                break;
        }
    }
}
