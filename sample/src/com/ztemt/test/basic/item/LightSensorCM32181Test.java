package com.ztemt.test.basic.item;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ztemt.test.basic.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by younix on 17-11-16.
 * 仅适用于 OrangePi RK3399 的 CM32181 Light Sensor
 */

public class LightSensorCM32181Test extends BaseTest {

    private TextView tv_LightSensor = null;
    private String s_LightSensor = "WATING";
    private static final int msgKey1 = 1;
    final String light_path = "/sys/bus/iio/devices/iio:device2/in_illuminance_input"; // Light Sensor 节点

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lightsensorcm32181,container, false);

        tv_LightSensor = (TextView) v.findViewById(R.id.lightsensorcm32181_tv);
        new TimeThread().start();

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setButtonVisibility(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.lightsensorcm32181_title);
    }

    @Override
    public boolean isNeedTest() {
        return true;
    }

    public static String getDeviceNode(String path){
        String prop = "WAITING";
        BufferedReader bufreader = null;
        try {
            bufreader = new BufferedReader(new FileReader(path));
            prop = bufreader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufreader != null){
                try {
                    bufreader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    public class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(100);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case msgKey1:
                    s_LightSensor = getDeviceNode(light_path);
                    tv_LightSensor.setText(s_LightSensor+"");
                    break;
                default:
                    break;
            }
        }
    };
}

