package com.example.lamelameo.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private String TAG = "GameActivity";

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

    private List<SudokuCell> cells = new ArrayList<>();

    // dptopx that accepts a floating point value input and context outputs an int with units of pixels
    private int dpToPx(float dp, Context context) {
        float screenDensity = getResources().getDisplayMetrics().density;
        long pixels = Math.round(dp * screenDensity); // rounds up/down around 0.5
        //TODO: can change to this one line instead...?
//        int pix = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
        return (int) pixels;
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

    private ArrayList<ArrayList<SudokuCell>> rowValues;
    private ArrayList<ArrayList<SudokuCell>> colValues;
    private ArrayList<ArrayList<SudokuCell>> blockValues;

    private void createLists() {
        rowValues = new ArrayList<>();
        colValues = new ArrayList<>();
        blockValues = new ArrayList<>();
        for (int i=0; i<9; i++) {
            rowValues.add(new ArrayList<SudokuCell>());
            colValues.add(new ArrayList<SudokuCell>());
            blockValues.add(new ArrayList<SudokuCell>());
        }
    }

    private boolean checkList(ArrayList<ArrayList<SudokuCell>> numList) {
        // checks all groups of a certain type (row/col/block) to see if all integers, 1-9, are present in each
        // returns false if an integer is not found exactly once in a group (not found or more than one found)

        String[] numbers = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };

        // check each group of 9 cells
        for(ArrayList<SudokuCell> array : numList) {
            for(String num : numbers) {  // check groups each have integers 1-9
                int check = 0;  // initialise counter to identify errors
                for (int i=0; i<9; i++) {  // check each cell in the group for the integer
                    SudokuCell cell = array.get(i);
                    if(cell.getValue().equals(num)) {  // if integer is found in cell increment counter
                        check += 1;
                    }  // else continue to check values
                }
                if (check !=1) {  // if more/less than 1 of an integer is found in a group, then the grid contains error(s)
                    return false;
                }  // else continue checking
            }
        } return true;  // return true if all checks are passed - is a potentially correct solution if other groups pass
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final GridLayout gridLayout = findViewById(R.id.gridLayout);

        final Button checkButton = findViewById(R.id.checkButton2);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if all rows/columns/blocks have errors
                //TODO: order of these could matter? what group more likely to have errors -> should be first
                if(checkList(rowValues) && checkList(colValues) && checkList(blockValues)) {  // correct solution
                    Toast gameWin = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG);
                    gameWin.show();
                } else {  // incorrect solution
                    Toast gridErrors = Toast.makeText(getApplicationContext(), "Check for Errors.", Toast.LENGTH_SHORT);
                    gridErrors.show();
                }
            }
        });

        // radiogroups and listeners
        rg1 = findViewById(R.id.RadioGroup1);
        rg2 = findViewById(R.id.RadioGroup2);
        rg3 = findViewById(R.id.RadioGroup3);
        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);
        rg3.setOnCheckedChangeListener(listener3);
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
        // array of radiobuttons used for checking active button across all 3 groups at once
        final CustomRadioButton[] radioButtons = {
                rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9
        };
        // pencil and eraser buttons
        final ToggleButton pencilButton = findViewById(R.id.pencilButton2);
        final ToggleButton eraserButton = findViewById(R.id.eraserButton2);

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

        // convert dp to px for the grid column margins - used outside loop so only 1 call needed
        // each cell has 1dp border, so middle margins must be 2dp and edge margins 3dp to total 4dp thick grid borders
        final int margin2dp = dpToPx(2, this);
        final int margin3dp = dpToPx(3, this);
        // margin between grid and edge of screen
        final int margin4dp = dpToPx(4, this);
        int wrap = ViewGroup.LayoutParams.WRAP_CONTENT;

        ViewGroup.LayoutParams gridMargins = gridLayout.getLayoutParams();
        gridMargins.height = wrap;
        gridMargins.width = wrap;
        ((ViewGroup.MarginLayoutParams)gridMargins).setMargins(margin4dp, margin4dp, margin4dp, 0);

        // determine largest cell size possible given the devices width (pixels)
        float screenSize = getResources().getDisplayMetrics().widthPixels;
        // need to account for margins associated with the grid and cells
        int marginsPx = (margin2dp + margin3dp + margin4dp)*2;
        // rounds down to nearest integer, as we want a whole number less than the maximum
        // 4dp x 2 margins between side of grid and screen, 3dp x 2 + 2dp x 2 for margins between grid blocks = 18dp
        int maxCellSize = (int)(screenSize - marginsPx)/9;
        ViewGroup.LayoutParams layoutSize = new ViewGroup.LayoutParams(maxCellSize, maxCellSize);

        // get the puzzle number to set associated starting values
        int puzzle = getIntent().getIntExtra("puzzle number", 1) - 1;

        // create lists for cells to be added to upon creation
        createLists();

        // insert 9 cells into each row in the grid
        for(int i = 0; i < 9; i++){
            for(int h = 0; h < 9; h++){
                int index = i * 9 + h;
//                Log.i("gridLayoutTest", "index: "+index);

                // value of 0 means no starting value
                final SudokuCell sudokuCell = new SudokuCell(this);

                // add cell object to appropriate lists for future access
                rowValues.get(i).add(sudokuCell);
                colValues.get(h).add(sudokuCell);
                // blocks are in a 3x3 grid which overlap multiple rows and columns...
                // cell row/col index -> block row/col index: (0,1,2)-> 0 | (3,4,5)-> 1 | (6,7,8)-> 2
                double xBlockIndex = Math.floor(h/3f);
                double yBlockIndex = Math.floor(i/3f);
                int blockIndex = (int)(xBlockIndex + 3*yBlockIndex);  // block index = 0-8 from left-right, top-bottom
                blockValues.get(blockIndex).add(sudokuCell);

                // set cells size to 37 x 37 dp
                sudokuCell.setLayoutParams(layoutSize);

                // set starting values for cells
                char StartValue = easyPuzzles[puzzle].charAt(index);

                // set starting values and add cells to grid
                if (StartValue != '0') {
                    sudokuCell.setValue(StartValue+"");
                    sudokuCell.setTextColor(Color.BLUE);
//                    sudokuCell.setNotEditable();
                    gridLayout.addView(sudokuCell, index);

                } else {  // editable values with no starting value
                    //TODO: create one onclicklistener and use for all buttons instead
                    //  need class variables for eraserbutton/pencilbutton/RBs so the method can access them
                    //  - or put in SudokuCell class and make a variable in this class that tells what button state active
                    //  can access by passing context to cell and then use in onclicklistener that is set on instantiation

                    // set a new onclicklistener for each of the changeable cell buttons
                    sudokuCell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            if(!eraserButton.isChecked() && checkRBs(radioButtons) == null && !pencilButton.isChecked()) {
//                                //TODO: no buttons checked - do something?
//                            }

                            // if eraser is active no radiobuttons can be active, so process eraser clicks first
                            String emptybutton = "";
                            if(eraserButton.isChecked()) {
                                if(pencilButton.isChecked()) {
                                    // erase all pencil values
                                    sudokuCell.clearPencilValues();
                                } else {
                                    // erase cell value and show pencil values
                                    sudokuCell.setValue(emptybutton);
                                }
                            } // if eraser inactive then we can move on to check if a radio button is active
                            CustomRadioButton activeRB = checkRBs(radioButtons);
                            if (activeRB != null) {  // only proceed if a radiobutton is on
                                int pos = Arrays.asList(radioButtons).indexOf(activeRB)+1;  // 1-9 - index is 1 less
                                if (!pencilButton.isChecked()) {
                                    // if pencil button is not on...change cell value to corresponding integer
                                    String integerString = Integer.toString(pos);
                                    Log.i(TAG, "RB string: "+integerString);
                                    // if cellbutton contains that integer already remove it
                                    if (sudokuCell.getValue().equals(integerString)) {
                                        sudokuCell.setValue(emptybutton);
                                    } else {  // add the integer to cell
                                        sudokuCell.setValue(integerString);
                                    }
                                } else {  // if pencil button is on change pencil values
                                    sudokuCell.editPencilValues(pos);
                                }
                            }
                        }
                    });
                    // add cell to table row after all processing is done to children before moving on to next cell
                    gridLayout.addView(sudokuCell, index);
                }

                // Setting margins for cells to line them up with background image so blocks have a thicker border
                // 2dp for middle margins as cell adds 2 x 1dp border, 3dp for edges as cells add 1 x 1dp border
                ViewGroup.MarginLayoutParams cellMargin = (ViewGroup.MarginLayoutParams)gridLayout.getChildAt(index).getLayoutParams();
                if (h==2 || h==5) {  // add right margin for columns 3,6,9
                    cellMargin.rightMargin = margin2dp;
                }
                if (h==8) {
                    cellMargin.rightMargin = margin3dp;
                }
                if (h==0) {  // add left margin for column 1
                    cellMargin.leftMargin = margin3dp;
                }
                if (i==2 || i==5) {  // add bottom margin for rows 3,6,9
                    cellMargin.bottomMargin = margin2dp;
                }
                if (i==8) {
                    cellMargin.bottomMargin = margin3dp;
                }
                if (i==0) {  // add top margin for column 1
                    cellMargin.topMargin = margin3dp;
                }
            }
        }
    }

}
