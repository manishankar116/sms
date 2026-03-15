package com.sms.sms.Repository;

import com.sms.sms.Entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findBySchoolClassId(Long classId);

    List<Exam> findByStudentId(Long studentId);
}
