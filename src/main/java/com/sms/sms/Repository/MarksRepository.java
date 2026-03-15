package com.sms.sms.Repository;

import com.sms.sms.Entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {
    List<Marks> findByStudentId(Long studentId);

    List<Marks> findByExamStudentId(Long studentId);
}
