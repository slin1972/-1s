package com.zeroxy._1s.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "controlled_terminal")
public class ControlledTerminal implements Serializable{

  private static final long serialVersionUID = -1979129014781686560L;

  @Id
  @JsonProperty("device_no")
  private String deviceNo;
  private String ip ;
  private String cpu ;
  private String ram ;
  private String os ;
  private String name ;
  private Integer lastHeartTime ;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getCpu() {
    return cpu;
  }

  public void setCpu(String cpu) {
    this.cpu = cpu;
  }

  public String getRam() {
    return ram;
  }

  public void setRam(String ram) {
    this.ram = ram;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getLastHeartTime() {
    return lastHeartTime;
  }

  public void setLastHeartTime(Integer lastHeartTime) {
    this.lastHeartTime = lastHeartTime;
  }

  @Override
  public String toString() {
    return "ControlledTerminal{" +
            "deviceNo='" + deviceNo + '\'' +
            ", ip='" + ip + '\'' +
            ", cpu='" + cpu + '\'' +
            ", ram='" + ram + '\'' +
            ", os='" + os + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
