package com.sms.sms.Service;

import com.sms.sms.Repository.ParentRepository;
import com.sms.sms.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;

    public void assertParentOwnsStudent(String username, Long studentId) {
        boolean isOwner = parentRepository.existsByUserUsernameAndStudentId(username, studentId);
        if (!isOwner) {
            throw new AccessDeniedException("Parents can only access their own child's data");
        }
    }

    public void assertTeacherAssignedToStudent(String username, Long studentId) {
        boolean isAssigned = teacherRepository.existsByUserUsernameAndClassTeacherOfStudentsId(username, studentId);
        if (!isAssigned) {
            throw new AccessDeniedException("Teachers can only access assigned classes/students");
        }
    }
}
