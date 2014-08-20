package com.vinerylink.al.fragments;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.vinerylink.al.R;

public class QuickReturnGooglePlusFragment extends AbstractGooglePlusFragment<String> {
    // region Constructors
    public static QuickReturnGooglePlusFragment newInstance() {
        QuickReturnGooglePlusFragment fragment = new QuickReturnGooglePlusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // region Member Variables
    private String[] mValues;

    protected ArrayAdapter<String> getAdapter() {

        mValues = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.google_plus_list_item, R.id.item_tv, mValues);

        return adapter;
    }
}