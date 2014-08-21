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
    public static QuickReturnGooglePlusFragment newInstance() {
        QuickReturnGooglePlusFragment fragment = new QuickReturnGooglePlusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // region Member Variables
    private String[] mValues;
    private AnimationAdapter getAdapter() {
        mValues = getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.google_plus_list_item, R.id.item_tv, mValues);

        AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(adapter);
        animAdapter.setAbsListView(mContentView);

        return animAdapter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null == mContentView) {
            return;
        }

        mContentView.setAdapter(getAdapter());
        mContentView.addFooterView(new View(getActivity()), null, false);
        mContentView.addHeaderView(new View(getActivity()), null, false);
        mContentView.setOnScrollListener(getScrollListener());
    }
}