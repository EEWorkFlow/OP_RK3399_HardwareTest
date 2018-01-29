package com.ztemt.test.basic.item;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ztemt.test.basic.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by younix on 18-1-9.
 */
public class CompassAK09911Test extends BaseTest {
    final String compass_ak09911_x_raw = "/sys/bus/iio/devices/iio:device3/in_magn_x_raw";
    final String compass_ak09911_y_raw = "/sys/bus/iio/devices/iio:device3/in_magn_y_raw";
    final String compass_ak09911_z_raw = "/sys/bus/iio/devices/iio:device3/in_magn_z_raw";
    private String compass_ak09911_x = "";
    private String compass_ak09911_y = "";
    private String compass_ak09911_z = "";
    private TextView tv_ak09911_x = null;
    private TextView tv_ak09911_y = null;
    private TextView tv_ak09911_z = null;

    private static final int msgKey1 = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.compass_ak09911,container,false);
        tv_ak09911_x = (TextView) v.findViewById(R.id.compass_ak09911_x);
        tv_ak09911_y = (TextView) v.findViewById(R.id.compass_ak09911_y);
        tv_ak09911_z = (TextView) v.findViewById(R.id.compass_ak09911_z);
        new compassTimeThread().start();
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

    public class compassTimeThread extends Thread {
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
                    compass_ak09911_x = getDeviceNode(compass_ak09911_x_raw);
                    tv_ak09911_x.setText(compass_ak09911_x);
                    compass_ak09911_y = getDeviceNode(compass_ak09911_y_raw);
                    tv_ak09911_y.setText(compass_ak09911_y);
                    compass_ak09911_z = getDeviceNode(compass_ak09911_z_raw);
                    tv_ak09911_z.setText(compass_ak09911_z);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public String getTestName() {
        return "电子罗盘测试";
    }

    @Override
    public boolean isNeedTest() {
        return true;
    }
}
