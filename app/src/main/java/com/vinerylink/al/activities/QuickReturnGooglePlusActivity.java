package com.vinerylink.al.activities;

import android.os.Bundle;

import com.vinerylink.al.R;
import com.vinerylink.al.fragments.QuickReturnGooglePlusFragment;

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
            getFragmentManager().beginTransaction()
                    .add(R.id.container, QuickReturnGooglePlusFragment.newInstance(R.layout.fragment_quick_return_google_plus))
                    .commit();
        }

        setDisplayHomeAsUpEnabled(false);
    }
    // endregion


}
