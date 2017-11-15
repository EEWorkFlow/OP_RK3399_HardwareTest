package com.ztemt.test.basic.item;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
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
 * Created by younix on 17-11-14.
 */
public class BoardInfoTest extends BaseTest {

    final String mem_path = "/proc/meminfo";// 系统内存信息文件，第一行为内存大小
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        try {
            fileReader = new FileReader(mem_path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedReader = new BufferedReader(fileReader, 8192);
        View v = inflater.inflate(R.layout.boardinfo, container, false);
        try {
            setBoardInfo(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setButtonVisibility(true);
    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.boardinfo_title);
    }

    @Override
    public boolean isNeedTest() {
        return true;
    }

    private void setBoardInfo(View v) throws IOException {
        final StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());//调用该类来获取磁盘信息（而getDataDirectory就是内部存储）
        long tcounts = statFs.getBlockCountLong();//总共的block数
        long counts = statFs.getAvailableBlocksLong() ; //获取可用的block数
        long size = statFs.getBlockSizeLong(); //每格所占的大小，一般是4KB==
        long availROMSize = counts * size;//可用内部存储大小
        long totalROMSize = tcounts * size /1024 ; //内部存储总大小 MB

        long totalRAMSize = Long.parseLong(bufferedReader.readLine().split("\\s+")[1])/1024L;//这里/1024是转换为单位MB
        long freeRAMSize = Long.parseLong(bufferedReader.readLine().split("\\s+")[1])/1024L;//这里/1024是转换为单位MB
        long availRAMSize = Long.parseLong(bufferedReader.readLine().split("\\s+")[1])/1024L;//这里/1024是转换为单位MB

        TextView totalram = (TextView) v.findViewById(R.id.boardinfo_totalmemory_info);
        TextView avialram = (TextView) v.findViewById(R.id.boardinfo_availmemory_info);
        TextView avialrom = (TextView) v.findViewById(R.id.boardinfo_availemmc_info);

        totalram.setText(totalRAMSize+"MB");
        avialram.setText(availRAMSize+"MB");
        avialrom.setText(totalROMSize/1024/1024+"GB");
    }
}
