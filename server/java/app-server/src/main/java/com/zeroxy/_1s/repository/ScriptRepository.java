package com.zeroxy._1s.repository;

import com.zeroxy._1s.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findByDeviceNoAndStatus(String deviceNo, int status);

    List<Script> findByDeviceNoOrderById(String deviceNo);
}
