package com.ztemt.test.basic.item;

import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.ztemt.test.basic.R;
import java.io.IOException;

/**
 * Created by younix on 17-11-28.
 */
public class SpdifTest extends BaseTest{
    private static final String TAG = "SpdifTest";
    private static final int MSG_DELAY_TIME = 3000;
    protected static final int MSG_START = 1;
    protected static final int MSG_END = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.spdif, container, false);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHandleMessage(final int index) {
        switch (index) {
            case MSG_START:
                Log.v(TAG,"HanleMessage MSG_START");
                setTimerTask(MSG_END, MSG_DELAY_TIME);
                try {
                    setSpdifOn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setButtonVisibility(false);
                break;
            case MSG_END:
                Log.v(TAG,"HanleMessage MSG_END");
                setButtonVisibility(true);
                break;
        }
    }

    private void setSpdifOn() throws IOException {
        SystemProperties.set("ctl.start","spdiftest");
        Log.v(TAG, "Call System Service 'spdiftest' in init.rc to test SPDIF.");
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        setTimerTask(MSG_START, 0);
        return true;

    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.spdif_title);
    }

    @Override
    public boolean isNeedTest() {
        return true;
    }


}
