package com.sms.sms.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String rollNo;

    private LocalDate dateOfBirth;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @OneToOne(mappedBy = "student")
    private Parent parent;

    @OneToMany(mappedBy = "student")
    private List<Attendance> attendanceRecords;

    @OneToMany(mappedBy = "student")
    private List<Marks> marks;

    @OneToMany(mappedBy = "student")
    private List<Remark> remarks;
}
