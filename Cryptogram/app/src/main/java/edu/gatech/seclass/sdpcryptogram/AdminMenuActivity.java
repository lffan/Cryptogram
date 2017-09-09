package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by chaiyixiao on 04/07/2017.
 */
import static edu.gatech.seclass.sdpcryptogram.R.layout.admin_menu;

public class AdminMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(admin_menu);

        Button addPlayer = (Button) findViewById(R.id.add_player);
        Button addCryptogram = (Button) findViewById(R.id.add_cryptogram);

        // jump to add a new player
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenuActivity.this, AdminAddPlayerActivity.class);
                startActivity(intent);
            }
        });

        // jump to add a new cryptogram
        addCryptogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenuActivity.this, AdminAddCryptogramActivity.class);
                startActivity(intent);
            }
        });
    }
}
