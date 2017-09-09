package edu.gatech.seclass.sdpcryptogram;

import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static android.R.attr.x;

/**
 * Created by chai on 05/07/2017.
 */

public class PlayCryptogramAdapter extends RecyclerView.Adapter<PlayCryptogramAdapter.CryptogramHolder> {
    private ArrayList<String> encodedLetters;
    private ArrayList<String> mySolutionLetters;
    private OnRefreshRVListener listener;

    private static OnLetterChangedListener mOnLetterChangedListener = null;

    //define interface
    public interface OnLetterChangedListener {
        void OnLetterChanged(int position, String encoded, String solution);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener listener) {
        this.mOnLetterChangedListener = listener;
    }

    public class CryptogramHolder extends RecyclerView.ViewHolder {
        private EditText mySolutionLetter;
        private TextView encodedTextView;
        private String text;
        private int position;

        public CryptogramHolder(View v) {
            super(v);
            encodedTextView = (TextView) v.findViewById(R.id.cryptogram_encoded_letter);
            mySolutionLetter = (EditText) v.findViewById(R.id.cryptogram_my_letter);
            mySolutionLetter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (hasFocus) {
                        Log.v("position1", String.valueOf(position));
                        mySolutionLetter.addTextChangedListener(textWatcher);
                    } else {
                        Log.v("position0", String.valueOf(position));
                        mySolutionLetter.removeTextChangedListener(textWatcher);
                        if (mOnLetterChangedListener != null) {
                            mOnLetterChangedListener.OnLetterChanged(position, encodedLetters.get(position), mySolutionLetter.getText().toString());
                        }
                    }
                }
            });

        }

        public void bindCryptogram(String encodedCryptogram, String mySolutionCryptogram, int position) {
            mySolutionLetter.setEnabled(encodedCryptogram.matches("[a-zA-Z]"));
            mySolutionLetter.setClickable(encodedCryptogram.matches("[a-zA-Z]"));
            encodedTextView.setText(encodedCryptogram);
            mySolutionLetter.setText(mySolutionCryptogram.replace(" ", ""));
            text = encodedCryptogram;
            this.position = position;
        }

        private TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onRefresh(text, s.toString());
            }
        };
    }


    public PlayCryptogramAdapter(ArrayList<String> encodedLetters, ArrayList<String> mySolutionLetters, OnRefreshRVListener l) {
        this.encodedLetters = encodedLetters;
        this.mySolutionLetters = mySolutionLetters;
        listener = l;
    }

    @Override
    public PlayCryptogramAdapter.CryptogramHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.play_cryptogram_item, parent, false);
        return new CryptogramHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final PlayCryptogramAdapter.CryptogramHolder holder, final int position) {
        final String itemEncoded = encodedLetters.get(position);
        String itemMySolution = mySolutionLetters.get(position);
        holder.bindCryptogram(itemEncoded, itemMySolution, position);
    }

    @Override
    public int getItemCount() {
        return encodedLetters.size();
    }


    public interface OnRefreshRVListener {
        void onRefresh(String encoded, String replace);
    }
}
