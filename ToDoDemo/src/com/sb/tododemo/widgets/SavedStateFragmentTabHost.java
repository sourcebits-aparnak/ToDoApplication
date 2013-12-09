/*
 * Copyright (C) 2012 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.sb.tododemo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;

/**
 * Special TabHost that allows the use of {@link Fragment} objects for its tab
 * content. When placing this in a view hierarchy, after inflating the hierarchy
 * you must call {@link #setup(Context, FragmentManager, int)} to complete the
 * initialization of the tab host. Customized to allow saving fragment states
 * when switching between tabs. Any fragment placed in this tabhost that needs
 * to maintain it's state when switching tabs needs to implement
 * {@linkplain Saveable}
 */
public class SavedStateFragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {

    private static final String         TAG   = "SavedStateFragmentTabHost";

    private final ArrayList<TabInfo>    mTabs = new ArrayList<TabInfo>();
    private FrameLayout                 mRealTabContent;
    private Context                     mContext;
    private FragmentManager             mFragmentManager;
    private int                         mContainerId;
    private TabHost.OnTabChangeListener mOnTabChangeListener;
    private TabInfo                     mLastTab;
    private boolean                     mAttached;

    static final class TabInfo {

        private final String   tag;
        private final Class<?> clss;
        private final Bundle   args;
        private Bundle         stateArgs;
        private Fragment       fragment;

        TabInfo(final String _tag, final Class<?> _class, final Bundle _args) {

            tag = _tag;
            clss = _class;
            args = _args;
        }
    }

    static class DummyTabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        public DummyTabFactory(final Context context) {

            mContext = context;
        }

