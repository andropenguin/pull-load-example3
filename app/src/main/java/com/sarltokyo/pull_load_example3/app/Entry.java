package com.sarltokyo.pull_load_example3.app;

/**
 * Created by osabe on 15/06/30.
 */
/**
 * ローダーのアイテム用データクラス
 */
public class Entry {
    private String mLabel;

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    @Override
    public String toString() {
        return mLabel;
    }
}
