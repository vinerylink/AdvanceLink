package com.vinerylink.al.listeners;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;

import com.vinerylink.al.R;
import com.vinerylink.al.enums.QuickReturnType;
import com.vinerylink.al.utils.QuickReturnUtils;

import java.util.ArrayList;

/**
 * Created by etiennelawlor on 7/14/14.
 */
public class SpeedyQuickReturnListViewOnScrollListener implements AbsListView.OnScrollListener {

    // region Member Variables
    private View mHeader;
    private View mFooter;
    private ArrayList<View> mHeaderViews;
    private ArrayList<View> mFooterViews;
    private int mPrevScrollY = 0;
    private QuickReturnType mQuickReturnType;
    private Context mContext;
    private Animation mSlideHeaderUpAnimation;
    private Animation mSlideHeaderDownAnimation;
    private Animation mSlideFooterUpAnimation;
    private Animation mSlideFooterDownAnimation;
    // endregion

    // region Constructors
    public SpeedyQuickReturnListViewOnScrollListener(Context context, QuickReturnType quickReturnType, View headerView, View footerView){
        mContext = context;
        mQuickReturnType = quickReturnType;

        mSlideHeaderUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anticipate_slide_header_up);
        mSlideHeaderDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.overshoot_slide_header_down);
        mSlideFooterUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.overshoot_slide_footer_up);
        mSlideFooterDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anticipate_slide_footer_down);


        mHeader =  headerView;
        mFooter =  footerView;
    }

    public SpeedyQuickReturnListViewOnScrollListener(Context context, QuickReturnType quickReturnType, ArrayList<View> headerViews, ArrayList<View> footerViews) {
        mContext = context;
        mQuickReturnType = quickReturnType;

        mHeaderViews = headerViews;
        mFooterViews = footerViews;

        mSlideHeaderUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anticipate_slide_header_up);
        mSlideHeaderDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.overshoot_slide_header_down);
        mSlideFooterUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.overshoot_slide_footer_up);
        mSlideFooterDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anticipate_slide_footer_down);
    }
    // endregion

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    private int mPrevFirst = 0;
    private int mPrevCount = 0;
    @Override
    public void onScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mPrevFirst == firstVisibleItem && mPrevCount == visibleItemCount) {
            return;
        }

        if (mPrevFirst + mPrevCount == firstVisibleItem + visibleItemCount) {
            return;
        }

        int scrollY = QuickReturnUtils.getScrollY(listview);
        int diff = mPrevScrollY - scrollY;
        mPrevFirst = firstVisibleItem;
        mPrevCount = visibleItemCount;

//        Log.e(TAG, "onScroll, firstVisibleItem = " + firstVisibleItem + ", visibleItemCount = " +
//                visibleItemCount + ", totalItemCount = " + totalItemCount);
//        Log.i(TAG, "onScroll, diff = " + diff + ", mQuickReturnType = " + mQuickReturnType);
        if(diff>0){ // scrolling up
            switch (mQuickReturnType){
                case HEADER:
                    if(mHeader.getVisibility() == View.GONE){
                        mHeader.setVisibility(View.VISIBLE);
                        mHeader.startAnimation(mSlideHeaderDownAnimation);
                    }
                    break;
                case FOOTER:
                    if(mFooter.getVisibility() == View.GONE){
                        mFooter.setVisibility(View.VISIBLE);
                        mFooter.startAnimation(mSlideFooterUpAnimation);
                    }
                    break;
                case BOTH:
                    if(mHeader.getVisibility() == View.GONE){
                        mHeader.setVisibility(View.VISIBLE);
                        mHeader.startAnimation(mSlideHeaderDownAnimation);
                    }

                    if(mFooter.getVisibility() == View.GONE){
                        mFooter.setVisibility(View.VISIBLE);
                        mFooter.startAnimation(mSlideFooterUpAnimation);
                    }
                    break;
                case CUSTOM:
                    if(mHeaderViews!=null){
                        for(View view : mHeaderViews){
                            if(view.getVisibility() == View.GONE){
                                view.setVisibility(View.VISIBLE);
                                view.startAnimation(mSlideHeaderDownAnimation);
                            }
                        }
                    }

                    if(mFooterViews!=null){
                        for(View view : mFooterViews){
                            if(view.getVisibility() == View.GONE){
                                view.setVisibility(View.VISIBLE);
//                                Log.i(TAG, "onScroll, diff = " + diff + ", animate footer to visible");
                                view.startAnimation(mSlideFooterUpAnimation);
                            }
                        }
                    }
                    break;
            }
        } else if(diff<0){ // scrolling down
            switch (mQuickReturnType){
                case HEADER:
                    if(mHeader.getVisibility() == View.VISIBLE){
                        mHeader.setVisibility(View.GONE);
                        mHeader.startAnimation(mSlideHeaderUpAnimation);
                    }
                    break;
                case FOOTER:
                    if(mFooter.getVisibility() == View.VISIBLE){
                        mFooter.setVisibility(View.GONE);
                        mFooter.startAnimation(mSlideFooterDownAnimation);
                    }
                    break;
                case BOTH:
                    if(mHeader.getVisibility() == View.VISIBLE){
                        mHeader.setVisibility(View.GONE);
                        mHeader.startAnimation(mSlideHeaderUpAnimation);
                    }

                    if(mFooter.getVisibility() == View.VISIBLE){
                        mFooter.setVisibility(View.GONE);
                        mFooter.startAnimation(mSlideFooterDownAnimation);
                    }
                    break;
                case CUSTOM:
                    if(mHeaderViews!=null){
                        for(View view : mHeaderViews){
                            if(view.getVisibility() == View.VISIBLE){
                                view.setVisibility(View.GONE);
                                view.startAnimation(mSlideHeaderUpAnimation);
                            }
                        }
                    }

                    if(mFooterViews!=null){
                        for(View view : mFooterViews){
                            if(view.getVisibility() == View.VISIBLE){
                                view.setVisibility(View.GONE);
//                                Log.i(TAG, "onScroll, diff = " + diff + ", animate footer to gone");
                                view.startAnimation(mSlideFooterDownAnimation);
                            }
                        }
                    }
                    break;
            }
        }

        mPrevScrollY = scrollY;
    }


    public void setSlideHeaderUpAnimation(Animation animation){
        mSlideHeaderUpAnimation = animation;
    }

    public void setSlideHeaderDownAnimation(Animation animation){
        mSlideHeaderDownAnimation = animation;
    }

    public void setSlideFooterUpAnimation(Animation animation){
        mSlideFooterUpAnimation = animation;
    }

    public void setSlideFooterDownAnimation(Animation animation){
        mSlideFooterDownAnimation = animation;
    }
}
