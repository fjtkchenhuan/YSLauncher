package com.ys.yslauncher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView application1;
    private ImageView setting;
    private ImageView file;
    private ImageView explorer;
    private static int focusedItem = 0;
//    private PowerManager.WakeLock mWakeLock;
    SimpleDateFormat mSimpleDateFormat;
    Context mContext;
    TextView hourMinuteView, statusView, dateView, weekView;

    private int[] appImg = new int[2];
    private int[] settingImg = new int[2];
    private int[] fileImg = new int[2];
    private int[] exploreImg = new int[2];

    boolean is24;
    Handler timeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mContext == null) return true;
            setTimeFormate();
            timeHandler.sendEmptyMessageDelayed(1001, 30 * 1000);
            return true;
        }
    });

    private void initTime() {
        is24 = DateFormat.is24HourFormat(this);
        if (is24) {
            mSimpleDateFormat = new SimpleDateFormat("EE HH:mm");
        } else {
            mSimpleDateFormat = new SimpleDateFormat("EE hh:mm aa");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
//        mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
//                | PowerManager.SCREEN_DIM_WAKE_LOCK
//                | PowerManager.ON_AFTER_RELEASE, "SimpleTimer");
        initTime();

        initView();
        setBackground();
    }

    private void initView() {
        application1 = (ImageView) findViewById(R.id.application);
        setting = (ImageView) findViewById(R.id.setting);
        file = (ImageView) findViewById(R.id.file);
        explorer = (ImageView) findViewById(R.id.explorer);
        hourMinuteView = findViewById(R.id.time);
        statusView = findViewById(R.id.time_status);
        dateView = findViewById(R.id.date);
        weekView = findViewById(R.id.week);

//        StatusBar statusBar = (StatusBar)findViewById(R.id.status_bar);
//        statusBar.zoomed(true);

        application1.setOnClickListener(this);
        setting.setOnClickListener(this);
        file.setOnClickListener(this);
        explorer.setOnClickListener(this);
        application1.setFocusable(true);
        application1.requestFocus();
        application1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("heef", "application1 keyCode:" + keyCode + "  focusedItem:" + focusedItem
                        + " event.getAction():" + event.getAction());
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        focusedItem = 1;
                        setFocusedItem(focusedItem);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        onClick(application1);
                    }
                }
                return true;
            }
        });
        setting.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("heef", "setting keyCode:" + keyCode + " getAction:" + event.getAction());
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        focusedItem = 2;
                        setFocusedItem(focusedItem);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        focusedItem = 0;
                        setFocusedItem(focusedItem);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        onClick(setting);
                    }
                }else if(event.getAction() == KeyEvent.ACTION_UP){

                }
                return true;
            }
        });
        file.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("heef", "file keyCode:" + keyCode);
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        focusedItem = 3;
                        setFocusedItem(focusedItem);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        focusedItem = 1;
                        setFocusedItem(focusedItem);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        onClick(file);
                    }
                }
                return true;
            }
        });
        explorer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("heef", "explorer keyCode:" + keyCode);
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        focusedItem = 2;
                        setFocusedItem(focusedItem);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        onClick(explorer);
                    }
                }
                return true;
            }
        });
    }

    private void setBackground() {
        String able = getResources().getConfiguration().locale.getCountry();
        if ("CN".equals(able) || "TW".equals(able) || "HK".equals(able)) {
            application1.setBackground(getResources().getDrawable(R.drawable.application_cn_background));
            setting.setBackground(getResources().getDrawable(R.drawable.setting_cn_background));
            file.setBackground(getResources().getDrawable(R.drawable.file_cn_background));
            explorer.setBackground(getResources().getDrawable(R.drawable.explorer_cn_background));
            appImg[0] = R.drawable.up1;
            appImg[1] = R.drawable.sup1;
            settingImg[0] = R.drawable.up2;
            settingImg[1] = R.drawable.sup2;
            fileImg[0] = R.drawable.up3;
            fileImg[1] = R.drawable.sup3;
            exploreImg[0] = R.drawable.up4;
            exploreImg[1] = R.drawable.sup4;
        } else {
            application1.setBackground(getResources().getDrawable(R.drawable.application_en_background));
            setting.setBackground(getResources().getDrawable(R.drawable.setting_en_background));
            file.setBackground(getResources().getDrawable(R.drawable.file_en_background));
            explorer.setBackground(getResources().getDrawable(R.drawable.explorer_en_background));
            appImg[0] = R.drawable.up1en;
            appImg[1] = R.drawable.sup1en;
            settingImg[0] = R.drawable.up2en;
            settingImg[1] = R.drawable.sup2en;
            fileImg[0] = R.drawable.up3en;
            fileImg[1] = R.drawable.sup3en;
            exploreImg[0] = R.drawable.up4en;
            exploreImg[1] = R.drawable.sup4en;
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.application:
                intent = new Intent(this, AppListActivity.class);
                focusedItem = 0;
                break;
            case R.id.setting:
                intent.setClassName("com.android.settings", "com.android.settings.Settings");
                focusedItem = 1;
                break;
            case R.id.file:
                focusedItem = 2;
                intent.setClassName("com.android.rockchip", "com.android.rockchip.RockExplorer");
                break;
            case R.id.explorer:
                focusedItem = 3;
                if (isApkInstall())
                    intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                else
                    intent.setClassName("com.android.chrome", "org.chromium.chrome.browser.document.ChromeLauncherActivity");
                //org.chromium.chrome.browser.ChromeTabbedActivity     org.chromium.chrome.browser.document.ChromeLauncherActivity
                break;
            default:
                break;
        }
        Utils.startActivitySafely(intent, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimeFormate();
        int curSecend = Integer.parseInt(new SimpleDateFormat("ss").format(new Date()));
        timeHandler.sendEmptyMessageDelayed(1001, (60 - curSecend) * 1000);
//        mWakeLock.acquire();
        setFocusedItem(focusedItem);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeHandler.removeMessages(1001);
//        mWakeLock.release();
    }

    public void setTimeFormate() {
        final Calendar now = Calendar.getInstance();
        String today = mSimpleDateFormat.format(now.getTime());
        String[] dates = today.split(" ");
        if (hourMinuteView != null) {
            hourMinuteView.setText(dates[1]);
        }
        if (statusView != null) {
            if (is24) {
                statusView.setText("");
            } else {
                statusView.setText(dates[2].toUpperCase());
            }
        }
        String longDate = DateFormat.getLongDateFormat(mContext).format(now.getTime());
        if (dateView != null) {
            dateView.setText(longDate.substring(5, longDate.length()));
        }
        if (weekView != null)
            weekView.setText(dates[0]);
    }

    private boolean isApkInstall() {
        boolean isAppInstall;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.android.browser", 0);
            if (packageInfo == null) {
                isAppInstall = false;
            } else {
                isAppInstall = true;
            }
        } catch (Exception e) {
            isAppInstall = false;
        }
        return isAppInstall;
    }

    public void setFocusedItem(int focusedItem) {
        Log.d("ddd", "item = " + focusedItem);
        switch (focusedItem) {
            case 0:
                file.setFocusable(false);
                setting.setFocusable(false);
                application1.setFocusable(true);
                explorer.setFocusable(false);
                application1.requestFocus();
                application1.setBackgroundResource(appImg[1]);
                setting.setBackgroundResource(settingImg[0]);
                file.setBackgroundResource(fileImg[0]);
                explorer.setBackgroundResource(exploreImg[0]);
                break;
            case 1:
                application1.setBackgroundResource(appImg[0]);
                file.setFocusable(false);
                setting.setFocusable(true);
                application1.setFocusable(false);
                explorer.setFocusable(false);
                setting.requestFocus();
                setting.setBackgroundResource(settingImg[1]);
                file.setBackgroundResource(fileImg[0]);
                explorer.setBackgroundResource(exploreImg[0]);
                break;
            case 2:
                application1.setBackgroundResource(appImg[0]);
                setting.setBackgroundResource(settingImg[0]);
                file.setFocusable(true);
                setting.setFocusable(false);
                application1.setFocusable(false);
                explorer.setFocusable(false);
                file.requestFocus();
                file.setBackgroundResource(fileImg[1]);
                explorer.setBackgroundResource(exploreImg[0]);
                break;
            case 3:
                application1.setBackgroundResource(appImg[0]);
                setting.setBackgroundResource(settingImg[0]);
                file.setBackgroundResource(fileImg[0]);
                file.setFocusable(false);
                setting.setFocusable(false);
                application1.setFocusable(false);
                explorer.setFocusable(true);
                explorer.requestFocus();
                explorer.setBackgroundResource(exploreImg[1]);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
