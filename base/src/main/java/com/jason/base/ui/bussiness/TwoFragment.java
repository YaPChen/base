package com.jason.base.ui.bussiness;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jason.base.R;

/**
 * Created by zhangcuicui on 2016/4/18.
 */
public class TwoFragment extends Fragment{
    private static final String TAG=TwoFragment.class.getSimpleName();
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        initView();
        return view;
    }
    private void initView(){

    }
}
