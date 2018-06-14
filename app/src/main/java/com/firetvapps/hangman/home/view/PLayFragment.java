package com.firetvapps.hangman.home.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firetvapps.hangman.R;
import com.firetvapps.hangman.home.model.WordGenerator;
import com.firetvapps.hangman.home.view.adapter.LetterAdapter;

import java.util.Random;

public class PLayFragment extends Fragment {

    private GridView letters;
    private LetterAdapter ltrAdapt;
    private LinearLayout wordLayout;
    private TextView[] charViews;
    private WordGenerator wordGenerator;
    private TextView tv_chances_left;

    //current word to guess
    private String currWord;

    //current part - will increment when wrong answers are chosen
    private int wrongGuess;

    //number of characters in current word
    private int numChars;

    //number correctly guessed
    private int rightGuess;

    //chances
    private int chancesLeft = 6;

    public static PLayFragment newInstance() {

        Bundle args = new Bundle();

        PLayFragment fragment = new PLayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, null);
        initView(view);
        return view;
    }

    private void initView(View view){
        wordLayout = view.findViewById(R.id.word_layout);
        letters = view.findViewById(R.id.soft_keyboard);
        ltrAdapt=new LetterAdapter(getActivity(), this);
        tv_chances_left = view.findViewById(R.id.tv_chances_left);

        playGame();
    }

    private void playGame(){
        wrongGuess = 0;
        rightGuess = 0;
        chancesLeft = 6;

        letters.setAdapter(ltrAdapt);

        tv_chances_left.setText(""+6);
        wordGenerator = new WordGenerator();
        String[] words = wordGenerator.getWords();

        Random rand = new Random();
        String newWord = words[rand.nextInt(words.length)];

        while (newWord.equals(currWord))  {
            newWord = words[rand.nextInt(words.length)];
        }
        currWord = newWord;

        charViews = new TextView[currWord.length()];
        wordLayout.removeAllViews();
        numChars = currWord.length();
        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(getActivity());


            charViews[c].setText(""+currWord.charAt(c));

            charViews[c].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);

            if(currWord.charAt(c) == ' '){
                charViews[c].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                charViews[c].setGravity(Gravity.CENTER);
                charViews[c].setTextColor(Color.WHITE);
                charViews[c].setVisibility(View.INVISIBLE);
                numChars = numChars-1;
            }

            //add to layout
            wordLayout.addView(charViews[c]);

        }

    }

    public void letterPressed(String letter) {
        //user has pressed a letter to guess
        boolean correct = false;
        for (int k = 0; k < currWord.length(); k++) {
            if (currWord.toUpperCase().charAt(k) == letter.toUpperCase().charAt(0) ) {
                correct = true;
                rightGuess++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }

        if (correct) {
            //correct guess
            if (rightGuess == numChars) {
                //user has won
                // Disable Buttons
                disableButtonsOfKeyboard();

                // Display Alert Dialog
                AlertDialog.Builder winnerBuilder = new AlertDialog.Builder(getActivity());
                winnerBuilder.setTitle("You won this time!");
                winnerBuilder.setMessage("Have fun !!");
                winnerBuilder.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                playGame();
                            }
                        });

                winnerBuilder.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getActivity().finish();
                            }
                        });

                winnerBuilder.show();
            }
        } else if (wrongGuess < chancesLeft - 1) {
            wrongGuess++;
            tv_chances_left.setText(""+(6 - wrongGuess));
        } else {
            //user has lost
            disableButtonsOfKeyboard();
            tv_chances_left.setText(""+0);

            // Display Alert Dialog
            AlertDialog.Builder looserBuilder = new AlertDialog.Builder(getActivity());
            looserBuilder.setTitle("You suck !!");
            looserBuilder.setMessage("You lose!\n\nThe answer was:\n\n" + currWord);
            looserBuilder.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            playGame();
                        }
                    });

            looserBuilder.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getActivity().finish();
                        }
                    });

            looserBuilder.show();

        }
    }

    public void disableButtonsOfKeyboard() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }
}
