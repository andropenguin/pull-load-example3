package com.sarltokyo.pull_load_example3.app;

import android.os.Bundle;
        import android.support.v4.app.ListFragment;
        import android.support.v4.app.LoaderManager;
        import android.support.v4.content.Loader;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import com.costum.android.widget.LoadMoreListView;

        import java.util.List;

/**
 * Created by osabe on 15/06/30.
 */
public class CustomListFragment extends ListFragment
        implements LoadMoreListView.OnLoadMoreListener,
        LoaderManager.LoaderCallbacks<List<Entry>> {
    private CustomAdapter mCustomAdapter;
    private boolean mIsLoading = false;
    private int mCount = 0;

    private final static String TAG = CustomListFragment.class.getSimpleName();

    public CustomListFragment() {
    }

    public static CustomListFragment newInstance() {
        CustomListFragment fragment = new CustomListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        return inflater.inflate(R.layout.loadmore, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCustomAdapter = new CustomAdapter(getActivity());
        setListAdapter(mCustomAdapter);
        ((LoadMoreListView)getListView()).setOnLoadMoreListener(this);
        mIsLoading = true;
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onLoadMore() {
        Log.d(TAG, "mIsLoading = " + mIsLoading);
        // 既に読み込み中ならスキップ
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        getLoaderManager().initLoader(mCount, null, this);
    }

    @Override
    public Loader<List<Entry>> onCreateLoader(int id, Bundle args) {
        return new CustomLoader(getActivity(), mCount);
    }

    @Override
    public void onLoadFinished(Loader<List<Entry>> loader, List<Entry> data) {
        // Call onLoadMoreComplete when the LoadMore task, has finished
        ((LoadMoreListView) getListView()).onLoadMoreComplete();
        mCustomAdapter.setData(data);
        mIsLoading = false;
        mCount++;
    }

    @Override
    public void onLoaderReset(Loader<List<Entry>> loader) {
        mCustomAdapter.setData(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // java.lang.IllegalStateException: Content view not yet created となる。
//        ((LoadMoreListView)getListView()).setOnLoadMoreListener(null);
        setListAdapter(null);
        mCustomAdapter.setData(null);
        mCustomAdapter = null;
    }
}
