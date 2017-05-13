package com.zeroxy._1s.web;

import com.zeroxy.CommonResult;
import com.zeroxy._1s.domain.ControlledTerminal;
import com.zeroxy._1s.repository.ControlledTerminalRepository;
import com.zeroxy._1s.result.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 作用：
 * ① 测试服务实例的相关内容
 * ② 为后来的服务做提供
 * @author slin
 */
@RestController
public class Controller {
  @Autowired
  private ControlledTerminalRepository controlledTerminalRepository;

  @PostMapping("/api/v1/heart")
  public CommonResult heart(@RequestBody ControlledTerminal controlledTerminal) {
    controlledTerminal.setLastHeartTime((int)(System.currentTimeMillis()/1000));
    controlledTerminalRepository.save(controlledTerminal);
    return ResponseCode.OK_0;

  }
  @GetMapping("/controlled/list")
  public ModelAndView controlledList(@RequestParam String key) {
    if(!key.equals("test321")){
      return null ;
    }
    List<ControlledTerminal> list = controlledTerminalRepository.findAll();
    ModelAndView modelAndView = new ModelAndView("list");
    modelAndView.addObject("list", list);
    return modelAndView;

  }
}
