package com.sms.sms.Service;

import com.sms.sms.DTO.common.SubjectDto;
import com.sms.sms.Entity.School;
import com.sms.sms.Entity.Subject;
import com.sms.sms.Repository.SchoolRepository;
import com.sms.sms.Repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SchoolRepository schoolRepository;

    public SubjectDto upsertSubject(Long subjectId, SubjectDto request) {
        Subject subject = subjectId == null
                ? new Subject()
                : subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found: " + subjectId));

        subject.setName(request.getName());
        subject.setSchool(getSchool(request.getSchoolId()));

        Subject saved = subjectRepository.save(subject);
        return new SubjectDto(saved.getId(), saved.getName(), saved.getSchool() != null ? saved.getSchool().getId() : null);
    }

    private School getSchool(Long schoolId) {
        if (schoolId == null) {
            return null;
        }
        return schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School not found: " + schoolId));
    }
}
