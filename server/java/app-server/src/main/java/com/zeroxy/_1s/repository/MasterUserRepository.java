package com.zeroxy._1s.repository;

import com.zeroxy._1s.domain.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MasterUserRepository extends JpaRepository<MasterUser, Long> {

    @Transactional
    @Modifying
    @Query("update com.zeroxy._1s.domain.MasterUser c set c.token = :token where c.username = :username and c.password = :password")
    int updateTokenByUsernameAndPassword(@Param("token") String token, @Param("username")String username, @Param("password")String password);

    MasterUser findByToken(String token);

    MasterUser findByUsername(String username);
}
