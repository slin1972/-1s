package com.zeroxy._1s.web;

import com.zeroxy.CommonResult;
import com.zeroxy._1s.domain.ControlledTerminal;
import com.zeroxy._1s.domain.MasterUser;
import com.zeroxy._1s.domain.Script;
import com.zeroxy._1s.kafka.Sender;
import com.zeroxy._1s.repository.ControlledTerminalRepository;
import com.zeroxy._1s.repository.MasterUserRepository;
import com.zeroxy._1s.repository.ScriptRepository;
import com.zeroxy._1s.result.ResponseCode;
import com.zeroxy.util.Base64Util;
import com.zeroxy.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 作用：
 * ① 测试服务实例的相关内容
 * ② 为后来的服务做提供
 * @author slin
 */
@RestController
public class V1Controller {
  //@Autowired
  //private Sender kafkaSender;
  @Autowired
  private ControlledTerminalRepository controlledTerminalRepository;
  @Autowired
  private ScriptRepository scriptRepository;
  @Autowired
  private MasterUserRepository masterUserRepository;

  @PostMapping("/api/v1/heart")
  public CommonResult heart(@RequestBody ControlledTerminal controlledTerminal) {
    controlledTerminal.setLastHeartTime((int)(System.currentTimeMillis()/1000));

    ControlledTerminal controlledTerminal1 = controlledTerminalRepository.findByDeviceNo(controlledTerminal.getDeviceNo());
    if(controlledTerminal1 != null){
      controlledTerminal.setMaster(controlledTerminal1.getMaster());
    }

    controlledTerminalRepository.save(controlledTerminal);
    CommonResult commonResult = ResponseCode.OK_0;
    if(controlledTerminal.getAv().equals("1")){
      //new version
    }
    List<Script> scripts = scriptRepository.findByDeviceNoAndStatus(controlledTerminal.getDeviceNo(), 1);
    if(scripts != null && scripts.size() != 0){
      commonResult = ResponseCode.newOkResult().setAttribute("scripts", scripts);
    }
    return commonResult;
  }

  @PostMapping("/api/v1/script_response")
  public CommonResult controlledScriptResponse(@RequestBody Script script) {
    script.setResult(new String(Base64Utils.decodeFromString(script.getResult())));
    script.setResponseTime(System.currentTimeMillis());
    script.setStatus(0);
    scriptRepository.save(script);
    //kafkaSender.sendScriptResponse(script);
    return ResponseCode.OK_0;
  }

  @PostMapping("/api/v1/upload")
  public CommonResult upload(@RequestParam String n, @RequestParam String v) {
    Base64Util.base64ToFile(v, "/usr/local/-1s/controlled/files/" + n);
    return ResponseCode.OK_0;
  }

  @PostMapping("/api/v1/master/login")
  public CommonResult masterLogin(@RequestBody MasterUser masterUser) {
    String token = UUID.randomUUID().toString();
    String password = CommonUtil.md5(masterUser.getUsername() + masterUser.getPassword());
    int count = masterUserRepository.updateTokenByUsernameAndPassword(token, masterUser.getUsername(), password);
    if(count > 0){
      masterUser = masterUserRepository.findByUsername(masterUser.getUsername());
      return ResponseCode.newOkResult().setAttribute("master_user", masterUser);
    }
    return ResponseCode.ERROR_100;
  }
  @PostMapping("/api/v1/master/register")
  public CommonResult masterRegister(@RequestBody MasterUser masterUser) {
    String token = UUID.randomUUID().toString();
    String username = masterUser.getUsername();
    String password = masterUser.getPassword();
    password = CommonUtil.md5(username + password);

    int count = masterUserRepository.updateTokenByUsernameAndPassword(token, username, password);
    if(count > 0){
      masterUser = masterUserRepository.findByUsername(username);
      return ResponseCode.newOkResult().setAttribute("master_user", masterUser);
    }

    masterUser = masterUserRepository.findByUsername(username);
    if(masterUser != null){
      return ResponseCode.ERROR_101;
    }

    masterUser = new MasterUser();
    masterUser.setToken(token);
    masterUser.setUsername(username);
    masterUser.setPassword(password);
    masterUserRepository.save(masterUser);
    return ResponseCode.newOkResult().setAttribute("master_user", masterUser);
  }

  @GetMapping("/api/v1/script_status")
  public CommonResult scriptStatus(@RequestParam("script_id") Long scriptId,@RequestHeader String token) {
    MasterUser masterUser = validateToken(token);
    if(masterUser == null){
      return ResponseCode.ERROR_300;
    }
    Script script = scriptRepository.findOne(scriptId);
    return ResponseCode.newOkResult().setAttribute("script", script);
  }

  @GetMapping("/api/v1/downloadControlled")
  public CommonResult downloadControlled(@RequestHeader(required = false) String token) {
    MasterUser masterUser = validateToken(token);
    String master = "default";
    if(masterUser != null){
      master = CommonUtil.md5(masterUser.getUsername() + masterUser.getId());
    }
    //判断文件是否存在

    return ResponseCode.newOkResult().setAttribute("url", "/files/controlled/client/"+master+".exe");
  }

  @PostMapping("/api/v1/script")
  public CommonResult controlledScript(@RequestBody Script script,@RequestHeader String token) {
    MasterUser masterUser = validateToken(token);
    if(masterUser == null){
      return ResponseCode.ERROR_300;
    }
    scriptRepository.save(script);
//    kafkaSender.sendScript(script, (script1)->{
//      Script script2 = (Script) script1;
//      scriptRepository.save(script2);
//    });

    return ResponseCode.OK_0;
  }


  @GetMapping("/api/v1/script_list")
  public CommonResult scriptList(@RequestParam("device_no") String device_no,@RequestHeader String token) {
    MasterUser masterUser = validateToken(token);
    if(masterUser == null){
      return ResponseCode.ERROR_300;
    }
    List<Script> scripts = null ;
    if(StringUtils.isBlank(device_no)){
      scripts = scriptRepository.findAll();
    }else{
      scripts = scriptRepository.findByDeviceNo(device_no);
    }
    return ResponseCode.newOkResult().setAttribute("scripts", scripts);
  }

  @GetMapping("/api/v1/controlled_list")
  public CommonResult controlledList(@RequestParam("device_no")String deviceNo,@RequestHeader String token) {
    MasterUser masterUser = validateToken(token);
    if(masterUser == null){
      return ResponseCode.ERROR_300;
    }
    String master = CommonUtil.md5(masterUser.getUsername() + masterUser.getId());
    List<ControlledTerminal> cntrolledTerminals = null ;
    if(StringUtils.isBlank(deviceNo)){
      cntrolledTerminals = controlledTerminalRepository.findByMaster(master);
    }else{
      cntrolledTerminals = controlledTerminalRepository.findByDeviceNoAndMaster(deviceNo, master);
    }
    return ResponseCode.newOkResult().setAttribute("controlleds", cntrolledTerminals);
  }

  private MasterUser validateToken(String token) {
    MasterUser masterUser = null ;
    if(StringUtils.isNotBlank(token)){
      masterUser = masterUserRepository.findByToken(token);
    }
    return masterUser ;
  }
}
