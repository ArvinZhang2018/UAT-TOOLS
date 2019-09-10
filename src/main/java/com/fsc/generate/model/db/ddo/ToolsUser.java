package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;
import com.valor.mfc.vms.api.model.common.AbstractPrintable;

import java.util.Date;

public class ToolsUser extends AbstractPrintable{

    @CrmColumn("uid")
    private long uid;
    @CrmColumn("fingerprint")
    private String fingerprint;
    @CrmColumn("score")
    private long score;
    @CrmColumn("nick_name")
    private String nickName;
    @CrmColumn("create_time")
    private Date createTime;
    @CrmColumn("version")
    private int version;
    @CrmColumn("like")
    private int like;
    @CrmColumn("show_log")
    private int showLog;
    @CrmColumn("language")
    private String language;

    public static final String QUERY_USER_INFO = "queryUserInfo";
    public static final String INSERT_USER = "insertUser";
    public static final String UPDATE_USER_ADD_SCORE = "addScore";
    public static final String UPDATE_USER_CONFIG = "updateUserConfig";
    public static final String UPDATE_USER_VERSION = "updateUserVersion";
    public static final String UPDATE_USER_NICKNAME = "updateUserNickname";

    @CrmExecuteSql("select * from crm_x_user where fingerprint = ?")
    public void queryUserInfo(){
    }

    @CrmExecuteSql("insert into crm_x_user(fingerprint,create_time) value(?,?)")
    public void insertUser(){
    }

    @CrmExecuteSql("update crm_x_user set score = score +1 where fingerprint = ?")
    public void addScore(){
    }

    @CrmExecuteSql("update crm_x_user set language = ?,show_log = ?,version = ? where fingerprint = ?")
    public void updateUserConfig(){
    }

    @CrmExecuteSql("update crm_x_user set version = ? where fingerprint = ? ")
    public void updateUserVersion(){
    }

    @CrmExecuteSql("update crm_x_user set nick_name = ? where fingerprint = ? ")
    public void updateUserNickname(){
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getShowLog() {
        return showLog;
    }

    public void setShowLog(int showLog) {
        this.showLog = showLog;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
