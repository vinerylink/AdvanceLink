package com.vinerylink.al.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.vinerylink.al.R;

import org.lucasr.twowayview.TwoWayView;

public class QuickReturnGooglePlusFragment extends AbstractGooglePlusFragment<String> {
    // region Constructors
//    public static QuickReturnGooglePlusFragment newInstance() {
//        QuickReturnGooglePlusFragment fragment = new QuickReturnGooglePlusFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static QuickReturnGooglePlusFragment newInstance(int layoutId) {
        QuickReturnGooglePlusFragment fragment = new QuickReturnGooglePlusFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_ID, layoutId);
        fragment.setArguments(args);

        return fragment;
    }

    // region Member Variables
    private String[] mValues;

    protected RecyclerView.Adapter getAdapter(Context context, TwoWayView view, int layoutId) {

        mValues = getResources().getStringArray(R.array.countries);

        return new SimpleAdapter(context, view, layoutId);
    }
}