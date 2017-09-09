package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;
import edu.gatech.seclass.sdpcryptogram.AvailableCryptogramsAdapter.OnItemClickListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by wc on 04/07/2017.
 */

public class AvailableCryptogramsFragment extends Fragment {

    private TextView solved;
    private TextView started;
    private TextView totalIncorrect;
    private RecyclerView availableCryptogramRecyclerView;
    private LinearLayoutManager acLayoutManager;
    private AvailableCryptogramsAdapter mAdapter;

    private ArrayList<Cryptogram> mCryptogramList;
    private ArrayList<PlayCryptogram> mPlayCryptograms;
    private DatabaseReference mDatabase;

    private String username = "";
    private Player currentPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.available_cryptograms_fragment, container, false);
        mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();

        solved = (TextView) v.findViewById(R.id.solved_num);
        started = (TextView) v.findViewById(R.id.started_num);
        totalIncorrect = (TextView) v.findViewById(R.id.incorrect_num);

        availableCryptogramRecyclerView = (RecyclerView) v.findViewById(R.id.available_cryptograms_recycler_view);
        availableCryptogramRecyclerView.setHasFixedSize(true);
        acLayoutManager = new LinearLayoutManager(getActivity());
        availableCryptogramRecyclerView.setLayoutManager(acLayoutManager);

        Button requestButton = (Button) v.findViewById(R.id.request_new_cryptograms);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncCryptograms();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        username = getActivity().getIntent().getExtras().getString("USERNAME");
        mCryptogramList = new ArrayList<>();
        mPlayCryptograms = new ArrayList<>();

        mAdapter = new AvailableCryptogramsAdapter(username, mCryptogramList, mPlayCryptograms);
        availableCryptogramRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, String cId) {
                setCryptogramStarted(cId);
                Intent intent = new Intent(getActivity(), PlayCryptogramActivity.class);
                intent.putExtra("CRYPTOGRAM_ID", cId);
                intent.putExtra("USERNAME", username);
                Cryptogram cryptogram = getSelectedCryptogram(cId);
                if (cryptogram != null) {
                    intent.putExtra("CRYPTOGRAM_ENCODED", cryptogram.encodedPhrase);
                    intent.putExtra("CRYPTOGRAM_SOLUTION", cryptogram.solutionPhrase);
                    startActivity(intent);
                }
            }
        });

//     2
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase.child("players").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentPlayer = dataSnapshot.getValue(Player.class);
                solved.setText(String.valueOf(currentPlayer.getSolvedCount()));
                started.setText(String.valueOf(currentPlayer.getStarted()));
                totalIncorrect.setText(String.valueOf(currentPlayer.getTotalIncorrect()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase.child("cryptograms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCryptogramList.clear();
                mPlayCryptograms.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cryptogram cr = snapshot.getValue(Cryptogram.class);
                    PlayCryptogram pc = new PlayCryptogram(username, cr.cryptoId);

                    mCryptogramList.add(cr);
                    mPlayCryptograms.add(pc);
                }
                // upload local cryptograms to external web service
                List<String[]> extCrypts = ExternalWebService.getInstance().syncCryptogramService();
                ArrayList<String> extIds = new ArrayList<>();
                for (String[] extCrypt : extCrypts) {
                    List<String> arr = Arrays.asList(extCrypt);
                    extIds.add(arr.get(0));
                }
                for (Cryptogram cr: mCryptogramList) {
                    if (!extIds.contains(cr.cryptoId)) {
                        ExternalWebService.getInstance().addCryptogramService(cr.encodedPhrase, cr.solutionPhrase);
                    }
                }
                mAdapter.notifyDataSetChanged();

                storeCryptogramList();
                mDatabase.keepSynced(true);

                mDatabase.child("playCryptograms").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PlayCryptogram pc = snapshot.getValue(PlayCryptogram.class);
                            for (int i = 0; i < mPlayCryptograms.size(); i++) {
                                if (mPlayCryptograms.get(i).getCryptogramId().equals(pc.getCryptogramId())) {
                                    mPlayCryptograms.set(i, pc);
                                }
                            }
                        }
                        storePlayCryptogramList();
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setCryptogramStarted(String cId) {
        PlayCryptogram pc = getSelectedPlayCryptogram(cId);
        if (pc != null) {
            if (pc.getProgress().equals("Not started")) {
                currentPlayer.addStarted();
                started.setText(String.valueOf(currentPlayer.getStarted()));
                mDatabase.child("players").child(username).setValue(currentPlayer);
                pc.setInProgress();
                mDatabase.child("playCryptograms").child(username).child(pc.getCryptogramId()).setValue(pc);
                mDatabase.keepSynced(true);
                mAdapter.notifyDataSetChanged();
                ExternalWebService.getInstance().updateRatingService(username, currentPlayer.getFirstname(), currentPlayer.getLastname(), currentPlayer.getSolvedCount(), currentPlayer.getStarted(), currentPlayer.getTotalIncorrect());
            }
        }
    }


    private void syncCryptograms() {
        List<String[]> newCrypts = ExternalWebService.getInstance().syncCryptogramService();
        ArrayList<String> currentIds = new ArrayList<>();
        for (Cryptogram crypto : mCryptogramList) {
            currentIds.add(crypto.cryptoId);
        }
        for (String[] newCrypt : newCrypts) {
            List<String> arr = Arrays.asList(newCrypt);
            if (!currentIds.contains(arr.get(0))) {
                Cryptogram newCryptogram = new Cryptogram(arr);
                mCryptogramList.add(newCryptogram);
                PlayCryptogram newPc = new PlayCryptogram(username, arr.get(0));
                mPlayCryptograms.add(newPc);
            }
        }
        mAdapter.notifyDataSetChanged();
        storeCryptogramList();
        storePlayCryptogramList();
    }

    private Cryptogram getSelectedCryptogram(String cId) {
        for (Cryptogram cryptogram : mCryptogramList) {
            if (cryptogram.cryptoId.equals(cId)) {
                return cryptogram;
            }
        }
        return null;
    }

    private PlayCryptogram getSelectedPlayCryptogram(String cId) {
        for (PlayCryptogram pc : mPlayCryptograms) {
            if (pc.getCryptogramId().equals(cId)) {
                return pc;
            }
        }
        return null;
    }

    private void storeCryptogramList () {
        HashMap<String, Cryptogram> cryptoMap = new HashMap();
        for (Cryptogram c : mCryptogramList) {
            cryptoMap.put(c.cryptoId, c);
        }
        mDatabase.child("cryptograms").setValue(cryptoMap);
    }

    private void storePlayCryptogramList () {
        HashMap<String, PlayCryptogram> playCryptoMap = new HashMap();
        for (PlayCryptogram p : mPlayCryptograms) {
            playCryptoMap.put(p.getCryptogramId(), p);
        }
        mDatabase.child("playCryptograms").child(username).setValue(playCryptoMap);
    }
}