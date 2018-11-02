
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 31.01.2018 11:26
 *
 */

package com.androidprojectbase.components.smartfragmentstatepageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public abstract class SmartFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private SparseArray<T> registeredFragments = new SparseArray<>();

    public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T fragment = (T) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //registeredFragments.remove(position);
        //super.destroyItem(container, position, object);
    }


    public T getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}