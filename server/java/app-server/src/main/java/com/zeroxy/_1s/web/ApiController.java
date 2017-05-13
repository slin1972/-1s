package com.zeroxy._1s.web;

import com.zeroxy.CommonResult;
import com.zeroxy._1s.domain.ControlledTerminal;
import com.zeroxy._1s.repository.ControlledTerminalRepository;
import com.zeroxy._1s.result.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作用：
 * ① 测试服务实例的相关内容
 * ② 为后来的服务做提供
 * @author slin
 */
@RestController
@RequestMapping("/api/v1")
public class ApiController {
  @Autowired
  private ControlledTerminalRepository controlledTerminalRepository;

  @GetMapping("/heart")
  public CommonResult heart(@RequestParam(required = false) String clientid,
                            ControlledTerminal controlledTerminal) {
    controlledTerminalRepository.save(controlledTerminal);
    return ResponseCode.OK_0;

  }
}
