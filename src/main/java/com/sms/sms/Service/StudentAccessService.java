package com.sms.sms.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentAccessService {

    private final AccessControlService accessControlService;

    public String getStudentProgressForParent(String username, Long studentId) {
        accessControlService.assertParentOwnsStudent(username, studentId);
        return "Progress data for student " + studentId;
    }

    public String getStudentDataForTeacher(String username, Long studentId) {
        accessControlService.assertTeacherAssignedToStudent(username, studentId);
        return "Student data for " + studentId;
    }

    public String createRemark(String username, Long studentId, String remark) {
        accessControlService.assertTeacherAssignedToStudent(username, studentId);
        return "Created remark for student " + studentId;
    }

    public String updateRemark(String username, Long studentId, Long remarkId, String remark) {
        accessControlService.assertTeacherAssignedToStudent(username, studentId);
        return "Updated remark " + remarkId + " for student " + studentId;
    }

    public String deleteRemark(String username, Long studentId, Long remarkId) {
        accessControlService.assertTeacherAssignedToStudent(username, studentId);
        return "Deleted remark " + remarkId + " for student " + studentId;
    }
}
