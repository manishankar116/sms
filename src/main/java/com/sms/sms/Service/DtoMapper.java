package com.sms.sms.Service;

import com.sms.sms.DTO.common.*;
import com.sms.sms.DTO.teacher.*;
import com.sms.sms.Entity.*;

public final class DtoMapper {

    private DtoMapper() {
    }

    public static SchoolDto toSchoolDto(School school) {
        return new SchoolDto(school.getId(), school.getName(), school.getAddress(), school.getEmail(), school.getPhone());
    }

    public static TeacherDto toTeacherDto(Teacher teacher) {
        return new TeacherDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getSubjectSpecialization(),
                teacher.getPhone(),
                teacher.getEmail(),
                teacher.getSchool() != null ? teacher.getSchool().getId() : null,
                teacher.getUser() != null ? teacher.getUser().getId() : null
        );
    }

    public static ParentDto toParentDto(Parent parent) {
        return new ParentDto(
                parent.getId(),
                parent.getName(),
                parent.getPhone(),
                parent.getEmail(),
                parent.getRelation(),
                parent.getStudent() != null ? parent.getStudent().getId() : null,
                parent.getUser() != null ? parent.getUser().getId() : null
        );
    }

    public static StudentDto toStudentDto(Student student) {
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getRollNo(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getSchool() != null ? student.getSchool().getId() : null,
                student.getSchoolClass() != null ? student.getSchoolClass().getId() : null
        );
    }

    public static SchoolClassDto toClassDto(SchoolClass schoolClass) {
        return new SchoolClassDto(
                schoolClass.getId(),
                schoolClass.getClassName(),
                schoolClass.getSection(),
                schoolClass.getSchool() != null ? schoolClass.getSchool().getId() : null,
                schoolClass.getClassTeacher() != null ? schoolClass.getClassTeacher().getId() : null
        );
    }

    public static AttendanceResponse toAttendanceResponse(Attendance attendance) {
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getDate(),
                attendance.getStatus(),
                attendance.getStudent() != null ? attendance.getStudent().getId() : null,
                attendance.getSchoolClass() != null ? attendance.getSchoolClass().getId() : null,
                attendance.getMarkedBy() != null ? attendance.getMarkedBy().getId() : null
        );
    }

    public static HomeworkResponse toHomeworkResponse(Homework homework) {
        return new HomeworkResponse(
                homework.getId(),
                homework.getTitle(),
                homework.getDescription(),
                homework.getAssignedDate(),
                homework.getDueDate(),
                homework.getTeacher() != null ? homework.getTeacher().getId() : null,
                homework.getSchoolClass() != null ? homework.getSchoolClass().getId() : null,
                homework.getSubject() != null ? homework.getSubject().getId() : null
        );
    }

    public static MarksResponse toMarksResponse(Marks marks) {
        return new MarksResponse(
                marks.getId(),
                marks.getMarks(),
                marks.getMaxMarks(),
                marks.getStudent() != null ? marks.getStudent().getId() : null,
                marks.getExam() != null ? marks.getExam().getId() : null,
                marks.getTeacher() != null ? marks.getTeacher().getId() : null
        );
    }

    public static RemarkResponse toRemarkResponse(Remark remark) {
        return new RemarkResponse(
                remark.getId(),
                remark.getRemark(),
                remark.getCreatedAt(),
                remark.getStudent() != null ? remark.getStudent().getId() : null,
                remark.getTeacher() != null ? remark.getTeacher().getId() : null
        );
    }
}
