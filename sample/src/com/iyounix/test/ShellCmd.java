package com.iyounix.test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 执行 shell 脚本工具类
 * @author Younix
 * Created by younix on 17-11-28.
 */
public class ShellCmd {
    public static final String TAG = "ShellCmd";
    public final static String CMD_SU       = "su";
    public final static String CMD_SH       = "sh";
    public final static String CMD_EXIT     = "exit\n";
    public final static String CMD_LINE_END = "\n";

    /**
     * Command执行结果
     * @author Younix
     *
     */
    public static class CMDResult {
        public int result = -1;
        public String errorMsg;
        public String successMsg;
    }
    /**
     * 执行命令—单条
     * @param command
     * @param isRoot
     * @return
     */
    public static CMDResult CMDexec(String command, boolean isRoot) {
        String[] commands = {command};
        return CMDexec(commands, isRoot);
    }

    /**
     * 执行命令-多条
     * @param commands
     * @param isRoot
     * @return
     */
    public static CMDResult CMDexec(String[] commands, boolean isRoot) {
        CMDResult cmdResult = new CMDResult();
        if (commands == null || commands.length == 0) return cmdResult;
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? CMD_SU : CMD_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command != null) {
                    os.write(command.getBytes());
                    os.writeBytes(CMD_LINE_END);
                    os.flush();
                }
            }
            os.writeBytes(CMD_EXIT);
            os.flush();
            cmdResult.result = process.waitFor();
            //获取错误信息
            successMsg = new StringBuilder();
            errorMsg = new StringBuilder();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) successMsg.append(s);
            while ((s = errorResult.readLine()) != null) errorMsg.append(s);
            cmdResult.successMsg = successMsg.toString();
            cmdResult.errorMsg = errorMsg.toString();
            Log.i(TAG, cmdResult.result + " | " + cmdResult.successMsg
                    + " | " + cmdResult.errorMsg);
        } catch (IOException e) {
            String errmsg = e.getMessage();
            if (errmsg != null) {
                Log.e(TAG, errmsg);
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            String errmsg = e.getMessage();
            if (errmsg != null) {
                Log.e(TAG, errmsg);
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (os != null) os.close();
                if (successResult != null) successResult.close();
                if (errorResult != null) errorResult.close();
            } catch (IOException e) {
                String errmsg = e.getMessage();
                if (errmsg != null) {
                    Log.e(TAG, errmsg);
                } else {
                    e.printStackTrace();
                }
            }
            if (process != null) process.destroy();
        }
        return cmdResult;
    }

    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd="chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

}
