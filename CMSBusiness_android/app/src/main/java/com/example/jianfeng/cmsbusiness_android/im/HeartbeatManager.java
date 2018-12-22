package com.example.jianfeng.cmsbusiness_android.im;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by oracle on 2017/10/20.
 */
public final class HeartbeatManager {
    public final static int HEARTBEAT_MIN = 5;
    public final static int HEARTBEAT_MAX = 10 * 5;
    private final static int HEARTBEAT_STEP = 15;

    private long now = System.currentTimeMillis();

    private int curHeartbeat = HEARTBEAT_MIN;  //初始状态下每次hb的时间间隔
    private AtomicLong curHeartbeatSuccess = new AtomicLong(0);   //接收到一次则+1
    private AtomicLong curHeartbeatTotoal = new AtomicLong(0);    //发送成功一次则+1

    private int successHeartbeat = 0;          //稳定间隔下每次hb的时间间隔
    private AtomicLong successHeartbeatSuccess = new AtomicLong(0);   //接收到一次则+1
    private AtomicLong successHeartbeatTotal = new AtomicLong(0);     //发送成功一次则+1

    private int state = 0;      //记录当前状态

    //idle每隔5秒钟调用一次,这里进行计数,看是否到了需要发送hb的时候
    private AtomicLong nextCount = new AtomicLong(1);
    private AtomicLong total = new AtomicLong(0);

    private void resetCurHB() {
        curHeartbeatSuccess.set(0);
        curHeartbeatTotoal.set(0);
    }

    private void resetSucHB() {
        successHeartbeatSuccess.set(0);
        successHeartbeatTotal.set(0);
    }

    public void resetAll() {
        resetCurHB();

        resetSucHB();

        total.set(0);
        nextCount.set(1);
        now = System.currentTimeMillis();
    }

    public boolean shouldHeartbeat() {
        //先检查是稳定状态还是初始状
        if (System.currentTimeMillis() - now > 24 * 60 * 60 * 1000) {
            resetAll();
            return false;
        }

        total.incrementAndGet();

        switch (state) {
            case 0:
                /*
                * 初始状态
                * 先用三次min测试,然后如果成功则按步长递加
                * */

                return shouldHbFromInitialied();
            default:
                //稳定状态

                return shouldHbFromStable();
        }

    }

    private boolean shouldHbFromInitialied() {
        /*
        * 初始状态下 3次短心跳后开始步跳,直到出现5次以上失败为止,进入下一状态
        * */
        long curTotal = curHeartbeatTotoal.get();
        long curSuccess = curHeartbeatSuccess.get();

        if (curTotal >= 3) {
            if (total.get() != nextCount.get()) {
                return false;
            } else if (curSuccess == curTotal && total.get() == nextCount.get()) {
                if (successHeartbeat == HEARTBEAT_MAX) {
                    System.out.println("Change to stable mode!");
                    state = 1;
                    nextCount.addAndGet(successHeartbeat / HEARTBEAT_MIN);
                    return shouldExecuteHb();
                }
                successHeartbeat = curHeartbeat;
                curHeartbeat += HEARTBEAT_STEP;
                if (curHeartbeat >= HEARTBEAT_MAX) {
                    curHeartbeat = HEARTBEAT_MAX;
                    successHeartbeat = HEARTBEAT_MAX;
                }

                nextCount.addAndGet(curHeartbeat / HEARTBEAT_MIN);
                System.out.println(String.format("Change time to %d on state %d", curHeartbeat, state));

                return true;

            } else if (curTotal - curSuccess < 5){
                //现实场景没法测试,只能在具体移动网络中测试
            } else {
                //如果连续失败5次就停下,并将状态转换为1
                if (successHeartbeat > 0) {
                    System.out.println("Change to stable mode!");

                    state = 1;
                    nextCount.addAndGet(successHeartbeat / HEARTBEAT_MIN);
                    return false;
                } else {
                    nextCount.addAndGet(curHeartbeat / HEARTBEAT_MIN);
                    return true;
                }


            }
        }


        return shouldExecuteHb();

    }

    private boolean shouldExecuteHb() {
        if (total.get() == nextCount.get()) {
            System.out.println("Total:" + total.get());
            System.out.println("Next:" + nextCount.get());
            nextCount.addAndGet(curHeartbeat / HEARTBEAT_MIN);
            return true;
        }

        return false;

    }

    private boolean shouldHbFromStable() {
        /*
        * 稳定状态下 5次心跳失败重置状态为初始状态,并初始化所有的值
        * */
        long sucTotal = successHeartbeatTotal.get();
        long sucSuccess = successHeartbeatSuccess.get();

        if (sucTotal - sucSuccess < 5){

        } else {
            //如果连续失败5次就停下,并将状态转换为1
            state = 0;

            curHeartbeat = HEARTBEAT_MIN;
            resetCurHB();

            successHeartbeat = 0;
            resetSucHB();
            nextCount.set(1);
            total.set(0);
            return false;
        }


        if (total.get() == nextCount.get()) {
            nextCount.addAndGet(successHeartbeat / HEARTBEAT_MIN);
            return true;
        }

        return false;
    }


    //每次成功了都要检查上次是否成功,不成功的话要重置,这里要注意就是如果success当前差<5时,如果有一个是成功的,则total和success均需要重置为0


    public void increaseSuccess() {
        switch (state) {
            case 0:
                this.curHeartbeatSuccess.incrementAndGet();
                break;
            default:
                this.successHeartbeatSuccess.incrementAndGet();
                long failed = successHeartbeatTotal.get() - successHeartbeatSuccess.get();
                if (failed > 0 && failed < 5 ) resetSucHB();
                break;
        }
    }

    public void increaseTotal() {
        switch (state) {
            case 0:
                this.curHeartbeatTotoal.incrementAndGet();
                break;
            default:
                this.successHeartbeatTotal.incrementAndGet();
                break;
        }

    }

}
