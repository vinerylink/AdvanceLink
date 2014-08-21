package com.vinerylink.al.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.vinerylink.al.R;
import com.vinerylink.al.enums.QuickReturnType;
import com.vinerylink.al.listeners.SpeedyQuickReturnListViewOnScrollListener;
import com.vinerylink.al.utils.QuickReturnUtils;

import org.lucasr.twowayview.ClickItemTouchListener;
import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.TwoWayView;
import org.lucasr.twowayview.widget.DividerItemDecoration;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class QuickReturnRecyclerViewFragment extends AbstractGooglePlusFragment<TwoWayView> {
    private static final String TAG = QuickReturnRecyclerViewFragment.class.getSimpleName();

    // region Constructors
//    public static QuickReturnGooglePlusFragment newInstance() {
//        QuickReturnGooglePlusFragment fragment = new QuickReturnGooglePlusFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static QuickReturnRecyclerViewFragment newInstance(int layoutId) {
        QuickReturnRecyclerViewFragment fragment = new QuickReturnRecyclerViewFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_ID, layoutId);
        fragment.setArguments(args);

        return fragment;
    }

    // region Member Variables
    private String[] mValues;
    private Toast mToast;

    protected RecyclerView.Adapter getAdapter(Context context, TwoWayView view, int layoutId) {

        mValues = getResources().getStringArray(R.array.countries);

        return new SimpleAdapter(context, view, layoutId);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(getAdapter());
//        animAdapter.setAbsListView(getListView());

//        mListView.setAdapter(animAdapter);
//        mListView.setAdapter(adapter);

//        mListView.addFooterView(new View(getActivity()), null, false);
//        mListView.addHeaderView(new View(getActivity()), null, false);

//        int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height3);
//        int headerTranslation = -(headerHeight*2) + QuickReturnUtils.getActionBarHeight(getActivity());
//        int footerTranslation = -(headerHeight*2) + QuickReturnUtils.getActionBarHeight(getActivity());

//        mListView.setOnScrollListener(new QuickReturnListViewOnScrollListener(QuickReturnType.BOTH,
//                mQuickReturnHeaderTextView, headerTranslation, mQuickReturnFooterLinearLayout, -footerTranslation));

        ArrayList<View> headerViews = new ArrayList<View>();
        headerViews.add(getActionBarView());

        ArrayList<View> footerViews = new ArrayList<View>();
        footerViews.add(mQuickReturnFooterTextView);
        footerViews.add(mQuickReturnFooterImageView);

        final SpeedyQuickReturnListViewOnScrollListener scrollListener = new SpeedyQuickReturnListViewOnScrollListener(getActivity(), QuickReturnType.CUSTOM, headerViews, footerViews);
        scrollListener.setSlideHeaderUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up));
        scrollListener.setSlideHeaderDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down));
        scrollListener.setSlideFooterUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up));
        scrollListener.setSlideFooterDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down));

//        mListView.setOnScrollListener(scrollListener);

        final Activity activity = getActivity();

        mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

        mContentView.setHasFixedSize(true);
        mContentView.setLongClickable(true);

        updateState(SCROLL_STATE_IDLE);

        ClickItemTouchListener clickListener = ClickItemTouchListener.addTo(mContentView);

        clickListener.setOnItemClickListener(new ClickItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                mToast.setText("Item clicked: " + position);
                mToast.show();
            }
        });

        clickListener.setOnItemLongClickListener(new ClickItemTouchListener.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View child, int position, long id) {
                mToast.setText("Item long pressed: " + position);
                mToast.show();
                return true;
            }
        });

        mContentView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(int scrollState) {
                updateState(scrollState);
            }

            @Override
            public void onScrolled(int i, int i2) {
//                mPositionText.setText("First: " + mContentView.getFirstVisiblePosition());
//                mCountText.setText("Count: " + mContentView.getChildCount());
                int first = mContentView.getFirstVisiblePosition();
                int count = mContentView.getChildCount();
                scrollListener.onScroll(QuickReturnUtils.getScrollY(mContentView), first, count);
            }
        });

        final Drawable divider = getResources().getDrawable(android.R.drawable.divider_horizontal_dark);
        mContentView.addItemDecoration(new DividerItemDecoration(divider));

        mContentView.setAdapter(getAdapter(activity, mContentView, getView().getId()));
    }

    private void updateState(int scrollState) {
        String stateName = "Undefined";
        switch(scrollState) {
            case SCROLL_STATE_IDLE:
                stateName = "Idle";
                break;

            case SCROLL_STATE_DRAGGING:
                stateName = "Dragging";
                break;

            case SCROLL_STATE_SETTLING:
                stateName = "Flinging";
                break;
        }

//        mStateText.setText(stateName);
        Log.v(TAG, "updateState, state " + stateName);
    }


    public static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
        private final Context mContext;
        private final TwoWayView mContentView;
//        private final int mLayoutId;

        public static class SimpleViewHolder extends RecyclerView.ViewHolder {
            public final TextView title;

            public SimpleViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.item_tv);
            }
        }

        public SimpleAdapter(Context context, TwoWayView recyclerView, int layoutId) {
            mContext = context;
            mContentView = recyclerView;
//            mLayoutId = layoutId;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.google_plus_list_item, parent, false);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.title.setText(String.valueOf(position));

            boolean isVertical = (mContentView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);
            final View itemView = holder.itemView;

//            if (mLayoutId == R.layout.layout_staggered_grid) {
//                final int id;
//                if (position % 3 == 0) {
//                    id = R.dimen.staggered_child_medium;
//                } else if (position % 5 == 0) {
//                    id = R.dimen.staggered_child_large;
//                } else if (position % 7 == 0) {
//                    id = R.dimen.staggered_child_xlarge;
//                } else {
//                    id = R.dimen.staggered_child_small;
//                }
//
//                final int span;
//                if (position == 2) {
//                    span = 2;
//                } else {
//                    span = 1;
//                }
//
//                final int size = mContext.getResources().getDimensionPixelSize(id);
//
//                final StaggeredGridLayoutManager.LayoutParams lp =
//                        (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
//                if (!isVertical && lp.width != id) {
//                    lp.span = span;
//                    lp.width = size;
//                    itemView.setLayoutParams(lp);
//                } else if (isVertical && lp.height != id) {
//                    lp.span = span;
//                    lp.height = size;
//                    itemView.setLayoutParams(lp);
//                }
//            } else if (mLayoutId == R.layout.layout_spannable_grid) {
//                final SpannableGridLayoutManager.LayoutParams lp =
//                        (SpannableGridLayoutManager.LayoutParams) itemView.getLayoutParams();
//
//                final int span1 = (position == 0 || position == 3 ? 2 : 1);
//                final int span2 = (position == 0 ? 2 : (position == 3 ? 3 : 1));
//
//                final int colSpan = (isVertical ? span2 : span1);
//                final int rowSpan = (isVertical ? span1 : span2);
//
//                if (lp.rowSpan != rowSpan || lp.colSpan != colSpan) {
//                    lp.rowSpan = rowSpan;
//                    lp.colSpan = colSpan;
//                    itemView.setLayoutParams(lp);
//                }
//            }
        }

        @Override
        public void onViewAttachedToWindow(SimpleViewHolder holder) {
            super.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(SimpleViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

}