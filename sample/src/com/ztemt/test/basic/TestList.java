    package com.ztemt.test.basic;

import android.content.Context;

import com.ztemt.test.basic.item.BacklightTest;
import com.ztemt.test.basic.item.BaseTest;
import com.ztemt.test.basic.item.BoardInfoTest;
import com.ztemt.test.basic.item.CameraTest;
import com.ztemt.test.basic.item.ChargerTest;
import com.ztemt.test.basic.item.CompassTest;
import com.ztemt.test.basic.item.EarphoneTest;
import com.ztemt.test.basic.item.EarphoneTest2;
import com.ztemt.test.basic.item.FlashlightTest;
import com.ztemt.test.basic.item.FmRadioTest;
import com.ztemt.test.basic.item.GSensorTest;
import com.ztemt.test.basic.item.GyroSensorTest;
import com.ztemt.test.basic.item.KeyTest;
import com.ztemt.test.basic.item.LCDTest;
import com.ztemt.test.basic.item.LightSensorTest;
import com.ztemt.test.basic.item.LoopbackTest;
import com.ztemt.test.basic.item.MicrophoneTest;
import com.ztemt.test.basic.item.NfcTest;
import com.ztemt.test.basic.item.ProxSensorTest;
import com.ztemt.test.basic.item.ReceiverTest;
import com.ztemt.test.basic.item.SpeakerTest;
import com.ztemt.test.basic.item.StorageTest;
import com.ztemt.test.basic.item.TouchTest;
import com.ztemt.test.basic.item.UnknownTest;
import com.ztemt.test.basic.item.VersionTest;
import com.ztemt.test.basic.item.VibratorTest;

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
        //FM收音机
        //new FmRadioTest(),
        //按键测试
        new KeyTest(),
/*
        //震动测试
        //new VibratorTest(),
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
        //MIC回环
        new LoopbackTest(),
        //MIC气密性测试
        //new MicrophoneTest(),
        //G-Sensor
        new GSensorTest(),
        //P-Sensor
        //new ProxSensorTest(),
        //L-Sensor
        new LightSensorTest(),
        //NFC
        //new NfcTest(),
        //Gyroscope
        new GyroSensorTest(),
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
