package com.vinerylink.al.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinerylink.al.R;
import com.vinerylink.al.enums.QuickReturnType;
import com.vinerylink.al.listeners.SpeedyQuickReturnListViewOnScrollListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by etiennelawlor on 6/23/14.
 */
abstract class AbstractGooglePlusFragment<V extends ViewGroup> extends ListFragment {
    private static final String TAG = AbstractGooglePlusFragment.class.getSimpleName();

    @InjectView(android.R.id.list)
    V mContentView;
    @InjectView(R.id.quick_return_footer_iv)
    ImageView mQuickReturnFooterImageView;
    @InjectView(R.id.quick_return_footer_tv)
    TextView mQuickReturnFooterTextView;
    // endregion

    //region Listeners
    @OnClick(R.id.quick_return_footer_tv)
    public void onRefreshButtonClicked(View v) {
        Log.v(TAG, "onRefreshButtonClicked done.");
    }

    @OnClick(R.id.quick_return_footer_iv)
    public void onAddButtonClicked(View v) {
        Log.v(TAG, "onAddButtonClicked done.");
    }
    //endregion

    public AbstractGooglePlusFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return_google_plus, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion

    // region Helper Methods
    public View getActionBarView() {
        Window window = getActivity().getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }
    // endregion

    // helper method begin
    protected SpeedyQuickReturnListViewOnScrollListener getScrollListener() {
        ArrayList<View> headerViews = new ArrayList<View>();
        headerViews.add(getActionBarView());

        ArrayList<View> footerViews = new ArrayList<View>();
        footerViews.add(mQuickReturnFooterTextView);
        footerViews.add(mQuickReturnFooterImageView);

        SpeedyQuickReturnListViewOnScrollListener scrollListener = new SpeedyQuickReturnListViewOnScrollListener(getActivity(), QuickReturnType.CUSTOM, headerViews, footerViews);
        scrollListener.setSlideHeaderUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up));
        scrollListener.setSlideHeaderDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down));
        scrollListener.setSlideFooterUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up));
        scrollListener.setSlideFooterDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down));
        return scrollListener;
    }
    // helper method end
}
