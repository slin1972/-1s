package com.zeroxy._1s.web;

import com.zeroxy.CommonResult;
import com.zeroxy._1s.domain.ControlledTerminal;
import com.zeroxy._1s.domain.MasterUser;
import com.zeroxy._1s.domain.Script;
import com.zeroxy._1s.kafka.Callback;
import com.zeroxy._1s.kafka.Receiver;
import com.zeroxy._1s.kafka.Sender;
import com.zeroxy._1s.repository.ControlledTerminalRepository;
import com.zeroxy._1s.repository.MasterUserRepository;
import com.zeroxy._1s.repository.ScriptRepository;
import com.zeroxy._1s.result.ResponseCode;
import com.zeroxy.util.Base64Util;
import com.zeroxy.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 作用：
 * ① 测试服务实例的相关内容
 * ② 为后来的服务做提供
 * @author slin
 */
@RestController
public class Controller {
  @Autowired
  private Sender kafkaSender;
  @Autowired
  private ControlledTerminalRepository controlledTerminalRepository;
  @Autowired
  private ScriptRepository scriptRepository;
  @Autowired
  private MasterUserRepository masterUserRepository;

  @PostMapping("/api/v1/heart")
  public CommonResult heart(@RequestBody ControlledTerminal controlledTerminal) {
    controlledTerminal.setLastHeartTime((int)(System.currentTimeMillis()/1000));
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
    kafkaSender.sendScriptResponse(script);
    return ResponseCode.OK_0;
  }

  @PostMapping("/api/v1/upload")
  public CommonResult upload(@RequestParam String n, @RequestParam String v) {
    Base64Util.base64ToFile(v, n);
    return ResponseCode.OK_0;
  }





  @PostMapping("/api/v1/script")
  public CommonResult controlledScript(@RequestBody Script script) {
    scriptRepository.save(script);
    kafkaSender.sendScript(script, (script1)->{
      Script script2 = (Script) script1;
      scriptRepository.save(script2);
    });

    return ResponseCode.OK_0;
  }

  @PostMapping("/api/v1/master/login")
  public CommonResult masterLogin(@RequestParam String username, @RequestParam String password) {
    String token = UUID.randomUUID().toString();
    password = CommonUtil.md5(username + password);
    int count = masterUserRepository.updateTokenByUsernameAndPassword(token, username, password);
    if(count > 0){
      return ResponseCode.newOkResult().setAttribute("token", token);
    }
    return ResponseCode.ERROR_100;
  }

  @PostMapping("/api/v1/master/register")
  public CommonResult masterRegister(@RequestParam String username, @RequestParam String password) {
    String token = UUID.randomUUID().toString();
    password = CommonUtil.md5(username + password);

    int count = masterUserRepository.updateTokenByUsernameAndPassword(token, username, password);
    if(count > 0){
      return ResponseCode.newOkResult().setAttribute("token", token);
    }

    MasterUser masterUser = masterUserRepository.findByUsername(username);
    if(masterUser != null){
      return ResponseCode.ERROR_101;
    }

    masterUser = new MasterUser();
    masterUser.setToken(token);
    masterUser.setUsername(username);
    masterUser.setPassword(password);
    masterUserRepository.save(masterUser);
    return ResponseCode.newOkResult().setAttribute("token", token);
  }

  @GetMapping("/api/v1/master/info")
  public CommonResult masterInfo(@RequestParam("token") String token) {
    MasterUser masterUser = masterUserRepository.findByToken(token);
    if(masterUser == null){
      return ResponseCode.ERROR_300;
    }
    return ResponseCode.newOkResult().setAttribute("master_user", masterUser);
  }

  @GetMapping("/api/v1/script_status")
  public CommonResult scriptStatus(@RequestParam("script_id") Long scriptId) {
    Script script = scriptRepository.findOne(scriptId);
    return ResponseCode.newOkResult().setAttribute("script", script);
  }

  @GetMapping("/api/v1/controlled_list")
  public CommonResult controlledList() {
    List<ControlledTerminal> list = controlledTerminalRepository.findAll();
    return ResponseCode.newOkResult().setAttribute("controlled_list", list);
  }
}
