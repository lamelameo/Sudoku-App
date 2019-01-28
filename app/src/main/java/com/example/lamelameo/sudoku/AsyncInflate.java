package com.example.lamelameo.sudoku;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class AsyncInflate extends AsyncTask {

    private ArrayList<View> cells;

    @Override
    protected Object doInBackground(Object[] objects) {

        LayoutInflater inflater = (LayoutInflater)objects[0];
        ViewGroup parentView = (ViewGroup)objects[1];
        @LayoutRes int view = (int)objects[2];

        for(int x=0; x<9; x++) {
            View cell = inflater.inflate(view, parentView, false);
//            cells.add(cell);
        }

        LayoutInflater inflater2 = (LayoutInflater)objects[3];
        ViewGroup parentView2 = (ViewGroup)objects[4];
        @LayoutRes int view2 = (int)objects[5];
        for(int x=0; x<9; x++) {
            View cell = inflater2.inflate(view2, parentView2, false);
//            cells.add(cell);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.i("asyncTask", "onPostExecute: "+cells);
    }
}
