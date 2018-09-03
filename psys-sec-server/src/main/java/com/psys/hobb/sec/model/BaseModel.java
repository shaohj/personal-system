package com.psys.hobb.sec.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.psys.hobb.common.sys.util.constant.UiPathConstants;
import com.psys.hobb.common.time.util.CustomLocalDateTimeSerializer;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 编  号：
 * 名  称：BaseModel
 * 描  述：
 * 完成日期：2018/7/12 23:43
 * 编码作者：SHJ
 */
@MappedSuperclass // JPA基类标识
@EntityListeners(AuditingEntityListener.class)
public abstract  class BaseModel implements Serializable {

    protected static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(updatable = false)
    protected String creator;

    @CreatedDate
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @Column(name = "create_time", updatable = false)
    protected LocalDateTime createTime;

    @Column(name = "last_updater")
    @LastModifiedBy
    protected String lastUpdater;

    @Column(name = "last_update_time")
    @LastModifiedDate
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    protected LocalDateTime lastUpdateTime;

    @Column(name = "enabled_flag")
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
