package com.vinerylink.al.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.vinerylink.al.R;

public class QuickReturnGooglePlusFragment extends AbstractGooglePlusFragment<ListView> {
    // region Constructors
    public static QuickReturnGooglePlusFragment newInstance(int layoutId) {
        QuickReturnGooglePlusFragment fragment = new QuickReturnGooglePlusFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_ID, layoutId);
        fragment.setArguments(args);

        return fragment;
    }

    // region Member Variables
    private final static String[] EMPTY_VALUES = new String[0];
    private static String[] SAMPLE_VALUES;
    private String[] mValues;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null == mContentView) {
            return;
        }

        SAMPLE_VALUES = getResources().getStringArray(R.array.countries);
        reloadAdapterData();

        mContentView.addFooterView(new View(getActivity()), null, false);
        mContentView.addHeaderView(new View(getActivity()), null, false);
        mContentView.setOnScrollListener(getScrollListener());

        setEmptyText(R.string.whats_happening);
    }

    private void resetAdapterData() {
        if (EMPTY_VALUES != mValues) {
            mValues = EMPTY_VALUES;
            updateAdapter();
        }
    }

    private void reloadAdapterData() {
        if (SAMPLE_VALUES != mValues) {
            mValues = SAMPLE_VALUES;
            updateAdapter();
        }
    }

    @Override
    public void onRefreshButtonClicked(View v) {
        resetAdapterData();
    }

    @Override
    public void onAddButtonClicked(View v) {
        reloadAdapterData();
    }

    private void updateAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.google_plus_list_item, R.id.item_tv, mValues);

        AnimationAdapter aniAdapter = new SwingBottomInAnimationAdapter(adapter);
        aniAdapter.setAbsListView(mContentView);
        mContentView.setAdapter(aniAdapter);
        setEmptyViewShow(mValues.length <= 0);
    }
}