        @Override
        public View createTabContent(final String tag) {

            final View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    static class SavedState extends BaseSavedState {

        String curTab;

        SavedState(final Parcelable superState) {

            super(superState);
        }

        private SavedState(final Parcel in) {

            super(in);
            curTab = in.readString();
        }

        @Override
        public void writeToParcel(final Parcel out, final int flags) {

            super.writeToParcel(out, flags);
            out.writeString(curTab);
        }

        @Override
        public String toString() {

            return "SavedStateFragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + curTab + "}";
        }

        /**
         * IMPORTANT: Do not rename or change the scope of this field as it is
         * part of {@link Parcelable}'s Contract
         */
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

                                                                       @Override
                                                                       public SavedState createFromParcel(final Parcel in) {

                                                                           return new SavedState(in);
                                                                       }

                                                                       @Override
                                                                       public SavedState[] newArray(final int size) {

                                                                           return new SavedState[size];
                                                                       }
                                                                   };
    }

    public SavedStateFragmentTabHost(final Context context) {

        // Note that we call through to the version that takes an AttributeSet,
        // because the simple Context construct can result in a broken object!
        super(context, null);
        initFragmentTabHost(context, null);
    }

    public SavedStateFragmentTabHost(final Context context, final AttributeSet attrs) {

        super(context, attrs);
        initFragmentTabHost(context, attrs);
    }

    private void initFragmentTabHost(final Context context, final AttributeSet attrs) {

        final TypedArray a = context.obtainStyledAttributes(attrs, new int[] { android.R.attr.inflatedId }, 0, 0);
        mContainerId = a.getResourceId(0, 0);
        a.recycle();

        super.setOnTabChangedListener(this);

        // If owner hasn't made its own view hierarchy, then as a convenience
        // we will construct a standard one here.
        if (findViewById(android.R.id.tabs) == null) {
            final LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            addView(ll, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            final TabWidget tw = new TabWidget(context);
            tw.setId(android.R.id.tabs);
            tw.setOrientation(LinearLayout.HORIZONTAL);
            ll.addView(tw, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

            FrameLayout fl = new FrameLayout(context);
            fl.setId(android.R.id.tabcontent);
            ll.addView(fl, new LinearLayout.LayoutParams(0, 0, 0));

            mRealTabContent = fl = new FrameLayout(context);
            mRealTabContent.setId(mContainerId);
            ll.addView(fl, new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        }
    }

    /**
     * @deprecated Don't call the original TabHost setup, you must instead call
     *             {@link #setup(Context, FragmentManager)} or
     *             {@link #setup(Context, FragmentManager, int)}.
     */
    @Override
    @Deprecated
    public void setup() {

        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(final Context context, final FragmentManager manager) {

        super.setup();
        mContext = context;
        mFragmentManager = manager;
        ensureContent();
    }

    public void setup(final Context context, final FragmentManager manager, final int containerId) {

        super.setup();
        mContext = context;
        mFragmentManager = manager;
        mContainerId = containerId;
        ensureContent();
        mRealTabContent.setId(containerId);

        // We must have an ID to be able to save/restore our state. If
        // the owner hasn't set one at this point, we will set it ourself.
        if (getId() == View.NO_ID) {
            setId(android.R.id.tabhost);
        }
    }

    private void ensureContent() {

        if (mRealTabContent == null) {
            mRealTabContent = (FrameLayout) findViewById(mContainerId);
            if (mRealTabContent == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + mContainerId);
            }
        }
    }

    @Override
    public void setOnTabChangedListener(final OnTabChangeListener l) {

        mOnTabChangeListener = l;
    }

    public void addTab(final TabHost.TabSpec tabSpec, final Class<?> clss, final Bundle args) {

        tabSpec.setContent(new DummyTabFactory(mContext));
        final String tag = tabSpec.getTag();

        final TabInfo info = new TabInfo(tag, clss, args);

        if (mAttached) {
            // If we are already attached to the window, then check to make
            // sure this tab's fragment is inactive if it exists. This shouldn't
            // normally happen.
            info.fragment = mFragmentManager.findFragmentByTag(tag);
            if ((info.fragment != null) && !info.fragment.isDetached()) {
                final FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }
        }

        mTabs.add(info);
        addTab(tabSpec);
    }

    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();

        final String currentTab = getCurrentTabTag();

        // Go through all tabs and make sure their fragments match
        // the correct state.
        FragmentTransaction ft = null;
        for (int i = 0; i < mTabs.size(); i++) {
            final TabInfo tab = mTabs.get(i);
            tab.fragment = mFragmentManager.findFragmentByTag(tab.tag);
            if ((tab.fragment != null) && !tab.fragment.isDetached()) {
                if (tab.tag.equals(currentTab)) {
                    // The fragment for this tab is already there and
                    // active, and it is what we really want to have
                    // as the current tab. Nothing to do.
                    mLastTab = tab;
                } else {
                    // This fragment was restored in the active state,
                    // but is not the current tab. Deactivate it.
                    if (ft == null) {
                        ft = mFragmentManager.beginTransaction();
                    }
                    ft.detach(tab.fragment);
                }
            }
        }

        // We are now ready to go. Make sure we are switched to the
        // correct tab.
        mAttached = true;
        ft = doTabChanged(currentTab, ft);
        if (ft != null) {
            // TODO
            // 07-29 16:36:26.084: E/AndroidRuntime(20047):
            // java.lang.IllegalStateException: Can not perform this action
            // after onSaveInstanceState
            ft.commit();
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    protected void onDetachedFromWindow() {

        super.onDetachedFromWindow();
        mAttached = false;
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        final Parcelable superState = super.onSaveInstanceState();
        final SavedState ss = new SavedState(superState);
        ss.curTab = getCurrentTabTag();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {

        final SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentTabByTag(ss.curTab);
    }

    @Override
    public void onTabChanged(final String tabId) {

        if (mAttached) {
            final FragmentTransaction ft = doTabChanged(tabId, null);
            if (ft != null) {
                ft.commit();
                // TODO
                // 07-29 16:36:26.084: E/AndroidRuntime(20047):
                // java.lang.IllegalStateException: Can not perform this action
                // after onSaveInstanceState

            }
        }
        if (mOnTabChangeListener != null) {
            mOnTabChangeListener.onTabChanged(tabId);
        }
    }

    private FragmentTransaction doTabChanged(final String tabId, final FragmentTransaction fragmenTrans) {

        FragmentTransaction ft = fragmenTrans;

        TabInfo newTab = null;
        for (int i = 0; i < mTabs.size(); i++) {
            final TabInfo tab = mTabs.get(i);
            if (tab.tag.equals(tabId)) {
                newTab = tab;
            }
        }
        if (newTab == null) {
            throw new IllegalStateException("No tab known for tag " + tabId);
        }
        if (mLastTab != newTab) {
            if (ft == null) {
                ft = mFragmentManager.beginTransaction();
            }
            if ((mLastTab != null) && (mLastTab.fragment != null)) {

                if (mLastTab.fragment instanceof Saveable) {
                    if (mLastTab.stateArgs == null) {
                        mLastTab.stateArgs = new Bundle();
                    }
                    ((Saveable) mLastTab.fragment).saveState(mLastTab.stateArgs);
                }
                ft.detach(mLastTab.fragment);
            }
            if (newTab.fragment == null) {
                newTab.fragment = Fragment.instantiate(mContext, newTab.clss.getName(), newTab.args);
                ft.add(mContainerId, newTab.fragment, newTab.tag);
            } else {
                if (newTab.fragment instanceof Saveable) {
                    ((Saveable) newTab.fragment).restoreState(newTab.stateArgs);
                }
                ft.attach(newTab.fragment);
            }

            mLastTab = newTab;
        }
        return ft;
    }

    public static interface Saveable {

        public void saveState(Bundle state);

        public void restoreState(Bundle state);
    }
}
