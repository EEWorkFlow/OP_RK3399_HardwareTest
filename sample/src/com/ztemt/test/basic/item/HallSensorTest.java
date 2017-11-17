package com.ztemt.test.basic.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztemt.test.basic.R;

/**
 * Created by younix on 17-11-17.
 */
public class HallSensorTest extends BaseTest {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hallsensor, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setButtonVisibility(true);
    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.hallsensor_title);
    }

    @Override
    public boolean isNeedTest() {
        return getSystemProperties("hall", true);
    }
}
