package com.sms.sms.Service;

import com.sms.sms.DTO.parent.ChildAcademicOverviewResponse;
import com.sms.sms.Entity.Marks;
import com.sms.sms.Entity.Parent;
import com.sms.sms.Entity.Student;
import com.sms.sms.Repository.*;
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

        return new ChildAcademicOverviewResponse(
                DtoMapper.toStudentDto(student),
                attendanceRepository.findByStudentId(student.getId()).stream().map(DtoMapper::toAttendanceResponse).toList(),
                student.getSchoolClass() == null
                        ? java.util.List.of()
                        : homeworkRepository.findBySchoolClassId(student.getSchoolClass().getId()).stream().map(DtoMapper::toHomeworkResponse).toList(),
                marks.stream().map(DtoMapper::toMarksResponse).toList(),
                remarkRepository.findByStudentId(student.getId()).stream().map(DtoMapper::toRemarkResponse).toList(),
                student.getSchool() == null
                        ? java.util.List.of()
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
}
