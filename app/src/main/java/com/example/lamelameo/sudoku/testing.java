package com.example.lamelameo.sudoku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.*;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.v4.view.ViewCompat.generateViewId;

public class testing extends AppCompatActivity {

    private String TAG = "testing";

    private CharSequence[] easyPuzzles = {
            // list of 50 'easy' sudoku puzzle starting states
            "003020600900305001001806400008102900700000008006708200002609500800203009005010300",
            "200080300060070084030500209000105408000000000402706000301007040720040060004010003",
            "000000907000420180000705026100904000050000040000507009920108000034059000507000000",
            "030050040008010500460000012070502080000603000040109030250000098001020600080060020",
            "020810740700003100090002805009040087400208003160030200302700060005600008076051090",
            "100920000524010000000000070050008102000000000402700090060000000000030945000071006",
            "043080250600000000000001094900004070000608000010200003820500000000000005034090710",
            "480006902002008001900370060840010200003704100001060049020085007700900600609200018",
            "000900002050123400030000160908000000070000090000000205091000050007439020400007000",
            "001900003900700160030005007050000009004302600200000070600100030042007006500006800",
            "000125400008400000420800000030000095060902010510000060000003049000007200001298000",
            "062340750100005600570000040000094800400000006005830000030000091006400007059083260",
            "300000000005009000200504000020000700160000058704310600000890100000067080000005437",
            "630000000000500008005674000000020000003401020000000345000007004080300902947100080",
            "000020040008035000000070602031046970200000000000501203049000730000000010800004000",
            "361025900080960010400000057008000471000603000259000800740000005020018060005470329",
            "050807020600010090702540006070020301504000908103080070900076205060090003080103040",
            "080005000000003457000070809060400903007010500408007020901020000842300000000100080",
            "003502900000040000106000305900251008070408030800763001308000104000020000005104800",
            "000000000009805100051907420290401065000000000140508093026709580005103600000000000",
            "020030090000907000900208005004806500607000208003102900800605007000309000030020050",
            "005000006070009020000500107804150000000803000000092805907006000030400010200000600",
            "040000050001943600009000300600050002103000506800020007005000200002436700030000040",
            "004000000000030002390700080400009001209801307600200008010008053900040000000000800",
            "360020089000361000000000000803000602400603007607000108000000000000418000970030014",
            "500400060009000800640020000000001008208000501700500000000090084003000600060003002",
            "007256400400000005010030060000508000008060200000107000030070090200000004006312700",
            "000000000079050180800000007007306800450708096003502700700000005016030420000000000",
            "030000080009000500007509200700105008020090030900402001004207100002000800070000090",
            "200170603050000100000006079000040700000801000009050000310400000005000060906037002",
            "000000080800701040040020030374000900000030000005000321010060050050802006080000000",
            "000000085000210009960080100500800016000000000890006007009070052300054000480000000",
            "608070502050608070002000300500090006040302050800050003005000200010704090409060701",
            "050010040107000602000905000208030501040070020901080406000401000304000709020060010",
            "053000790009753400100000002090080010000907000080030070500000003007641200061000940",
            "006080300049070250000405000600317004007000800100826009000702000075040190003090600",
            "005080700700204005320000084060105040008000500070803010450000091600508007003010600",
            "000900800128006400070800060800430007500000009600079008090004010003600284001007000",
            "000080000270000054095000810009806400020403060006905100017000620460000038000090000",
            "000602000400050001085010620038206710000000000019407350026040530900020007000809000",
            "000900002050123400030000160908000000070000090000000205091000050007439020400007000",
            "380000000000400785009020300060090000800302009000040070001070500495006000000000092",
            "000158000002060800030000040027030510000000000046080790050000080004070100000325000",
            "010500200900001000002008030500030007008000500600080004040100700000700006003004050",
            "080000040000469000400000007005904600070608030008502100900000005000781000060000010",
            "904200007010000000000706500000800090020904060040002000001607000000000030300005702",
            "000700800006000031040002000024070000010030080000060290000800070860000500002006000",
            "001007090590080001030000080000005800050060020004100000080000030100020079020700400",
            "000003017015009008060000000100007000009000200000500004000000020500600340340200000",
            "300200000000107000706030500070009080900020004010800050009040301000702000000008006"
    };

    private RadioGroup rg1, rg2, rg3;

    private List<ConstraintLayout> cells = new ArrayList<>();

