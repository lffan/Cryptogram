package edu.gatech.seclass.sdpcryptogram;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by chaiyixiao on 04/07/2017.
 */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

import static edu.gatech.seclass.sdpcryptogram.R.layout.add_player;

public class AdminAddPlayerActivity extends AppCompatActivity {

    private EditText username;
    private EditText firstname;
    private EditText lastname;

    private DatabaseReference mDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(add_player);
        mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();
        Button saveBtn = (Button) findViewById(R.id.save_player);
        Button cancelBtn = (Button) findViewById(R.id.cancel_add_player);

        username = (EditText) findViewById(R.id.add_username);
        firstname = (EditText) findViewById(R.id.add_first_name);
        lastname = (EditText) findViewById(R.id.add_last_name);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        // button SAVE clicked
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the username list from the external webservice
                List<String> usernameList =  ExternalWebService.getInstance().playernameService();

                String usernameStr = username.getText().toString();
                String firstnameStr = firstname.getText().toString();
                String lastnameStr = lastname.getText().toString();

                // save the new player info to the external webservice
                if (!usernameList.contains(usernameStr)) {
                    // check if username is unique
                    boolean addPlayerSuccessful = ExternalWebService.getInstance().updateRatingService(
                            usernameStr, firstnameStr, lastnameStr, 0, 0, 0);
                    if (addPlayerSuccessful) {
                        // check if the new player is added successfully
                        // pop up a confirmation message
                        Toast.makeText(context, "New player created successfully!", duration).show();
                        // reset the input
                        username.setText("");
                        firstname.setText("");
                        lastname.setText("");
                        // popup a message to promote administrator to add another player
                        Toast.makeText(context, "Add another player or cancel.", duration).show();

                        // add the new player to the local database created by ourselves
                        Player newPlayer = new Player(usernameStr, firstnameStr, lastnameStr);
                        mDatabase.child("players").child(usernameStr).setValue(newPlayer);
                    } else {
                        // failed if any values are null or empty or cannot add to player ratings
                        Toast.makeText(context, "Invalid input or duplicated username.", duration).show();
                    }
                } else {
                    // failed if any values are null or empty or cannot add to player ratings
                    Toast.makeText(context, "Invalid input or duplicated username.", duration).show();
                }
            }
        });

        // button CANCEL clicked
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddPlayerActivity.this.finish();
            }
        });
    }
}
