package com.sms.sms.Repository;

import com.sms.sms.Entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    List<Homework> findBySchoolClassId(Long schoolClassId);
}