    // convert density independent pixels to pixels using the devices pixel density
    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        return (int) (dp * density + 0.5f);
    }

    // dptopx that accepts a floating point value input and context outputs an int with units of pixels
    private int dpToPx(float dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        long pixels = Math.round(dp * density); // rounds up/down around 0.5
//        Log.i("tag", "dpToPx: "+pixels);
        //TODO: can change to this one line instead...?
//        int pix = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
//        Log.i("tagtag", "dp to Px: "+pix);
        return (int) pixels;
    }

    private boolean checkList(ArrayList<ArrayList<Integer>> numList) {
        Integer[] numbers = {
                1, 2, 3, 4, 5, 6, 7, 8, 9
        };
        for(ArrayList array : numList) {
            for(Integer num : numbers) {  // check rows each have integers 1-9
                boolean getNum = array.contains(num);
                if(!getNum) {  // break loop by returning false for method
                    return false;
                }  // else continue to check values
            }
        } return true;  // return true if all checks are passed
    }

    private boolean checkGrid() {
        // check grid cells values to see if they correspond to a correct solution and return true if so

        //TODO: can change this to check each row/col/block as the value lists are created rather than after all are
        //  created to speed up the method, but not sure if make any difference...think it wasnt taking much time
        //  also can create 1 list for each group as class variable and just clear and repopulate each time instead

        // take values from the cells array made up of the layout objects (cells) that populate the grid
        // each layout contains a togglebutton or textview holding the value, all of which are the child at index 0
        // rows: each element is a grid row ArrayList with its elements being a position value in that row
        ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
        for(int x = 0; x < 9; x++) {
            ArrayList<Integer> row = new ArrayList<>();
            for(int y = 0; y < 9; y++) {
                int cellIndex = x*9 + y;
                // check if child view is a textview (starting value) else it a togglebutton (changeable value)
                boolean isStartValue = cells.get(cellIndex).getChildAt(0) instanceof AppCompatTextView;
                int cellVal;
                if(isStartValue) { // is a start value...value is stored as textviews text
                    AppCompatTextView cell = (AppCompatTextView) cells.get(cellIndex).getChildAt(0);
                    // have to take only the integer part of charsequence to convert to int
                    CharSequence val = cell.getText();
                    CharSequence charVal = val.subSequence(1,2);
                    cellVal = Integer.parseInt(charVal.toString());

                } else {  // isnt start value...value is stored in a togglebutton texton
                    ToggleButton cell = (ToggleButton)cells.get(cellIndex).getChildAt(0);
                    CharSequence emptybutton = getResources().getString(R.string.emptybutton);
                    CharSequence onValue = cell.getTextOn();
                    // cant change "" to an int so just pass it as 0
                    if(onValue == emptybutton) {
                        cellVal = 0;
                    } else {
                        cellVal = Integer.parseInt(onValue.toString());
                    }
                } row.add(cellVal);
//                Log.i(TAG, "checkGrid: "+ cellVal);
            }
            // populate rows with each row list of values
            rows.add(row);
        }

        // column list and values taken from row list made above
        ArrayList<ArrayList<Integer>> cols = new ArrayList<>();
        for(int x = 0; x < 9; x++) {
            ArrayList<Integer> col = new ArrayList<>();
            for(int y = 0; y < 9; y++) {
                // row to get values from increments 1-9 for each column
                ArrayList currentRow = rows.get(y);
                // position of the value to take from that row increments with outer loop
                Integer colValue = (Integer)currentRow.get(x);
                col.add(colValue);
            }
            cols.add(col);
        }

        // block list and values taken from rows list
        ArrayList<ArrayList<Integer>> blocks = new ArrayList<>();
        for(int x = 0; x < 9; x++) {
            // add 9 block lists to blocks - need to create first as values are added to multiple blocks in each loop
            ArrayList<Integer> block = new ArrayList<>();
            blocks.add(block);
        }

        // loop through 9 rows (using rows list) and add cell values in groups of 3 to the
        // corresponding 3 blocks that intersect the current row.
        int blockIndex;

        for(int x = 0; x < 9; x++) {
            // set counter2 based on current row - reset to the first block of that row of blocks
            // ie rows1-3, first block index is 0, rows4-6, index is 3, rows7-9, index is 6

            // TODO: alternate way of setting block index
            long xDiv = x/3;
            double xFloor = Math.floor(xDiv);
            int currentBlockIndex = (int)xFloor*3;

            if (x < 3) {
                blockIndex = 0;
            } else { if (x < 6) {
                blockIndex = 3;
                } else {
                    blockIndex = 6;
                }
            }

            // append groups of 3 cell values into the 3 blocks that overlap with that row
            // increment valIndex (3x3 times) to go through 9 values in the current row
            // increment blockIndex (3 times) to go through 3 blocks which values of that row also belong to
            for(int y = 0; y < 3; y++) {
                for(int z = 0; z < 3; z++) {
                    int valIndex = y*3 + z;
                    ArrayList currentRow = rows.get(x);  // current row from rows list created above
                    Integer blockVal = (Integer)currentRow.get(valIndex);  // current value from the current row
                    ArrayList currentBlock = blocks.get(blockIndex);
                    currentBlock.add(blockVal);
                    // concatenated way of doing the above - just more abstruse
                    //blocks.get(blockIndex).add(rows.get(x).get(valIndex));
                }
                blockIndex += 1;
            }
        }

//        Log.i(TAG, "rowValues: "+rows);
//        Log.i(TAG, "colValues: "+cols);
//        Log.i(TAG, "blockValues: "+blocks);

        // check lists of rows/cols/blocks for errors return true if all checks pass else false
        return checkList(rows) && checkList(cols) && checkList(blocks);
    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // checkId = -1 if button is not checked - so this statement is for a checked button
            if(checkedId != -1) {
                // removing listeners for other two ragio groups before clearing to avoid some error
                rg2.setOnCheckedChangeListener(null);
                rg3.setOnCheckedChangeListener(null);
                // clear the other two radio groups checked buttons
                rg2.clearCheck();
                rg3.clearCheck();
                // reset the listeners for the other two radio groups after buttons cleared
                rg2.setOnCheckedChangeListener(listener2);
                rg3.setOnCheckedChangeListener(listener3);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId != -1) {
                rg1.setOnCheckedChangeListener(null);
                rg3.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg3.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
                rg3.setOnCheckedChangeListener(listener3);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener3 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId != -1) {
                rg1.setOnCheckedChangeListener(null);
                rg2.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg2.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
                rg2.setOnCheckedChangeListener(listener2);
            }
        }
    };

    @Nullable private CustomRadioButton checkRBs(CustomRadioButton[] radioButtons) {
        // iterate through array to check if any button is checked, then return that item, else return null
        for(CustomRadioButton element : radioButtons) {
            if(element.isChecked()) {
                return element;
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        // set checkbutton and corresponding onclicklistener
        final Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGrid()) {
                    // correct solution
                    Toast gameWin = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG);
                    gameWin.show();
                } else {
                    // incorrect solution
                    Toast gridErrors = Toast.makeText(getApplicationContext(), "Check for Errors.", Toast.LENGTH_SHORT);
                    gridErrors.show();
                }
            }
        });

        // set variables for rows in relative layout to inflate views into
        TableRow tableRow1 = findViewById(R.id.Row1);
        TableRow tableRow2 = findViewById(R.id.Row2);
        TableRow tableRow3 = findViewById(R.id.Row3);
        TableRow tableRow4 = findViewById(R.id.Row4);
        TableRow tableRow5 = findViewById(R.id.Row5);
        TableRow tableRow6 = findViewById(R.id.Row6);
        TableRow tableRow7 = findViewById(R.id.Row7);
        TableRow tableRow8 = findViewById(R.id.Row8);
        TableRow tableRow9 = findViewById(R.id.Row9);

        // array of all the rows
        TableRow[] rows = {
                tableRow1, tableRow2, tableRow3, tableRow4, tableRow5,
                tableRow6, tableRow7, tableRow8, tableRow9
        };

        // radiobuttons
        CustomRadioButton rb1 = findViewById(R.id.Set1);
        CustomRadioButton rb2 = findViewById(R.id.Set2);
        CustomRadioButton rb3 = findViewById(R.id.Set3);
        CustomRadioButton rb4 = findViewById(R.id.Set4);
        CustomRadioButton rb5 = findViewById(R.id.Set5);
        CustomRadioButton rb6 = findViewById(R.id.Set6);
        CustomRadioButton rb7 = findViewById(R.id.Set7);
        CustomRadioButton rb8 = findViewById(R.id.Set8);
        CustomRadioButton rb9 = findViewById(R.id.Set9);

        final CustomRadioButton[] radioButtons = {
                rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9
        };

        final ToggleButton pencilButton = findViewById(R.id.pencilButton);
        final ToggleButton eraserButton = findViewById(R.id.eraserButton);

        // turn off eraser button if radio button is clicked
        for(final CustomRadioButton element: radioButtons) {
            element.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eraserButton.setChecked(false);
                }
            });
        }

        // turn off radio buttons if eraser is clicked
        eraserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg1.clearCheck();
                rg2.clearCheck();
                rg3.clearCheck();
            }
        });

        // get the puzzle number to set associated starting values
        int puzzle = getIntent().getIntExtra("puzzle number", 1) - 1;

        final SudokuCell cellTest = findViewById(R.id.sudokuCell);
        cellTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomRadioButton activeRB = checkRBs(radioButtons);
                if (activeRB != null) {  // only proceed if a radiobutton is on
                    // get the radiobutton integer as a string to change values of cell
                    int pos = Arrays.asList(radioButtons).indexOf(activeRB)+1;  // 1-9 - index is 1 less
                    String integerString = Integer.toString(pos);
                    if (pencilButton.isChecked()) {  // for pencil on
                        cellTest.editPencilValues(pos);
                        cellTest.invalidate();
                    } else {
                        // if cellbutton contains that integer already remove it
                        if (cellTest.getValue().equals(integerString)) {
                            cellTest.setValue("");
                            cellTest.setShowPencilValues(true);
                            cellTest.invalidate();
                            Log.i(TAG, "test(off) value: "+cellTest.getValue());
                        } else {
                            cellTest.setValue(integerString);
                            // set button checked so it will redraw the integer
                            cellTest.setShowPencilValues(false);
                            cellTest.invalidate();
                            Log.i(TAG, "test(on) value: "+cellTest.getValue());
                        }
                    }
                }
            }
        });

        // convert 2dp to px for the grid column margins - used outside loop so only 1 call needed
        int marginPx = dpToPx(2);

        // made an inflater instance out of loop so we dont make 81 of them
        LayoutInflater layoutInflater = getLayoutInflater();

        // TODO: KEEP IN ONCREATE - HAD IN ONRESUME AND IT CREATED MORE BUTTONS AFTER PHONE WENT TO SLEEP AND RESUMED
        // insert 9 cells (relative layouts) into each row in the tablelayout
        for(int i = 0; i < 9; i++){
            TableRow row = rows[i];
            for(int h = 0; h < 9; h++){
                final int index = i * 9 + h + 1;
                // set starting values for cells
                CharSequence startValues = easyPuzzles[puzzle];
                char StartValue = startValues.charAt(index-1);
                // value of 0 means no starting value
                if (StartValue != '0') {
                    CharSequence cellValue = " "+StartValue+" ";  // setText wont accept char - spaces to convert
                    // inflate a single textview only for starting values as interaction is not needed
                    View sudokuCell = layoutInflater.inflate(R.layout.starting_value, row, false);
                    sudokuCell.setTag(index);
                    TextView startValueCell = sudokuCell.findViewById(R.id.startingValue);
                    startValueCell.setText(cellValue);
                    startValueCell.setTextColor(Color.BLUE);
                    row.addView(sudokuCell);

                } else {  // inflate the more bulky pencil values xml instead for changeable cells
                    //ViewHolder holder = new ViewHolder();
                    //TODO: test a blank custom view see how long it takes to load - if still slow then no need
                    // to try fix load speed this way...
                    View sudokuCell = layoutInflater.inflate(R.layout.pencil_values, row, false);

                    // row.addView(sudokuCell);
                    sudokuCell.setTag(index);

                    // declaring togglebutton variable for the cell button
                    final ToggleButton cellButton = sudokuCell.findViewById(R.id.sudokuButton);

                    // declaring textview variables for pencil values
                    TextView tv1 = sudokuCell.findViewById(R.id.pv1);
                    TextView tv2 = sudokuCell.findViewById(R.id.pv2);
                    TextView tv3 = sudokuCell.findViewById(R.id.pv3);
                    TextView tv4 = sudokuCell.findViewById(R.id.pv4);
                    TextView tv5 = sudokuCell.findViewById(R.id.pv5);
                    TextView tv6 = sudokuCell.findViewById(R.id.pv6);
                    TextView tv7 = sudokuCell.findViewById(R.id.pv7);
                    TextView tv8 = sudokuCell.findViewById(R.id.pv8);
                    TextView tv9 = sudokuCell.findViewById(R.id.pv9);

                    final TextView[] textViews = {
                            tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9
                    };

                    //TODO: how to make only one onclicklistener and set to all buttons instead?
//                    cellButton.setOnClickListener(tbListener);

                    // set a new onclicklistener for each of the changeable cell buttons
                    cellButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // these are cells with no starting value therefore they may be changed
                            // if no buttons are active and cell is clicked..do nothing or rest of code will give bugs
                            if(!eraserButton.isChecked() && checkRBs(radioButtons) == null && !pencilButton.isChecked()) {
                                // negate auto toggle onclick if nothing else is active
                                cellButton.toggle();
                            } else {  // if only pencil button is active do nothing on click
                                if (!eraserButton.isChecked() && checkRBs(radioButtons) == null && pencilButton.isChecked()) {
                                    cellButton.toggle();
                                }
                            }
                            // if eraser is active no radiobuttons can be active, so check and process eraser first
                            String emptybutton = getResources().getString(R.string.emptybutton);
                            if(eraserButton.isChecked()) {
                                if(pencilButton.isChecked()) {
                                    // erase all pencil values
                                    for (TextView element : textViews) {
                                        if(element.getCurrentTextColor() == Color.BLACK) {  // for set value
                                            element.setTextColor(Color.TRANSPARENT);
                                        }
                                    }
                                    cellButton.toggle();  // negate auto toggle
                                } else {
                                    // erase cell value and show pencil values
                                    cellButton.setTextOn(emptybutton);
                                    cellButton.setChecked(false);
                                }
                            } // if eraser inactive then we can move on to check if a radio button is active

                            CustomRadioButton activeRB = checkRBs(radioButtons);
                            if (activeRB != null) {  // only proceed if a radiobutton is on
                                if (!pencilButton.isChecked()) {  // if pencil button is not on...change cell value
                                    // set the clicked cells value to the corresponding radiobutton integer
                                    int pos = Arrays.asList(radioButtons).indexOf(activeRB)+1;  // 1-9 - index is 1 less
                                    String integerString = Integer.toString(pos);
                                    // if cellbutton contains that integer already remove it
                                    if (cellButton.getTextOn() == integerString) {
                                        cellButton.setTextOn(emptybutton);
                                        cellButton.setChecked(false);
                                    } else {
                                        cellButton.setTextOn(integerString);
                                        // set button checked so it will redraw the integer
                                        cellButton.setChecked(true);
                                    }
                                } else {  // if pencil button is on change pencil values
                                    int pos = Arrays.asList(radioButtons).indexOf(activeRB)+1;  // 1-9 - index is 1 less
                                    // textview object of the corresponding integer to the active radiobutton
                                    TextView pencilview = Arrays.asList(textViews).get(pos-1);
                                    if (pencilview.getCurrentTextColor() == 0) {  // textview is transparent (not set)
                                        pencilview.setTextColor(Color.BLACK);  // set colour to black
                                    } else {  // textview is active (visible colour)
                                        pencilview.setTextColor(Color.TRANSPARENT);  // set to transparent
                                    }
                                    // dont turn the button on if pencil used as the text views only visible when off
                                    if(cellButton.getTextOn() == emptybutton) {
                                        cellButton.setChecked(false);
                                    } else {  // TODO: show/flash the pencil values if a value is set in the cell/??
                                        cellButton.setChecked(true);
                                    }
                                }
                            }
                        }
                    });
                    // add cell to table row after all processing is done to children before moving on to next cell
                    row.addView(sudokuCell);
                }
            }
        }



        // populate cells array with all 81 cell objects (constraint layouts @pencil_values)
        for(int i = 0; i <9; i++) {
            // iterate through all 9 rows
            TableRow tbRow = rows[i];
            for(int h=0; h < 9; h++) {
                // current position index in grid 0-80
                int index = i * 9 + h;
                // check if object ConstraintLayout (changeable values) or TextView (start value)
//                if(tbRow.getChildAt(h) instanceof AppCompatTextView) {
//                    AppCompatTextView cell = (AppCompatTextView)tbRow.getChildAt(h);
//                    cells.add(index, cell);
//                    //TODO: Change cells and code here to allow for change on start values xml to not be constraint layout
//                }
                // iterate through all 9 cells
                ConstraintLayout cell = (ConstraintLayout)tbRow.getChildAt(h);

                cells.add(index, cell);
                //setting margins for cells to line them up with background image - have arranged them in table rows
                // so have to individually set margins on cells to get a column margin
                List<Integer> cols3_6 = Arrays.asList(3,6,12,15,21,24,30,33,39,42,48,51,57,60,66,69,75,78); //rightmargin
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)cell.getLayoutParams();
                if(cols3_6.contains(index + 1)) {
                    params.rightMargin = marginPx;
                }

            }
        }

        Log.i(TAG, "cells-"+cells);

    }

    private ToggleButton.OnClickListener tbListener = new ToggleButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("cellButton clicked", "test-" + v);
        }
    };

    // called when activity becomes visible to user
    @Override
    protected void onStart()  {
        super.onStart();
    }

    // called when user interaction occurs
    @Override
    protected void onResume() {
        super.onResume();

        rg1 = findViewById(R.id.RadioGroup1);
        rg2 = findViewById(R.id.RadioGroup2);
        rg3 = findViewById(R.id.RadioGroup3);

        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);
        rg3.setOnCheckedChangeListener(listener3);
        
    }
}
