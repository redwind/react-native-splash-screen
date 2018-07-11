package org.devio.rn.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
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
                    mSplashDialog.setContentView(R.layout.launch_screen);
                    mSplashDialog.setCancelable(false);
                    if (!mSplashDialog.isShowing()) {
                        mSplashDialog.show();
                    }
                }
            }
        });
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

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog != null && mSplashDialog.isShowing()) {
                    mSplashDialog.dismiss();
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
