package com.firetvapps.hangman.home.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Toast;

import com.firetvapps.hangman.R;
import com.firetvapps.hangman.home.view.HomeFragment;
import com.firetvapps.hangman.home.view.PLayFragment;

public class LetterAdapter extends BaseAdapter {
    private String[] letters;
    private LayoutInflater letterInf;
    private Context mContext;
    private PLayFragment fragment;

    public LetterAdapter(Context c, PLayFragment fragment) {
        letters=new String[26];
        for (int a = 0; a < letters.length; a++) {
            letters[a] = "" + (char)(a+'A');
        }
        //specify the context in which we want to inflate the layout
        // will be passed from the main activity
        letterInf = LayoutInflater.from(c);
        mContext = c;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {

        return letters.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create a button for the letter at this position in the alphabet
        final Button letterBtn;
        if (convertView == null) {
            //inflate the button layout
            letterBtn = (Button)letterInf.inflate(R.layout.letter, parent, false);
        } else {
            letterBtn = (Button) convertView;
        }
        //set the text to this letter
        letterBtn.setText(letters[position]);
        //request focus for first letter

        if(position == 0) {
            letterBtn.setFocusable(true);
            letterBtn.setSelected(true);
            letterBtn.requestFocus();
            letterBtn.setBackgroundResource(R.drawable.letter_selected);
        }

        letterBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if(!((Button) view).isEnabled()) {
                        ((Button) view).setSelected(true);
                        //Toast.makeText(mContext, "Focus: true | " + ((Button) view).getText().toString() ,Toast.LENGTH_SHORT).show();
                        ((Button) view).setBackgroundResource(R.drawable.letter_selected);
                    }

                } else {
                    ((Button) view).setSelected(false);
                    //Toast.makeText(mContext, "Focus: false | " + ((Button) view).getText().toString() ,Toast.LENGTH_SHORT).show();
                    if(((Button) view).isEnabled()) {
                        ((Button) view).setBackgroundResource(R.drawable.letter_up);
                    }


                }
            }
        });



        letterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Pressed: " + letterBtn.getText().toString(), Toast.LENGTH_SHORT).show();
                letterBtn.setEnabled(false);
                letterBtn.setBackgroundResource(R.drawable.letter_down);
                fragment.letterPressed(letterBtn.getText().toString());
            }
        });
        return letterBtn;

    }

}