package com.firstmilestone.first.repository;

import com.firstmilestone.first.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface CrudRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);
    User findByMobile(String mobile);
    User findByEmail(String email);
    boolean existsByUserName(String username);
    Integer deleteByUserName(String username);
}
