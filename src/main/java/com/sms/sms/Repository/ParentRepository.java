package com.sms.sms.Repository;

import com.sms.sms.Entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findByUserId(Long userId);

    boolean existsByUserUsernameAndStudentId(String username, Long studentId);

    boolean existsByUser_UsernameAndStudent_Id(String username, Long studentId);
}
