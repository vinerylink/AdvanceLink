package com.vinerylink.al.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vinerylink.al.R;
import com.vinerylink.al.listeners.SpeedyQuickReturnListViewOnScrollListener;
import com.vinerylink.al.utils.QuickReturnUtils;

import org.lucasr.twowayview.ClickItemTouchListener;
import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.TwoWayView;
import org.lucasr.twowayview.widget.DividerItemDecoration;


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
        return new SimpleAdapter(context, view, layoutId, mValues);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEmptyText(R.string.whats_happening);

        mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

        updateState(SCROLL_STATE_IDLE);

        if (null == mContentView) {
            return;
        }

        SAMPLE_VALUES = getResources().getStringArray(R.array.countries);
        reloadAdapterData();

        mContentView.setHasFixedSize(true);
        mContentView.setLongClickable(true);

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

        final SpeedyQuickReturnListViewOnScrollListener scrollListener = getScrollListener();
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
        private String[] mLabels;

        public static class SimpleViewHolder extends RecyclerView.ViewHolder {
            public final TextView title;

            public SimpleViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.item_tv);
            }
        }

        public SimpleAdapter(Context context, TwoWayView recyclerView, int layoutId, String[] values) {
            mContext = context;
            mContentView = recyclerView;
//            mLayoutId = layoutId;
            mLabels = values;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.google_plus_list_item, parent, false);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.title.setText(mLabels[position % mLabels.length]);

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
            return null == mLabels ? 0 : mLabels.length;
        }
    }


    private final static String[] EMPTY_VALUES = new String[0];
    private static String[] SAMPLE_VALUES;
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
        mContentView.setAdapter(getAdapter(getActivity(), mContentView, getView().getId()));
        setEmptyViewShow(mValues.length <= 0);
    }
}