package com.sarltokyo.pull_load_example3.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by osabe on 15/06/30.
 */
public class SecondFragment extends Fragment {
    public static final String TAG = SecondFragment.class.getSimpleName();

    public SecondFragment() {
    }

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }
        return inflater.inflate(R.layout.second_fragment, container, false);
    }
}
