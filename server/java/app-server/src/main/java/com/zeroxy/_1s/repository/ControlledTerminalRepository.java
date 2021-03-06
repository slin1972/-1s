package com.zeroxy._1s.repository;

import com.zeroxy.CommonResult;
import com.zeroxy._1s.domain.ControlledTerminal;
import com.zeroxy.util.CommonUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public interface ControlledTerminalRepository extends JpaRepository<ControlledTerminal, Long> {

    ControlledTerminal findByDeviceNo(String deviceNo);

    List<ControlledTerminal> findByDeviceNoAndMaster(String deviceNo, String master);

    List<ControlledTerminal> findByMaster(String master);
}
