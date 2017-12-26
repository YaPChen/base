package com.jason.base.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jason.base.MyApplication;
import com.jason.base.R;
import com.jason.base.bean.AppVersionObject;
import com.jason.base.event.RequestAppUpdateMessage;
import com.jason.base.service.AppUpdateService;
import com.jason.base.ui.bussiness.FourFragment;
import com.jason.base.ui.bussiness.OneFragment;
import com.jason.base.ui.bussiness.ThreeFragment;
import com.jason.base.ui.bussiness.TwoFragment;
import com.jason.base.utils.AppCompatDialogUtils;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by chenyp on 2017/12/21.
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private Fragment oneFragment, twoFragment, threeFragment, fourFragment;
    private TextView mButtonOne, mButtonTwo, mButtonThree, mButtonFour;
    private ActivityDoOtherLinster activityDoOtherLinster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager();
        gotoUpdateApk();
    }

    private void initView() {
        mButtonOne = (TextView) findViewById(R.id.button_one);
        mButtonOne.setOnClickListener(this);
        mButtonTwo = (TextView) findViewById(R.id.button_two);
        mButtonTwo.setOnClickListener(this);
        mButtonThree = (TextView) findViewById(R.id.button_three);
        mButtonThree.setOnClickListener(this);
        mButtonFour = (TextView) findViewById(R.id.button_four);
        mButtonFour.setOnClickListener(this);
    }

    public void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        activityDoOtherLinster = (ActivityDoOtherLinster) threeFragment;
        fourFragment = new FourFragment();
        fragmentList.add(0, oneFragment);
        fragmentList.add(1, twoFragment);
        fragmentList.add(2, threeFragment);
        fragmentList.add(3, fourFragment);
        //给ViewPager设置适配器
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        F5(0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_one:
                mPager.setCurrentItem(fragmentList.indexOf(oneFragment));
                F5(fragmentList.indexOf(oneFragment));
                break;
            case R.id.button_two:
                mPager.setCurrentItem(fragmentList.indexOf(twoFragment));
                F5(fragmentList.indexOf(twoFragment));
                break;
            case R.id.button_three:
                mPager.setCurrentItem(fragmentList.indexOf(threeFragment));
                F5(fragmentList.indexOf(threeFragment));
                break;
            case R.id.button_four:
                mPager.setCurrentItem(fragmentList.indexOf(fourFragment));
                F5(fragmentList.indexOf(fourFragment));
                break;
        }
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
        @Override
        public void onPageSelected(int position) {
            F5(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }


    private void F5(int index) {
        switch (index) {
            case 0:
                mButtonOne.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_highlight), null, null);
                mButtonTwo.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonThree.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonFour.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonOne.setSelected(true);
                mButtonTwo.setSelected(false);
                mButtonThree.setSelected(false);
                mButtonFour.setSelected(false);
                break;

            case 1:
                mButtonOne.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonTwo.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_highlight), null, null);
                mButtonThree.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonFour.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonOne.setSelected(false);
                mButtonTwo.setSelected(true);
                mButtonThree.setSelected(false);
                mButtonFour.setSelected(false);
                break;
            case 2:
                mButtonOne.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonTwo.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonThree.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_highlight), null, null);
                mButtonFour.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonOne.setSelected(false);
                mButtonTwo.setSelected(false);
                mButtonThree.setSelected(true);
                mButtonFour.setSelected(false);
                break;
            case 3:
                mButtonOne.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonTwo.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonThree.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_normal), null, null);
                mButtonFour.setCompoundDrawables(null, getResources().getDrawable(R.drawable.home_bottom_tab_icon_work_highlight), null, null);
                mButtonOne.setSelected(false);
                mButtonTwo.setSelected(false);
                mButtonThree.setSelected(false);
                mButtonFour.setSelected(true);
                break;
        }
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (activityDoOtherLinster.cankeyDown()) {
                activityDoOtherLinster.keyDown();
                return true;
            } else {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
                    firstTime = secondTime;// 更新firstTime
                    MyApplication.getInstance().showMessage("再按一次回退键退出程序");
                    return true;
                } else { // 两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 回到主界面
     *
     * @param context
     */
    public static void startActivityForTop(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setPackage(context.getPackageName());
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    public interface ActivityDoOtherLinster {
        boolean cankeyDown();
        void keyDown();
    }

    public void onEventMainThread(RequestAppUpdateMessage event) {
        installApp(event.uri);
    }

    public void installApp(String filePath) {
        File _file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(_file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void gotoUpdateApk() {
        final AppVersionObject vo = AppVersionObject.getVersionInfo();
        if (!vo.isYiAZNewVersion(MainActivity.this) && !TextUtils.isEmpty(vo.getUpdateUrl())) {
            AppCompatDialogUtils.createSimpleConfirmAlertDialog(MainActivity.this, "发现新版本，是否更新？", getString(android.R.string.ok), getString(android.R.string.cancel), new AppCompatDialogUtils.DialogCallback() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                }
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            Bundle bundle = new Bundle();
                            bundle.putString("signatureurl", vo.getUpdateUrl());
                            Intent it = new Intent().setClass(MainActivity.this, AppUpdateService.class).putExtras(bundle);
                            startService(it);
                            break;
                    }
                }
                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
        }
    }
}
