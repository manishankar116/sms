package com.sms.sms.Repository;

import com.sms.sms.Entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
