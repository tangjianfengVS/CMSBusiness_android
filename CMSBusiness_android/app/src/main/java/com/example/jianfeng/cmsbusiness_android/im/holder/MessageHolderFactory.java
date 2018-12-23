package com.example.jianfeng.cmsbusiness_android.im.holder;


import com.example.jianfeng.cmsbusiness_android.im.HeartbeatManager;
import com.pythonsh.common.Constant;
import com.pythonsh.common.RawPacket;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by oracle on 2017/10/11.
 */
public class MessageHolderFactory {
    private static Map<Short, Class<? extends MessageHolder>> holders = new HashMap<Short, Class<? extends MessageHolder>>();
    static {
        holders.put(Constant.TYPE_SENDMSG_ACK, MessageSendHolder.class);
        holders.put(Constant.TYPE_SYNC_ACK, MessageSyncHolder.class);
        holders.put(Constant.TYPE_PONG, MessagePongHolder.class);
        holders.put(Constant.TYPE_INIT_ACK, MessageInitHolder.class);
        holders.put(Constant.TYPE_INVOKE_ACK, MessageInvokeHolder.class);
    }

    public static MessageHolder create(RawPacket rawPacket, HeartbeatManager heartbeatManager) {

        short optrType = rawPacket.getOptrType();
        Class<? extends MessageHolder> holderClass = holders.get(optrType);
        Constructor constructor = null;
        try {
            if (holderClass != null) {
                constructor = holderClass.getConstructor(RawPacket.class, HeartbeatManager.class);
                return  (MessageHolder) constructor.newInstance(rawPacket, heartbeatManager);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
