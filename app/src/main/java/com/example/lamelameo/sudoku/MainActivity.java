package com.example.lamelameo.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "lamemessage";

    // First activity called when app is run
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate:");

        // intent to change to gamescreen to be used on user difficulty selection
        final Intent intent1 = new Intent(this, asyncTest.class);
        final Intent intent2 = new Intent(this, testing.class);
        final Intent intent4 = new Intent(this, GameActivity.class);

        // making interactive buttons
        Button b_easy = findViewById(R.id.button_easy);
        Button b_medium = findViewById(R.id.button_medium);
        Button b_hard = findViewById(R.id.button_hard);
        Button b_load = findViewById(R.id.button_load);

        // drop down list to select which puzzle you want
        final Spinner easyDropDown = findViewById(R.id.easySpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.drop_down_array,
                R.layout.support_simple_spinner_dropdown_item);
        easyDropDown.setAdapter(arrayAdapter);

        b_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // selected puzzle number
                int puzzleNum = easyDropDown.getSelectedItemPosition();
                // pass the puzzle number through to the game activity
                if (puzzleNum == 0) {  // if the top 'Puzzle#' selection was set give a warning to choose a puzzle
                    Toast errorMessage = Toast.makeText(getApplicationContext(), "Choose a Puzzle Number!", Toast.LENGTH_SHORT);
                    errorMessage.show();
                } else {  // if a number from dropdown was selected proceed to puzzle of that number
                    intent4.putExtra("puzzle number", puzzleNum);
                    // start activity: testing
                    startActivity(intent4);
                }
            }
        });
    }

//    // called when activity becomes visible to user
//    @Override
//    protected void onStart()  {
//        super.onStart();
//        Log.i(TAG, "onStart:");
//    }
//
//    // called when user interaction occurs
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i(TAG, "onResume:");
//
//    }
//
//    // called when activity is in background
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i(TAG, "onPause:");
//    }
//
//    // called when activity is not visible.. app closed or in background
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i(TAG, "onStop:");
//        //used for cpu intensive shutdown operations like saving information
//    }
//
//    // called when app is closed
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy:");
//    }
}
