package edu.gatech.seclass.sdpcryptogram;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wc on 05/07/2017.
 */

public class AvailableCryptogramsAdapter extends RecyclerView.Adapter<AvailableCryptogramsAdapter.CryptogramHolder> implements View.OnClickListener {

    private ArrayList<Cryptogram> mCryptograms;
    private ArrayList<PlayCryptogram> mPlayCryptograms;
    private String username = "";

    private static OnItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, String cId);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    public static class CryptogramHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener {

        private TextView cryptogramId;
        private TextView progress;
        private TextView incorrectNum;

        public CryptogramHolder(View v) {
            super(v);
            cryptogramId = (TextView) v.findViewById(R.id.available_cryptogram_id);
            progress = (TextView) v.findViewById(R.id.available_cryptogram_progress);
            incorrectNum = (TextView) v.findViewById(R.id.available_cryptogram_incorrect);
        }

        public void bindCryptogram(String u, Cryptogram c, PlayCryptogram p) {

            cryptogramId.setText(c.cryptoId);
            progress.setText(p.getProgress());
            incorrectNum.setText(String.valueOf(p.getIncorrectSubmit()));

        }
    }

    public AvailableCryptogramsAdapter(String username, ArrayList<Cryptogram> cryptograms, ArrayList<PlayCryptogram> playCryptograms) {
        this.username = username;
        this.mCryptograms = cryptograms;
        this.mPlayCryptograms = playCryptograms;
    }

    @Override
    public AvailableCryptogramsAdapter.CryptogramHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_cryptograms_recycler_item_row, parent, false);
        CryptogramHolder ch = new CryptogramHolder(inflatedView);
        //register click listener
        inflatedView.setOnClickListener(this);
        return ch;
    }

    @Override
    public void onBindViewHolder(CryptogramHolder holder, int position) {
        Cryptogram crypto = mCryptograms.get(position);
        PlayCryptogram pc = mPlayCryptograms.get(position);
        holder.itemView.setTag(pc.getCryptogramId());
        holder.bindCryptogram(username, crypto, pc);
    }

    @Override
    public int getItemCount() {
        return mCryptograms.size();
    }

}
