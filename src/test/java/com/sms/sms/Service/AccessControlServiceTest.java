package com.sms.sms.Service;

import com.sms.sms.Repository.ParentRepository;
import com.sms.sms.Repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessControlServiceTest {

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    private AccessControlService accessControlService;

    @BeforeEach
    void setUp() {
        accessControlService = new AccessControlService(parentRepository, teacherRepository);
    }

    @Test
    void assertParentOwnsStudent_allowsOwner() {
        when(parentRepository.existsByUserUsernameAndStudentId("parent1", 101L)).thenReturn(true);

        assertDoesNotThrow(() -> accessControlService.assertParentOwnsStudent("parent1", 101L));
    }

    @Test
    void assertParentOwnsStudent_deniesNonOwner() {
        when(parentRepository.existsByUserUsernameAndStudentId("parent1", 101L)).thenReturn(false);

        assertThrows(AccessDeniedException.class,
                () -> accessControlService.assertParentOwnsStudent("parent1", 101L));
    }

    @Test
    void assertTeacherAssignedToStudent_allowsAssignedTeacher() {
        when(teacherRepository.existsByUserUsernameAndClassTeacherOfStudentsId("teacher1", 202L)).thenReturn(true);

        assertDoesNotThrow(() -> accessControlService.assertTeacherAssignedToStudent("teacher1", 202L));
    }

    @Test
    void assertTeacherAssignedToStudent_deniesUnassignedTeacher() {
        when(teacherRepository.existsByUserUsernameAndClassTeacherOfStudentsId("teacher1", 202L)).thenReturn(false);

        assertThrows(AccessDeniedException.class,
                () -> accessControlService.assertTeacherAssignedToStudent("teacher1", 202L));
    }
}
