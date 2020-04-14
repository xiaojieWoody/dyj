package com.dyj.callshell;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 执行shell脚本（绝对路径）
 */
@Slf4j
public class ShellUtil {

    /**
     * exec shell start
     *
     * @param shellScript shell script absolute path
     * @throws Exception
     */
    public void execute(String shellScript) throws Exception {
        try {
            callScript(shellScript);
        } catch (Exception e) {
            log.error("ShellExecutor error, " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * exec shell script
     *
     * @param shellScript
     * @throws Exception
     */
    private void callScript(String shellScript) throws Exception {

        try {
            String cmd = "sh " + shellScript;

            // start new process
            CommandWaitForThread commandThread = new CommandWaitForThread(cmd);
            commandThread.start();

            while (!commandThread.isFinish()) {
                log.info("shell " + shellScript + " no finish, please wait");
                Thread.sleep(1000);
            }

            if (commandThread.getExitValue() != 0) {
                throw new Exception("shell " + shellScript + "exec shell fail, exitValue = " + commandThread.getExitValue());
            }
            log.info("shell " + shellScript + "exec shell success, exitValue = " + commandThread.getExitValue());

        } catch (Exception e) {
            throw new Exception("exec shell unknown error, " + shellScript, e);
        }
    }

    @Data
    public class CommandWaitForThread extends Thread {
        private String cmd;
        private boolean finish = false;
        private int exitValue = -1;

        public CommandWaitForThread(String cmd) {
            this.cmd = cmd;
        }

        @Override
        public void run() {
            try {

                Process process = Runtime.getRuntime().exec(cmd);

                BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String line = "";
                while ((line = infoInput.readLine()) != null) {
                    log.info(line);
                }
                while ((line = errorInput.readLine()) != null) {
                    log.error(line);
                }
                infoInput.close();
                errorInput.close();

                // wait for process finish
                this.exitValue = process.waitFor();
            } catch (Exception e) {
                log.error("CommandWaitForThread accrue exception, shell " + cmd, e);
                exitValue = 10;
            } finally {
                finish = true;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        String scriptPath = "/Users/dingyuanjie/work/engine/doc/license/license_file/test.sh";
        ShellUtil util = new ShellUtil();
        util.execute(scriptPath);
    }
}
