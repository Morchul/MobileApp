package com.example.silvanott.timer;

/**
 * Created by silvan.ott on 15.02.2017.
 */

/**
 * Enum Klasse mit den ModelObject bei den SwipViews
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

    /**
     * Nimmt den Titel des Layouts
     * @return den Titel als String
     */
    public int getTitleResId() {
        return mTitleResId;
    }
    /**
     * Nimmt die Id des Layouts
     * @return die Id als int
     */
    public int getLayoutResId() {
        return mLayoutResId;
    }
}
