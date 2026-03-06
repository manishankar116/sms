package com.sms.sms.Service;

import com.sms.sms.DTO.admin.ProvisionCredentialRequest;
import com.sms.sms.DTO.common.*;
import com.sms.sms.Entity.*;
import com.sms.sms.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SchoolDto upsertSchool(Long schoolId, SchoolDto request) {
        School school = schoolId == null
                ? new School()
                : schoolRepository.findById(schoolId).orElseThrow(() -> new IllegalArgumentException("School not found: " + schoolId));
        school.setName(request.getName());
        school.setAddress(request.getAddress());
        school.setEmail(request.getEmail());
        school.setPhone(request.getPhone());
        return DtoMapper.toSchoolDto(schoolRepository.save(school));
    }

    public TeacherDto upsertTeacher(Long teacherId, TeacherDto request) {
        Teacher teacher = teacherId == null
                ? new Teacher()
                : teacherRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher not found: " + teacherId));
        teacher.setName(request.getName());
        teacher.setSubjectSpecialization(request.getSubjectSpecialization());
        teacher.setPhone(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacher.setSchool(getSchool(request.getSchoolId()));
        return DtoMapper.toTeacherDto(teacherRepository.save(teacher));
    }

    public ParentDto upsertParent(Long parentId, ParentDto request) {
        Parent parent = parentId == null
                ? new Parent()
                : parentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent not found: " + parentId));
        parent.setName(request.getName());
        parent.setPhone(request.getPhone());
        parent.setEmail(request.getEmail());
        parent.setRelation(request.getRelation());
        parent.setStudent(getStudent(request.getStudentId()));
        return DtoMapper.toParentDto(parentRepository.save(parent));
    }

    public StudentDto upsertStudent(Long studentId, StudentDto request) {
        Student student = studentId == null
                ? new Student()
                : studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
        student.setName(request.getName());
        student.setRollNo(request.getRollNo());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setSchool(getSchool(request.getSchoolId()));
        student.setSchoolClass(getClassEntity(request.getClassId()));
        return DtoMapper.toStudentDto(studentRepository.save(student));
    }

    public SchoolClassDto upsertClass(Long classId, SchoolClassDto request) {
        SchoolClass schoolClass = classId == null
                ? new SchoolClass()
                : schoolClassRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("Class not found: " + classId));
        schoolClass.setClassName(request.getClassName());
        schoolClass.setSection(request.getSection());
        schoolClass.setSchool(getSchool(request.getSchoolId()));
        schoolClass.setClassTeacher(getTeacher(request.getClassTeacherId()));
        return DtoMapper.toClassDto(schoolClassRepository.save(schoolClass));
    }

    public UserCredentialResponse provisionTeacherCredentials(Long teacherId, ProvisionCredentialRequest request) {
        if (request.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("Role must be TEACHER for teacher credentials");
        }
        Teacher teacher = getTeacher(teacherId);
        User user = createUser(request, teacher.getSchool());
        teacher.setUser(user);
        teacherRepository.save(teacher);
        return new UserCredentialResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public UserCredentialResponse provisionParentCredentials(Long parentId, ProvisionCredentialRequest request) {
        if (request.getRole() != Role.PARENT) {
            throw new IllegalArgumentException("Role must be PARENT for parent credentials");
        }
        Parent parent = getParent(parentId);
        School school = parent.getStudent() != null ? parent.getStudent().getSchool() : null;
        User user = createUser(request, school);
        parent.setUser(user);
        parentRepository.save(parent);
        return new UserCredentialResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    private User createUser(ProvisionCredentialRequest request, School school) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setSchool(school);
        return userRepository.save(user);
    }

    private School getSchool(Long schoolId) {
        if (schoolId == null) {
            return null;
        }
        return schoolRepository.findById(schoolId).orElseThrow(() -> new IllegalArgumentException("School not found: " + schoolId));
    }

    private Teacher getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher not found: " + teacherId));
    }

    private Parent getParent(Long parentId) {
        return parentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent not found: " + parentId));
    }

    private Student getStudent(Long studentId) {
        if (studentId == null) {
            return null;
        }
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
    }

    private SchoolClass getClassEntity(Long classId) {
        if (classId == null) {
            return null;
        }
        return schoolClassRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("Class not found: " + classId));
    }
}
