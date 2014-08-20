package com.vinerylink.al.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.vinerylink.al.R;
import com.vinerylink.al.enums.QuickReturnType;
import com.vinerylink.al.listeners.SpeedyQuickReturnListViewOnScrollListener;

import org.lucasr.twowayview.ClickItemTouchListener;
import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.TwoWayView;
import org.lucasr.twowayview.widget.DividerItemDecoration;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;
import org.lucasr.twowayview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Created by etiennelawlor on 6/23/14.
 */
abstract class AbstractGooglePlusFragment<T> extends Fragment {
    private static final String TAG = AbstractGooglePlusFragment.class.getSimpleName();

//    @InjectView(android.R.id.list)
//    ListView mListView;
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
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_quick_return_google_plus, container, false);
//        ButterKnife.inject(this, view);
//        return view;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = getArguments().getInt(ARG_LAYOUT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(mLayoutId, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    abstract protected RecyclerView.Adapter getAdapter(Context context, TwoWayView view, int layoutId);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Activity activity = getActivity();

        mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

//        mRecyclerView = (TwoWayView) view.findViewById(android.R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);

//        mPositionText = (TextView) view.getRootView().findViewById(R.id.position);
//        mCountText = (TextView) view.getRootView().findViewById(R.id.count);

//        mStateText = (TextView) view.getRootView().findViewById(R.id.state);
        updateState(SCROLL_STATE_IDLE);

        ClickItemTouchListener clickListener = ClickItemTouchListener.addTo(mRecyclerView);

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

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(int scrollState) {
                updateState(scrollState);
            }

            @Override
            public void onScrolled(int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerView.getFirstVisiblePosition());
//                mCountText.setText("Count: " + mRecyclerView.getChildCount());
            }
        });

        final Drawable divider = getResources().getDrawable(R.drawable.divider);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(divider));

        mRecyclerView.setAdapter(getAdapter(activity, mRecyclerView, mLayoutId));

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

        SpeedyQuickReturnListViewOnScrollListener scrollListener = new SpeedyQuickReturnListViewOnScrollListener(getActivity(), QuickReturnType.CUSTOM, headerViews, footerViews);
        scrollListener.setSlideHeaderUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up));
        scrollListener.setSlideHeaderDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down));
        scrollListener.setSlideFooterUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up));
        scrollListener.setSlideFooterDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down));

//        mListView.setOnScrollListener(scrollListener);

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

    protected static final String ARG_LAYOUT_ID = "layout_id";
    @InjectView(android.R.id.list)
//    ListView mListView;
    TwoWayView mRecyclerView;
//    private TextView mPositionText;
//    private TextView mCountText;
//    private TextView mStateText;
    private Toast mToast;

    private int mLayoutId;

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

    public int getLayoutId() {
        return getArguments().getInt(ARG_LAYOUT_ID);
    }

    public static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
        private final Context mContext;
        private final TwoWayView mRecyclerView;
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
            mRecyclerView = recyclerView;
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

            boolean isVertical = (mRecyclerView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);
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
