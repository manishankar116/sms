package com.sms.sms.Service;

import com.sms.sms.DTO.parent.ChildAcademicOverviewResponse;
import com.sms.sms.Entity.Exam;
import com.sms.sms.Entity.Marks;
import com.sms.sms.Entity.Parent;
import com.sms.sms.Entity.Student;
import com.sms.sms.Repository.AnnouncementRepository;
import com.sms.sms.Repository.AttendanceRepository;
import com.sms.sms.Repository.ExamRepository;
import com.sms.sms.Repository.HomeworkRepository;
import com.sms.sms.Repository.MarksRepository;
import com.sms.sms.Repository.ParentRepository;
import com.sms.sms.Repository.RemarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final AttendanceRepository attendanceRepository;
    private final HomeworkRepository homeworkRepository;
    private final MarksRepository marksRepository;
    private final RemarkRepository remarkRepository;
    private final AnnouncementRepository announcementRepository;
    private final ExamRepository examRepository;

    public ChildAcademicOverviewResponse getChildAcademicOverview(Long parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found: " + parentId));
        return buildOverview(parent);
    }

    public ChildAcademicOverviewResponse getChildAcademicOverviewByUserId(Long userId) {
        Parent parent = parentRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found for user id: " + userId));
        return buildOverview(parent);
    }

    private ChildAcademicOverviewResponse buildOverview(Parent parent) {
        Student student = parent.getStudent();
        if (student == null) {
            throw new IllegalArgumentException("Parent is not linked to any student");
        }

        List<Marks> marks = getMarksForStudent(student.getId());
        AcademicPerformance performance = calculateAcademicPerformance(marks);

        LinkedHashMap<Long, Exam> examsById = new LinkedHashMap<>();
        if (student.getSchoolClass() != null) {
            examRepository.findBySchoolClassId(student.getSchoolClass().getId()).forEach(exam -> examsById.put(exam.getId(), exam));
        }
        examRepository.findByStudentId(student.getId()).forEach(exam -> examsById.put(exam.getId(), exam));

        return new ChildAcademicOverviewResponse(
                DtoMapper.toStudentDto(student),
                attendanceRepository.findByStudentId(student.getId()).stream().map(DtoMapper::toAttendanceResponse).toList(),
                student.getSchoolClass() == null
                        ? List.of()
                        : homeworkRepository.findBySchoolClassId(student.getSchoolClass().getId()).stream().map(DtoMapper::toHomeworkResponse).toList(),
                List.copyOf(examsById.values()).stream().map(DtoMapper::toExamResponse).toList(),
                marks.stream().map(DtoMapper::toMarksResponse).toList(),
                performance.totalMarksObtained(),
                performance.totalMaxMarks(),
                performance.percentage(),
                performance.grade(),
                remarkRepository.findByStudentId(student.getId()).stream().map(DtoMapper::toRemarkResponse).toList(),
                student.getSchool() == null
                        ? List.of()
                        : announcementRepository.findBySchoolIdOrderByCreatedAtDesc(student.getSchool().getId())
                        .stream()
                        .map(DtoMapper::toAnnouncementResponse)
                        .toList()
        );
    }

    private List<Marks> getMarksForStudent(Long studentId) {
        LinkedHashMap<Long, Marks> marksById = new LinkedHashMap<>();
        marksRepository.findByStudentId(studentId).forEach(mark -> marksById.put(mark.getId(), mark));
        marksRepository.findByExamStudentId(studentId).forEach(mark -> marksById.put(mark.getId(), mark));
        return List.copyOf(marksById.values());
    }

    private AcademicPerformance calculateAcademicPerformance(List<Marks> marks) {
        int totalMarksObtained = marks.stream()
                .filter(mark -> mark.getMarks() != null && mark.getMaxMarks() != null)
                .mapToInt(Marks::getMarks)
                .sum();
        int totalMaxMarks = marks.stream()
                .filter(mark -> mark.getMarks() != null && mark.getMaxMarks() != null)
                .mapToInt(Marks::getMaxMarks)
                .sum();

        if (totalMaxMarks == 0) {
            return new AcademicPerformance(totalMarksObtained, totalMaxMarks, 0.0, "N/A");
        }

        double percentage = Math.round((totalMarksObtained * 10000.0) / totalMaxMarks) / 100.0;
        return new AcademicPerformance(totalMarksObtained, totalMaxMarks, percentage, calculateGrade(percentage));
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) {
            return "A+";
        }
        if (percentage >= 80) {
            return "A";
        }
        if (percentage >= 70) {
            return "B";
        }
        if (percentage >= 60) {
            return "C";
        }
        if (percentage >= 50) {
            return "D";
        }
        return "F";
    }

    private record AcademicPerformance(int totalMarksObtained, int totalMaxMarks, double percentage, String grade) {
    }
}
