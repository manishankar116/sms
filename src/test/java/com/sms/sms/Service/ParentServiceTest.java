package com.sms.sms.Service;

import com.sms.sms.DTO.parent.ChildAcademicOverviewResponse;
import com.sms.sms.Entity.Announcement;
import com.sms.sms.Entity.Parent;
import com.sms.sms.Entity.School;
import com.sms.sms.Entity.Student;
import com.sms.sms.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParentServiceTest {

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private HomeworkRepository homeworkRepository;

    @Mock
    private MarksRepository marksRepository;

    @Mock
    private RemarkRepository remarkRepository;

    @Mock
    private AnnouncementRepository announcementRepository;

    private ParentService parentService;

    @BeforeEach
    void setUp() {
        parentService = new ParentService(
                parentRepository,
                attendanceRepository,
                homeworkRepository,
                marksRepository,
                remarkRepository,
                announcementRepository
        );
    }

    @Test
    void getChildAcademicOverview_includesSchoolAnnouncements() {
        School school = new School();
        school.setId(20L);

        Student student = new Student();
        student.setId(10L);
        student.setSchool(school);

        Parent parent = new Parent();
        parent.setId(1L);
        parent.setStudent(student);

        Announcement latest = new Announcement();
        latest.setId(200L);
        latest.setTitle("Latest");
        latest.setCreatedAt(LocalDateTime.now());
        latest.setSchool(school);

        Announcement older = new Announcement();
        older.setId(100L);
        older.setTitle("Older");
        older.setCreatedAt(LocalDateTime.now().minusDays(1));
        older.setSchool(school);

        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(attendanceRepository.findByStudentId(10L)).thenReturn(List.of());
        when(marksRepository.findByStudentId(10L)).thenReturn(List.of());
        when(remarkRepository.findByStudentId(10L)).thenReturn(List.of());
        when(announcementRepository.findBySchoolIdOrderByCreatedAtDesc(20L)).thenReturn(List.of(latest, older));
        when(homeworkRepository.findBySchoolClassId(anyLong())).thenReturn(List.of());

        ChildAcademicOverviewResponse overview = parentService.getChildAcademicOverview(1L);

        assertNotNull(overview.getAnnouncements());
        assertEquals(2, overview.getAnnouncements().size());
        assertEquals(200L, overview.getAnnouncements().get(0).getId());
        assertEquals(100L, overview.getAnnouncements().get(1).getId());
    }
}
