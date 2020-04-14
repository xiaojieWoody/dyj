package com.dyj.callshell;

/**
 * 直接执行命令语句
 */
public class SingleShell {

    public static void main(String[] args) {
        String originpath = args[0];
        String targetpath = args[1];
        // 移动文件
        String shellCmd = "mv " + originpath + " " + targetpath;
        String winCmd = "cmd /c move " + originpath + " " + targetpath + "\\";
        // 删除文件
//        String shellCmd = "rm -f " + originpath;
//        String winCmd = "cmd /c del /s/q "+originpath;

        // windows
        if(winCmd != null) {
            try {
                Process process = Runtime.getRuntime().exec(winCmd);
                if(process.waitFor() != 0) {
                    System.out.println("command run failed, please check the parameter");
                }
                process.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // linux
        if(shellCmd != null) {
            try {
                String[] shell =  {"/bin/bash", "-c", shellCmd};
                Process process = Runtime.getRuntime().exec(shell);
                if(process.waitFor() != 0) {
                    System.out.println("command run failed, please check the parameter");
                }
                process.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
