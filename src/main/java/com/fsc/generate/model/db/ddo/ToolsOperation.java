package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;

public class ToolsOperation {

    @CrmColumn("id")
    private long id;
    @CrmColumn("module")
    private int module;
    @CrmColumn("operation")
    private int operation;
    @CrmColumn("tag")
    private String tag;
    @CrmColumn("op_name_cn")
    private String opNameCn;
    @CrmColumn("op_name_en")
    private String opNameEn;
    @CrmColumn("op_description_cn")
    private String opDescriptionCn;
    @CrmColumn("op_description_en")
    private String opDescriptionEn;
    @CrmColumn("click_count")
    private long clickCount;
    @CrmColumn("color")
    private int color;
    @CrmColumn("sort_no")
    private int sortNo;
    @CrmColumn("score")
    private int score;
    @CrmColumn("optimize_vote")
    private int optimizeVote;
    @CrmColumn("like")
    private long like;
    @CrmColumn("status")
    private int status;
    @CrmColumn("luck_op")
    private int luckOp;
    @CrmColumn("execute_method")
    private String executeMethod;
    @CrmColumn("configs")
    private String configs;

    public static final String QUERY_ALL_OPERATIONS = "queryAllOperations";
    public static final String UPDATE_OPERATIONS_ADD_CLICK_AMOUNT = "updateOperationsAddClickAmount";

    @CrmExecuteSql("select * from crm_x_operation where status = 0 order by sort_no")
    public void queryAllOperations(){

    }
    @CrmExecuteSql("update crm_x_operation set click_count = click_count + 1 where operation = ?")
    public void updateOperationsAddClickAmount(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOpNameCn() {
        return opNameCn;
    }

    public void setOpNameCn(String opNameCn) {
        this.opNameCn = opNameCn;
    }

    public String getOpNameEn() {
        return opNameEn;
    }

    public void setOpNameEn(String opNameEn) {
        this.opNameEn = opNameEn;
    }

    public String getOpDescriptionCn() {
        return opDescriptionCn;
    }

    public void setOpDescriptionCn(String opDescriptionCn) {
        this.opDescriptionCn = opDescriptionCn;
    }

    public String getOpDescriptionEn() {
        return opDescriptionEn;
    }

    public void setOpDescriptionEn(String opDescriptionEn) {
        this.opDescriptionEn = opDescriptionEn;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getOptimizeVote() {
        return optimizeVote;
    }

    public void setOptimizeVote(int optimizeVote) {
        this.optimizeVote = optimizeVote;
    }

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLuckOp() {
        return luckOp;
    }

    public void setLuckOp(int luckOp) {
        this.luckOp = luckOp;
    }

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public String getConfigs() {
        return configs;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }
}
