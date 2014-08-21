package com.vinerylink.al.activities;

import android.os.Bundle;

import com.vinerylink.al.R;
import com.vinerylink.al.fragments.QuickReturnRecyclerViewFragment;

import butterknife.ButterKnife;


public class QuickReturnGooglePlusActivity extends QuickReturnBaseActivity {

    // region Member Variables
    // endregion

    // region Listeners
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_return_google_plus);
        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, QuickReturnRecyclerViewFragment.newInstance(R.layout.fragment_quick_return_two_way_view))
                    .commit();
        }

        setDisplayHomeAsUpEnabled(false);
    }
    // endregion


}
