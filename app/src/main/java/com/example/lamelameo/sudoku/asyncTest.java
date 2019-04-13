package com.example.lamelameo.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class asyncTest extends AppCompatActivity {

    private String TAG = "asyncTest";

    private CharSequence puzzle = "003020600900305001001806400008102900700000008006708200002609500800203009005010300";

    private View cell;

    // convert density independent pixels to pixels using the devices pixel density
    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        return (int) (dp * density + 0.5f);
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
            p.setMargins(left,top,right,bottom);
            view.requestLayout();
        }
    }

    private int CORES = Runtime.getRuntime().availableProcessors();

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(CORES*2, CORES*2,
            2, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private Runnable inflater(final ViewGroup viewGroup, final LayoutInflater layoutInflater) {
        Runnable inflaterTask = new Runnable() {
            @Override
            public void run() {
                layoutInflater.inflate(R.layout.pencil_values, viewGroup, false);
            }
        };
     return inflaterTask;
    }

    private void testo(final ViewGroup viewGroup, final LayoutInflater layoutInflater) {
        layoutInflater.inflate(R.layout.pencil_values, viewGroup, false);
        // TODO: use method to run inflation in asyncInflate but can pass varibles from loop to here which go to
        //  the class to go off to new threads
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_test);

        // set variables for rows in relative layout to inflate views into
        TableRow tableRow1 = findViewById(R.id.tableRow1);
        TableRow tableRow2 = findViewById(R.id.tableRow2);
        TableRow tableRow3 = findViewById(R.id.tableRow3);
        TableRow tableRow4 = findViewById(R.id.tableRow4);
        TableRow tableRow5 = findViewById(R.id.tableRow5);
        TableRow tableRow6 = findViewById(R.id.tableRow6);
        TableRow tableRow7 = findViewById(R.id.tableRow7);
        TableRow tableRow8 = findViewById(R.id.tableRow8);
        TableRow tableRow9 = findViewById(R.id.tableRow9);

        // array of all the rows
        final TableRow[] rows = {
                tableRow1, tableRow2, tableRow3, tableRow4, tableRow5,
                tableRow6, tableRow7, tableRow8, tableRow9
        };

        // convert 2dp to px for the grid column margins - used outside loop so only 1 call needed
        final int marginPx = dpToPx(2);

        //TODO: use this threading class to run parallel inflation

//        final LayoutInflater layoutInflater = getLayoutInflater();
//        Log.i(TAG, "CORES: "+CORES);
//
//        AsyncInflate testInflate = new AsyncInflate();
//        // inflate 9 cells into the first row in parallel
//        testInflate.executeOnExecutor(AsyncInflate.THREAD_POOL_EXECUTOR,
//                layoutInflater, tableRow1, R.layout.pencil_values,
//                layoutInflater, tableRow2, R.layout.pencil_values);

//        AsyncLayoutInflater testInflater = new AsyncLayoutInflater(this);

        //TODO: async inflate 9 cells to grid using 9 async threads at once
//        AsyncLayoutInflater testInflater = new AsyncLayoutInflater(this);
//        AsyncLayoutInflater test2Inflater = new AsyncLayoutInflater(this);

//        for (int h = 0; h < 9; h++) {
//            final int rowIndex = h;
//
//            testInflater.inflate(R.layout.pencil_values, gridLayout, new AsyncLayoutInflater.OnInflateFinishedListener() {
//                @Override
//                public void onInflateFinished(@NonNull View view, int i, @Nullable ViewGroup viewGroup) {
//                    // does this in the main thread once the inflation is done
//                    view.setTag(rowIndex);
//                    ToggleButton cell = view.findViewById(R.id.sudokuButton);
//                    // set starting values for cells
//                    char startValue = puzzle.charAt(rowIndex);
//                    // value of 0 means no starting value
//                    if (startValue != '0') {
//                        CharSequence cellValue = " " + startValue + " ";  // setText wont accept char - spaces to convert
//                        cell.setTextOff(cellValue);
//                        cell.setTextColor(Color.BLUE);
//                        cell.setChecked(false);
//                    } else {
//                        Integer cellPos = rowIndex;
//                        cell.setTextOn("" + cellPos.toString());
//                        cell.setChecked(true);
//                    }
//
//                    gridLayout.addView(view, rowIndex);
//                }
//            });
//        }

        for(int i = 0; i < 9; i++){

            for(int h = 0; h < 9; h++){
                final int index = i * 9 + h + 1;
                final int rowIndex = h;

                final TableRow row = rows[i];

                //TODO: try loading cells onclick - need to use a background image for grid

                //TODO: try using just AsyncTaskLoader to put the whole block into the background thread
                //  also look into FRAGMENTS for a possible solution

//                testInflater.inflate(R.layout.pencil_values, row, new AsyncLayoutInflater.OnInflateFinishedListener() {
//                    @Override
//                    public void onInflateFinished(@NonNull View view, int i, @Nullable ViewGroup viewGroup) {
//                        // does this in the main thread once the inflation is done
//                        view.setTag(index);
//                        ToggleButton cell = view.findViewById(R.id.sudokuButton);
//                        // set starting values for cells
//                        char startValue = puzzle.charAt(index-1);
//                        // value of 0 means no starting value
//                        if (startValue != '0') {
//                            CharSequence cellValue = " "+startValue+" ";  // setText wont accept char - spaces to convert
//                            cell.setTextOff(cellValue);
//                            cell.setTextColor(Color.BLUE);
//                            cell.setChecked(false);
//                        } else {
//                            Integer cellPos = index;
//                            cell.setTextOn(""+cellPos.toString());
//                            cell.setChecked(true);
//                        }
//
//                        row.addView(view, rowIndex);  // make sure view is added to right spot in tablerow
//                        ConstraintLayout rowCell = (ConstraintLayout) row.getChildAt(rowIndex);
////                        Log.i(TAG, "onCreate1: "+rowCell);
//                        View cellChild = rowCell.getChildAt(0);
//                        ViewGroup.MarginLayoutParams params1 = (ViewGroup.MarginLayoutParams)rowCell.getLayoutParams();
//                        //TODO: cells overlap margins by 1dp on the right
//                        if(rowIndex == 2 || rowIndex == 5) {  // if 3rd or 6th column add margin of 2dp
//                            params1.rightMargin = marginPx;
//                        }
//                    }
//                });
//
//                // anything in the main thread will load before the async thread - got null pointer exceptions trying
//                // to access the views created in the other thread
//                View rowCell = row.getChildAt(h);
////                Log.i(TAG, "onCreate2: "+rowCell);

            }
        }
    }

    // called when activity becomes visible to user
    @Override
    protected void onStart()  {
        super.onStart();
    }

    // called when user interaction occurs
    @Override
    protected void onResume() {
        super.onResume();

        // string extra from main screen intent which give the difficulty chosen
        String ExtraDifficulty = getIntent().getStringExtra("difficulty");

        final Button b1,b2;
        final TextView difftext;

//        b1=findViewById(R.id.button1);
//        b2=findViewById(R.id.button2);
        difftext=findViewById(R.id.difficultytext);
        difftext.setText(ExtraDifficulty);


    }

}
