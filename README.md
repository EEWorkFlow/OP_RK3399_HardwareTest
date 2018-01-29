OrangePi RK3399 Android Hardware Modules Test 

# HardwareTest
参考 package com.ztemt.test.basic.item，针对 OrangePi RK3399 的 Android 测试程序。
Android 各个硬件模块 自动化 / 半自动化 测试。

com.ztemt.test.basic.item 默认的功能有

```java
private static final BaseTest[] ALL_ITEMS = {
        //版本信息
        new VersionTest(),
        //LCD屏幕测试
        new LCDTest(),
        //背光测试
        new BacklightTest(),
        //触摸屏
        new TouchTest(),
        //听筒测试
        new ReceiverTest(),
        //喇叭测试
        new SpeakerTest(),
        //耳机测试
        new EarphoneTest(), //带Mic的四段式耳机回环测试
        //FM收音机
        new FmRadioTest(),
        //按键测试
        new KeyTest(),
        //震动测试
        new VibratorTest(),
        //存储卡
        new StorageTest(),
        //相机
        new CameraTest(),
        //闪光灯
        new FlashlightTest(),
        //罗盘
        new CompassTest(),
        //充电识别
        new ChargerTest(),
        //MIC回环
        new LoopbackTest(),
        //MIC气密性测试
        new MicrophoneTest(),
        //G-Sensor
        new GSensorTest(),
        //P-Sensor
        new ProxSensorTest(),
        //L-Sensor
        new LightSensorTest(),
        //NFC
        new NfcTest(),
        //Gyroscope
        new GyroSensorTest(),
};
```
