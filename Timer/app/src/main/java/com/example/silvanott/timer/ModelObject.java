package com.example.silvanott.timer;

/**
 * Created by silvan.ott on 15.02.2017.
 */

public enum ModelObject {
    RED(R.string.stopwatch, R.layout.stopwatch),
    BLUE(R.string.timer, R.layout.timer),
    GREEN(R.string.settings, R.layout.settings_layout);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
