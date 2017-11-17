    package com.ztemt.test.basic;

import android.content.Context;

import com.ztemt.test.basic.item.BacklightTest;
import com.ztemt.test.basic.item.BaseTest;
import com.ztemt.test.basic.item.BoardInfoTest;
import com.ztemt.test.basic.item.EarphoneTest2;
import com.ztemt.test.basic.item.GSensorTest;
import com.ztemt.test.basic.item.GyroSensorTest;
import com.ztemt.test.basic.item.HallSensorTest;
import com.ztemt.test.basic.item.KeyTest;
import com.ztemt.test.basic.item.LCDTest;
import com.ztemt.test.basic.item.LightSensorCM32181Test;
import com.ztemt.test.basic.item.LightSensorTest;
import com.ztemt.test.basic.item.LoopbackTest;
import com.ztemt.test.basic.item.SpeakerTest;
import com.ztemt.test.basic.item.TouchTest;
import com.ztemt.test.basic.item.UnknownTest;
import com.ztemt.test.basic.item.VersionTest;

    public class TestList {

    // Add new test item here
    private static final BaseTest[] ALL_ITEMS = {
        //版本测试
        new VersionTest(),
        //硬件测试
        new BoardInfoTest(),
        //LCD屏幕测试
        new LCDTest(),
        //背光测试
        new BacklightTest(),
        //触摸屏
        new TouchTest(),
        //听筒测试
        //new ReceiverTest(),
        //喇叭测试
        new SpeakerTest(),
        //耳机测试
        //new EarphoneTest(), //带Mic的四段式耳机回环测试
        new EarphoneTest2(), //普通耳机测试
        // MIC回环测试
        new LoopbackTest(),
        //FM收音机
        //new FmRadioTest(),
        //按键测试
        new KeyTest(),
        //震动测试
        //new VibratorTest(),
        //传感器测试
        //G-Sensor
        new GSensorTest(),
        //Gyroscope
        new GyroSensorTest(),
        //LightSensor IIO Sensor CM32181
        new LightSensorCM32181Test(),
        // Hall Sensor
        new HallSensorTest(),
/*
        //存储卡
        new StorageTest(),
        //相机
        new CameraTest(),
        //闪光灯
        //new FlashlightTest(),
        //罗盘
        new CompassTest(),
        //充电
        new ChargerTest(),

        //MIC气密性测试
        //new MicrophoneTest(),
        //P-Sensor
        //new ProxSensorTest(),
        //NFC
        //new NfcTest(),
        //L-Sensor
        new LightSensorTest(),
*/
    };

    // Need to test items
    private static BaseTest[] sItems;

    // Unknown test item
    private static BaseTest sUnknownTest = new UnknownTest();

    static void updateItems(Context context) {
        sUnknownTest.setContext(context);

        int size = 0;
        for (BaseTest t : ALL_ITEMS) {
            t.setContext(context);
            if (t.isNeedTest()) {
                size++;
            }
        }
        sItems = new BaseTest[size];

        int i = 0;
        for (BaseTest t : ALL_ITEMS) {
            if (t.isNeedTest()) {
                sItems[i] = t;
                i++;
            }
        }
    }

    static int getCount() {
        return sItems.length;
    }

    static BaseTest get(int position) {
        if (position >= 0 && position < getCount()) {
            return sItems[position];
        } else {
            return sUnknownTest;
        }
    }
}
