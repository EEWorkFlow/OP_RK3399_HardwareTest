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
 * Created by younix on 17-11-20.
 */
public class EthernetTest extends BaseTest{
    final String ethernet_operstate = "/sys/class/net/eth0/operstate"; //以太网状态
    final String ethernet_speed = "/sys/class/net/eth0/speed"; //以太网类型
    final String ethernet_address = "/sys/class/net/eth0/address"; //以太网地址
    private String s_operstate = "";
    private String s_speed = "";
    private String s_address = "";
    private TextView tv_operstate = null;
    private TextView tv_speed = null;
    private TextView tv_address = null;

    private static final int msgKey1 = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ethernet, container, false);
        tv_operstate = (TextView) v.findViewById(R.id.ethernet_operstate);
        tv_speed = (TextView) v.findViewById(R.id.ethernet_speed);
        tv_address = (TextView) v.findViewById(R.id.ethernet_address);
        new ethernetTimeThread().start();
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

    public class ethernetTimeThread extends Thread {
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
                    s_operstate = getDeviceNode(ethernet_operstate);
                    tv_operstate.setText(s_operstate);
                    s_speed = getDeviceNode(ethernet_speed);
                    tv_speed.setText(s_speed);
                    s_address = getDeviceNode(ethernet_address);
                    tv_address.setText(s_address);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public String getTestName() {
        return "以太网测试";
    }

    @Override
    public boolean isNeedTest() {
        return getSystemProperties("ethernet", true);
    }
}
