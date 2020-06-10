package com.example.demo.model;

/**
 * @author
 */
public class User {
    /**
     * 昵称
     */
    private String nick;
    private String acc;
    private String pwd;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
