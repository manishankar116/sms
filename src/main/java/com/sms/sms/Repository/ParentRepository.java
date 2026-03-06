package com.sms.sms.Repository;

import com.sms.sms.Entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    boolean existsByUserUsernameAndStudentId(String username, Long studentId);
}
