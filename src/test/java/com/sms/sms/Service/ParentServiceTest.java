package com.sms.sms.Service;

import com.sms.sms.DTO.parent.ChildAcademicOverviewResponse;
import com.sms.sms.Entity.Announcement;
import com.sms.sms.Entity.Exam;
import com.sms.sms.Entity.Marks;
import com.sms.sms.Entity.Parent;
import com.sms.sms.Entity.School;
import com.sms.sms.Entity.Student;
import com.sms.sms.Entity.Subject;
import com.sms.sms.Repository.AnnouncementRepository;
import com.sms.sms.Repository.AttendanceRepository;
import com.sms.sms.Repository.ExamRepository;
import com.sms.sms.Repository.HomeworkRepository;
import com.sms.sms.Repository.MarksRepository;
import com.sms.sms.Repository.ParentRepository;
import com.sms.sms.Repository.RemarkRepository;
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

    @Mock
    private ExamRepository examRepository;

    private ParentService parentService;

    @BeforeEach
    void setUp() {
        parentService = new ParentService(
                parentRepository,
                attendanceRepository,
                homeworkRepository,
                marksRepository,
                remarkRepository,
                announcementRepository,
                examRepository
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
        when(marksRepository.findByExamStudentId(10L)).thenReturn(List.of());
        when(remarkRepository.findByStudentId(10L)).thenReturn(List.of());
        when(announcementRepository.findBySchoolIdOrderByCreatedAtDesc(20L)).thenReturn(List.of(latest, older));
        when(examRepository.findByStudentId(10L)).thenReturn(List.of());
        when(homeworkRepository.findBySchoolClassId(anyLong())).thenReturn(List.of());

        ChildAcademicOverviewResponse overview = parentService.getChildAcademicOverview(1L);

        assertNotNull(overview.getAnnouncements());
        assertEquals(2, overview.getAnnouncements().size());
        assertEquals(200L, overview.getAnnouncements().get(0).getId());
        assertEquals(100L, overview.getAnnouncements().get(1).getId());
        assertEquals(0.0, overview.getPercentage());
        assertEquals("N/A", overview.getGrade());
    }

    @Test
    void getChildAcademicOverview_includesSubjectNameAndPerformanceSummary() {
        School school = new School();
        school.setId(20L);

        Student student = new Student();
        student.setId(10L);
        student.setName("John Student");
        student.setSchool(school);

        Parent parent = new Parent();
        parent.setId(1L);
        parent.setStudent(student);

        Subject maths = new Subject();
        maths.setId(5L);
        maths.setName("Mathematics");

        Subject science = new Subject();
        science.setId(6L);
        science.setName("Science");

        Exam mathsExam = new Exam();
        mathsExam.setId(7L);
        mathsExam.setSubject(maths);
        mathsExam.setStudent(student);

        Exam scienceExam = new Exam();
        scienceExam.setId(8L);
        scienceExam.setSubject(science);
        scienceExam.setStudent(student);

        Marks mathsMarks = new Marks();
        mathsMarks.setId(11L);
        mathsMarks.setMarks(91);
        mathsMarks.setMaxMarks(100);
        mathsMarks.setStudent(student);
        mathsMarks.setExam(mathsExam);
        mathsMarks.setSubject(maths);

        Marks scienceMarks = new Marks();
        scienceMarks.setId(12L);
        scienceMarks.setMarks(78);
        scienceMarks.setMaxMarks(100);
        scienceMarks.setStudent(student);
        scienceMarks.setExam(scienceExam);
        scienceMarks.setSubject(science);

        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(attendanceRepository.findByStudentId(10L)).thenReturn(List.of());
        when(marksRepository.findByStudentId(10L)).thenReturn(List.of(mathsMarks, scienceMarks));
        when(marksRepository.findByExamStudentId(10L)).thenReturn(List.of(mathsMarks, scienceMarks));
        when(remarkRepository.findByStudentId(10L)).thenReturn(List.of());
        when(announcementRepository.findBySchoolIdOrderByCreatedAtDesc(20L)).thenReturn(List.of());
        when(examRepository.findByStudentId(10L)).thenReturn(List.of(mathsExam, scienceExam));

        ChildAcademicOverviewResponse overview = parentService.getChildAcademicOverview(1L);

        assertEquals(2, overview.getMarks().size());
        assertEquals(5L, overview.getMarks().get(0).getSubjectId());
        assertEquals("Mathematics", overview.getMarks().get(0).getSubjectName());
        assertEquals(169, overview.getTotalMarksObtained());
        assertEquals(200, overview.getTotalMaxMarks());
        assertEquals(84.5, overview.getPercentage());
        assertEquals("A", overview.getGrade());
    }
}
