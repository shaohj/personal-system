package com.psys.hobb.common.sys.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.psys.hobb.common.sys.util.constant.UiPathConstants;
import com.psys.hobb.common.time.util.CustomLocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 编  号：
 * 名  称：BaseModelParam
 * 描  述：
 * 完成日期：2018/7/14 23:10
 * 编码作者：SHJ
 */
public abstract  class BaseModelParam implements Serializable {

    protected static final long serialVersionUID = 1L;

    protected String creator;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    protected LocalDateTime createTime;

    protected String lastUpdater;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    protected LocalDateTime lastUpdateTime;

    protected String enabledFlag;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getShowCreateTime() {
        if(null != createTime){
            return UiPathConstants.DEFAULT_FORMATTER.format(createTime);
        }
        return "";
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdater() {
        return lastUpdater;
    }

    public void setLastUpdater(String lastUpdater) {
        this.lastUpdater = lastUpdater;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

}
