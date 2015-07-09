package com.sarltokyo.pull_load_example3.app;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osabe on 15/06/30.
 */
public class CustomLoader extends AsyncTaskLoader<List<Entry>> {

    static List<Entry> sOldList;
    // staticだったのを、非staticに変更
    List<Entry> mData;
    int mCount;
    int m;

    private final static String TAG = CustomLoader.class.getSimpleName();

    public CustomLoader(Context context, int count) {
        super(context);
        mCount = count;
    }

    /**
     * バックグラウンドでローダ用のデータを読み込む
     */
    @Override
    public List<Entry> loadInBackground() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mCount == 0) {
            mData = new ArrayList<Entry>();
        } else {
            // それまでのリスト
            mData = sOldList;
            m = mData.size();
        }
        Log.d(TAG, "m = " + m);

        for (int i = 0; i < 30; i++) {
            Entry entry = new Entry();
            entry.setLabel("text" + (i + m));
            mData.add(entry);
        }

        // Entry用のリストを作成
        List<Entry> entries = new ArrayList<Entry>(mData.size());

        for (int i = 0; i < mData.size(); i++) {
            Entry entry = mData.get(i);
            entries.add(entry);
        }

        // entriesの内容をsOldListにコピー
        sOldList = new ArrayList<Entry>();
        for (Entry entry : entries) {
            sOldList.add(entry);
        }

        // mDataの要素について、参照を消す
        for (Entry entry: mData) {
            entry = null;
        }

        return entries;
    }

    /**
     * 提供する新しいデータがあるときに呼び出される
     */
    @Override
    public void deliverResult(List<Entry> data) {
        if (isReset()) {
            // リセット時(または最初に読み込みが開始されていない、もしくは
            // reset()が呼び出された後)現在の非同期クエリを解放
            if (data != null) {
                onReleaseResources(data);
            }
            return;
        }

        List<Entry> oldEntries = data;

        mData = data;

        if (isStarted()) {
            // 読み込みが開始されている (startLoading()が呼び出されているが
            // stopLoading()やreset()はまだ呼び出されていない)場合いｎ、その結果を返す
            super.deliverResult(data);
        }

        // この時点で、必要であれば oldEntries に関連するリソースを解放できる
        if (oldEntries != null && oldEntries != data) {
            onReleaseResources(oldEntries);
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void reset() {
        super.reset();
        onStopLoading();
    }

    /**
     * 読み込んだデータ・セットに関連するリソースを解放するヘルパーメソッド
     */
    protected void onReleaseResources(List<Entry> apps) {
        // Cursorの場合は閉じる
        // 単純なリストList<>の場合は特に何もしない
    }
}

