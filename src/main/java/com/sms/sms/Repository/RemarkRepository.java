package com.sms.sms.Repository;

import com.sms.sms.Entity.Remark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemarkRepository extends JpaRepository<Remark, Long> {
    List<Remark> findByStudentId(Long studentId);
}
