package com.ztemt.test.basic.item;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ztemt.test.basic.R;

import java.io.File;

/**
 * Created by younix on 17-11-15.
 *
 */
public class EarphoneTest2 extends LoopbackTest implements OnPreparedListener {
    private static final String TAG = "EarphoneTest2";
    private static final int MSG_DELAY_TIME = 3000;
    protected static final int MSG_START = 1;
    protected static final int MSG_END = 2;

    private MediaPlayer mPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.earphone2, container, false);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onHandleMessage(final int index) {
        switch (index) {
            case MSG_START:
                setTimerTask(MSG_END, MSG_DELAY_TIME);
                setButtonVisibility(false);
                playMusic(R.raw.play);
                //startLoopback();
                break;
            case MSG_END:
                setButtonVisibility(true);
                stopMusic();
                //stopLoopback();
                break;
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        setTimerTask(MSG_START, 0);
        return true;
    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.earphone2_title);
    }

    @Override
    public boolean isNeedTest() {
        return getSystemProperties("earphone", true);
    }

    protected void playMusic(int resId) {
        stopMusic();

        AssetFileDescriptor fd = getResources().openRawResourceFd(resId);
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mPlayer.setOnPreparedListener(this);
            mPlayer.prepare();
        } catch (Exception e) {
            Log.e(TAG, "E:" + e.getMessage());
        }
    }

    protected void stopMusic() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

}
