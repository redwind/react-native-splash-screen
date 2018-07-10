package org.devio.rn.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
    private static Dialog mSplashDialog;
    private static WeakReference<Activity> mActivity;
    private static final String TAG = "SplashScreen";
    private static int  progress = 0;
    private static Thread progressThread;
    private static TextView progressText = null;
    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final int themeResId) {
        if (activity == null) return;
        mActivity = new WeakReference<Activity>(activity);
        //        View rootView =(View) activity.getWindow().getDecorView().findViewById(android.R.id.content);
//
//        ViewGroup frameLayout = (FrameLayout)rootView.getRootView();
//        Log.d(TAG,frameLayout.toString());
//        // fill in any details dynamically here
//        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        mSplashView = new SplashView(activity);
//        mSplashView.setDuration(500); // the animation will last 0.5 seconds
//        mSplashView.setBackgroundColor(Color.WHITE); // transparent hole will look white before the animation
//        mSplashView.setIconColor(Color.parseColor("#38D46A")); // this is the Twitter blue color
//        mSplashView.setIconResource(R.drawable.ic_launcher); // a Twitter icon with transparent hole in it
//        mSplashView.setRemoveFromParentOnEnd(true); // remove the SplashView from MainView once animation is completed
//
//        // add the view
//        frameLayout.addView(mSplashView);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {
                    mSplashDialog = new Dialog(activity, themeResId);
                    LayoutInflater inflater = (LayoutInflater)   activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate( R.layout.launch_screen, null);
                    progressText =(TextView) view.findViewById(R.id.progress_text);
                    mSplashDialog.setContentView(view);
                    mSplashDialog.setCancelable(false);
                    progressThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (progress <99){
                                progress = doSomeWork();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(progressText != null)
                                            progressText.setText(String.format(activity.getString(R.string.splash_progress),progress));
                                    }
                                });
                            }
                        }
                    });

                    if (!mSplashDialog.isShowing()) {
                        mSplashDialog.show();
                        progressThread.start();
                    }
                }
            }
        });
    }

    private static int doSomeWork() {
        try {
            // ---simulate doing some work---
            if(progress == 25){
                Thread.sleep(1000);
            }else if(progress == 48){
                Thread.sleep(1000);
            }else if(progress == 80){
                Thread.sleep(1000);
            }else{
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ++progress;
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final boolean fullScreen) {
        int resourceId = fullScreen ? R.style.SplashScreen_Fullscreen : R.style.SplashScreen_SplashTheme;

        show(activity, resourceId);
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity) {
        show(activity, false);
    }

    /**
     * 关闭启动屏
     */
    public static void hide(Activity activity) {
        if (activity == null) {
            if (mActivity == null) {
                return;
            }
            activity = mActivity.get();
        }
        if (activity == null) return;

        final Activity localActivity = activity;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog != null && mSplashDialog.isShowing()) {
                    if(progressText != null)
                        progressText.setText(String.format(localActivity.getString(R.string.splash_progress),100));
                    mSplashDialog.dismiss();
                    progressThread.interrupt();
                }

//                mSplashView.splashAndDisappear(new ISplashListener(){
//                    @Override
//                    public void onStart(){
//                        // log the animation start event
//                        if(BuildConfig.DEBUG){
//                            Log.d(TAG, "splash started");
//                        }
//                    }
//
//                    @Override
//                    public void onUpdate(float completionFraction){
//                        // log animation update events
//                        if(BuildConfig.DEBUG){
//                            Log.d(TAG, "splash at " + String.format("%.2f", (completionFraction * 100)) + "%");
//                        }
//                    }
//
//                    @Override
//                    public void onEnd(){
//                        // log the animation end event
//                        if(BuildConfig.DEBUG){
//                            Log.d(TAG, "splash ended");
//                        }
//                        // free the view so that it turns into garbage
//                        mSplashView = null;
//                    }
//                });
            }
        });
    }
}
