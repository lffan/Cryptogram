package edu.gatech.seclass.sdpcryptogram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.gatech.seclass.utilities.ExternalWebService;

import static edu.gatech.seclass.sdpcryptogram.R.layout.play_cryptogram;

/**
 * Created by chaiyixiao on 04/07/2017.
 */

public class PlayCryptogramActivity extends AppCompatActivity {

    private ArrayList<String> mEncodedLetters = new ArrayList<>();
    private ArrayList<String> mySolutionLetters = new ArrayList<>();

    private String cryptogramId = "";
    private String solutionStr = "";
    private String username = "";

    private RecyclerView playRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private PlayCryptogramAdapter mAdapter;

    private DatabaseReference mDatabase;
    private PlayCryptogram mPlayCrypt = new PlayCryptogram();
    private Player currentPlayer = new Player();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(play_cryptogram);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        playRecyclerView = (RecyclerView) findViewById(R.id.play_cryptogram_recycler_view);
        playRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 10);
        playRecyclerView.setLayoutManager(mGridLayoutManager);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            cryptogramId = (String) b.get("CRYPTOGRAM_ID");
            char[] encodedArr = ((String) b.get("CRYPTOGRAM_ENCODED")).toCharArray();
            for (char c : encodedArr) {
                mEncodedLetters.add(String.valueOf(c));
            }
            solutionStr = (String) b.get("CRYPTOGRAM_SOLUTION");
            username = (String) b.get("USERNAME");

            mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();
            mDatabase.child("players").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentPlayer = dataSnapshot.getValue(Player.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            mDatabase.child("playCryptograms").child(username).child(cryptogramId).addListenerForSingleValueEvent(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(PlayCryptogram.class) != null) {
                        mPlayCrypt = dataSnapshot.getValue(PlayCryptogram.class);
                        String mySolutionStr = mPlayCrypt.getPriorSolution();
                        char[] mySolutionArr = mySolutionStr.toCharArray();

                        if (!mySolutionStr.isEmpty()) {
                            for (char c : mySolutionArr) {
                                mySolutionLetters.add(String.valueOf(c));
                            }
                        } else {
                            resetEmptyMySolutionLetters();
                        }
                    } else {
                        resetEmptyMySolutionLetters();
                    }
                    mAdapter = new PlayCryptogramAdapter(mEncodedLetters, mySolutionLetters, listener);

                    mAdapter.setOnLetterChangedListener(new PlayCryptogramAdapter.OnLetterChangedListener() {
                        @Override
                        public void OnLetterChanged(int position, String encoded, String mSolution) {
//                            Log.v("pxx", String.valueOf(position));
//                            mAdapter.notifyItemChanged(position);
                        }
                    });
                    playRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }); //
        }

        final Button submit = (Button) findViewById(R.id.submit_cryptogram_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer sb = saveProgress();

                if (sb.toString().replace(" ", "").length() == solutionStr.replace(" ", "").length()) {

                    if (sb.toString().equals(solutionStr)) {
                        submit.setError(null);
                        if (!mPlayCrypt.getProgress().equals("Solved")) {
                            mPlayCrypt.setProgressComplete();
                            currentPlayer.addSolvedCount();
                        }
                        LinearLayout layout = (LinearLayout) findViewById(R.id.right_submit_answer);
                        layout.setVisibility(View.VISIBLE);
//                    PlayCryptogramActivity.this.finish();
                    } else {
                        Toast.makeText(context, "Wrong Answer!", duration).show();
                        submit.setError("Wrong Answer!");
                        mPlayCrypt.addIncorrectSubmit();
                        currentPlayer.addTotalIncorrect();
                    }
                    mDatabase.child("playCryptograms").child(username).child(mPlayCrypt.getCryptogramId()).setValue(mPlayCrypt);
                    mDatabase.child("players").child(username).setValue(currentPlayer);
                    mDatabase.keepSynced(true);
                    ExternalWebService.getInstance().updateRatingService(username, currentPlayer.getFirstname(), currentPlayer.getLastname(), currentPlayer.getSolvedCount(), currentPlayer.getStarted(), currentPlayer.getTotalIncorrect());

                } else {
                    submit.hasFocus();
                    submit.setError("Complete all the letters before submit!");
                    Toast.makeText(context, "Complete all the letters before submit!", duration).show();
                }
            }
        });
        // back
        Button back = (Button) findViewById(R.id.back_cryptogram_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayCryptogramActivity.this.finish();
            }
        });

        //reset
        Button reset = (Button) findViewById(R.id.reset_cryptogram_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setError(null);
                resetEmptyMySolutionLetters();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveProgress();
    }

    private StringBuffer saveProgress() {
        StringBuffer sb = new StringBuffer();

        for (String mySolutionLetter : mySolutionLetters) {
            if (mySolutionLetter.isEmpty()) {
                sb.append(" ");
            } else {
                sb.append(mySolutionLetter);
            }
        }
        mPlayCrypt.setPriorSolution(sb.toString());
        mDatabase.child("playCryptograms").child(username).child(mPlayCrypt.getCryptogramId()).setValue(mPlayCrypt);
        return sb;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void resetEmptyMySolutionLetters() {
        mySolutionLetters.clear();
        for (String l : mEncodedLetters) {
            if (l.matches("[a-zA-Z]")) {
                mySolutionLetters.add(" ");
            } else {
                mySolutionLetters.add(l);
            }
        }
    }

    private PlayCryptogramAdapter.OnRefreshRVListener listener = new PlayCryptogramAdapter.OnRefreshRVListener() {
        @Override
        public void onRefresh(final String encoded, final String replace) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < mEncodedLetters.size(); i++) {
                        String iLetter = mEncodedLetters.get(i);
                        if (iLetter.equals(encoded.toLowerCase())) {
                            mySolutionLetters.set(i, replace.toLowerCase());
                        } else if (iLetter.equals(encoded.toUpperCase())) {
                            mySolutionLetters.set(i, replace.toUpperCase());

                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    private Handler handler = new Handler(Looper.getMainLooper());
}
