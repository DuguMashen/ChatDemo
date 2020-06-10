package com.example.demo.model;

import java.util.Map;

/**
 * @author jinhq
 */
public class WsMessage {
    private String fromUserId;
    private String toUserId;
    private String type;
    private Map map;
    private String time;


    public enum TypeEnum {
        /**
         * 用户消息
         */
        USERLIST("userlist","用户列表"),
        CLIENT("client", "客户消息");
        private String type;
        private String desc;

        TypeEnum(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
