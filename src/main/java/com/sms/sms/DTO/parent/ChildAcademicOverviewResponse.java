package com.sms.sms.DTO.parent;

import com.sms.sms.DTO.common.StudentDto;
import com.sms.sms.DTO.teacher.AnnouncementResponse;
import com.sms.sms.DTO.teacher.AttendanceResponse;
import com.sms.sms.DTO.teacher.ExamResponse;
import com.sms.sms.DTO.teacher.HomeworkResponse;
import com.sms.sms.DTO.teacher.MarksResponse;
import com.sms.sms.DTO.teacher.RemarkResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildAcademicOverviewResponse {
    private StudentDto student;
    private List<AttendanceResponse> attendance;
    private List<HomeworkResponse> homework;
    private List<ExamResponse> exams;
    private List<MarksResponse> marks;
    private Integer totalMarksObtained;
    private Integer totalMaxMarks;
    private Double percentage;
    private String grade;
    private List<RemarkResponse> remarks;
    private List<AnnouncementResponse> announcements;
}
