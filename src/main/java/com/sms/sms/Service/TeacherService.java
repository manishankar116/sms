package com.sms.sms.Service;

import com.sms.sms.DTO.common.StudentDto;
import com.sms.sms.DTO.teacher.*;
import com.sms.sms.Entity.*;
import com.sms.sms.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final AttendanceRepository attendanceRepository;
    private final HomeworkRepository homeworkRepository;
    private final MarksRepository marksRepository;
    private final RemarkRepository remarkRepository;
    private final AnnouncementRepository announcementRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;
    private final SchoolRepository schoolRepository;

    public AttendanceResponse recordAttendance(AttendanceRequest request) {
        Attendance attendance = new Attendance();
        attendance.setDate(request.getDate());
        attendance.setStatus(request.getStatus());
        attendance.setStudent(getStudent(request.getStudentId()));
        attendance.setSchoolClass(getClassEntity(request.getClassId()));
        attendance.setMarkedBy(getTeacher(request.getTeacherId()));
        return DtoMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }

    public HomeworkResponse createHomework(HomeworkRequest request) {
        Homework homework = new Homework();
        homework.setTitle(request.getTitle());
        homework.setDescription(request.getDescription());
        homework.setAssignedDate(request.getAssignedDate());
        homework.setDueDate(request.getDueDate());
        homework.setTeacher(getTeacher(request.getTeacherId()));
        homework.setSchoolClass(getClassEntity(request.getClassId()));
        homework.setSubject(getSubject(request.getSubjectId()));
        return DtoMapper.toHomeworkResponse(homeworkRepository.save(homework));
    }

    public MarksResponse upsertMarks(Long marksId, MarksRequest request) {
        Marks marks = marksId == null
                ? new Marks()
                : marksRepository.findById(marksId).orElseThrow(() -> new IllegalArgumentException("Marks not found: " + marksId));

        Exam exam = getExam(request.getExamId());

        marks.setMarks(request.getMarks());
        marks.setMaxMarks(request.getMaxMarks());
        marks.setStudent(getStudent(request.getStudentId()));
        marks.setExam(exam);
        marks.setGrade(request.getGrade());
        marks.setStatus(request.getStatus());
        marks.setTeacher(getTeacher(request.getTeacherId()));
        marks.setSubject(exam.getSubject());
        return DtoMapper.toMarksResponse(marksRepository.save(marks));
    }

    public RemarkResponse upsertRemark(Long remarkId, RemarkRequest request) {
        Remark remark = remarkId == null
                ? new Remark()
                : remarkRepository.findById(remarkId).orElseThrow(() -> new IllegalArgumentException("Remark not found: " + remarkId));

        remark.setRemark(request.getRemark());
        remark.setStudent(getStudent(request.getStudentId()));
        remark.setTeacher(getTeacher(request.getTeacherId()));
        if (remark.getCreatedAt() == null) {
            remark.setCreatedAt(LocalDateTime.now());
        }
        return DtoMapper.toRemarkResponse(remarkRepository.save(remark));
    }

    public ExamResponse upsertExam(Long examId, ExamRequest request) {
        Exam exam = examId == null
                ? new Exam()
                : examRepository.findById(examId).orElseThrow(() -> new IllegalArgumentException("Exam not found: " + examId));

        exam.setExamName(request.getExamName());
        exam.setExamDate(request.getExamDate());
        exam.setSchool(getSchool(request.getSchoolId()));
        exam.setSchoolClass(getClassEntity(request.getClassId()));
        exam.setSubject(getSubject(request.getSubjectId()));
        exam.setStudent(request.getStudentId() == null ? null : getStudent(request.getStudentId()));
        exam.setTeacher(request.getTeacherId() == null ? null : getTeacher(request.getTeacherId()));
        return DtoMapper.toExamResponse(examRepository.save(exam));
    }

    public AnnouncementResponse upsertAnnouncement(Long announcementId, AnnouncementRequest request) {
        Announcement announcement = announcementId == null
                ? new Announcement()
                : announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + announcementId));

        announcement.setTitle(request.getTitle());
        announcement.setDescription(request.getDescription());
        announcement.setSchool(getSchool(request.getSchoolId()));
        if (announcement.getCreatedAt() == null) {
            announcement.setCreatedAt(LocalDateTime.now());
        }
        return DtoMapper.toAnnouncementResponse(announcementRepository.save(announcement));
    }

    public List<StudentDto> listStudentsByClass(Long classId) {
        return studentRepository.findBySchoolClassId(classId)
                .stream()
                .map(DtoMapper::toStudentDto)
                .toList();
    }

    private Student getStudent(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
    }

    private Teacher getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher not found: " + teacherId));
    }

    private SchoolClass getClassEntity(Long classId) {
        return schoolClassRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("Class not found: " + classId));
    }

    private Subject getSubject(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found: " + subjectId));
    }

    private Exam getExam(Long examId) {
        return examRepository.findById(examId).orElseThrow(() -> new IllegalArgumentException("Exam not found: " + examId));
    }

    private School getSchool(Long schoolId) {
        return schoolRepository.findById(schoolId).orElseThrow(() -> new IllegalArgumentException("School not found: " + schoolId));
    }
}
