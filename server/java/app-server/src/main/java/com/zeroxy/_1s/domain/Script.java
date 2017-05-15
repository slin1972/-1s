package com.zeroxy._1s.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "t_script")
public class Script extends IdDomain implements Serializable{

    @JsonProperty("device_no")
    private String deviceNo;

    @Column(length=9000)
    private String script ;

    @Column(length=9000)
    private String result ;

    private int status = 1 ;

    @JsonProperty("send_time")
    private long sendTime = System.currentTimeMillis() ;

    @JsonProperty("response_time")
    private Long responseTime ;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return "Script{" +
                "deviceNo='" + deviceNo + '\'' +
                ", script='" + script + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
