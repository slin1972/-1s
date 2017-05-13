package com.zeroxy._1s.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "sys_user")
public class SysUser extends IdDomain implements Serializable{

  private static final long serialVersionUID = -1979129014781686560L;

  private String username ;

  private String password ;


}
