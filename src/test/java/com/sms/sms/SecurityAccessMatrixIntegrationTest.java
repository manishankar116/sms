package com.sms.sms;

import com.sms.sms.Config.SecurityConfig;
import com.sms.sms.Controller.AdminController;
import com.sms.sms.Controller.ParentController;
import com.sms.sms.Controller.TeacherController;
import com.sms.sms.Security.JwtAuthenticationFilter;
import com.sms.sms.Service.StudentAccessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AdminController.class, TeacherController.class, ParentController.class})
@AutoConfigureMockMvc(addFilters = true)
@Import(SecurityConfig.class)
class SecurityAccessMatrixIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentAccessService studentAccessService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void adminEndpoints_allowAdmin_andForbidOthers() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/admin/dashboard")
                        .with(user("teacher").roles("TEACHER")))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/admin/dashboard")
                        .with(user("parent").roles("PARENT")))
                .andExpect(status().isForbidden());
    }

    @Test
    void teacherEndpoints_allowTeacher_andForbidOthers() throws Exception {
        when(studentAccessService.getStudentDataForTeacher(anyString(), anyLong())).thenReturn("ok");

        mockMvc.perform(get("/api/teacher/students/1")
                        .with(user("teacher").roles("TEACHER")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/teacher/students/1")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/teacher/students/1")
                        .with(user("parent").roles("PARENT")))
                .andExpect(status().isForbidden());
    }

    @Test
    void parentEndpoints_allowParent_andForbidOthers() throws Exception {
        when(studentAccessService.getStudentProgressForParent(anyString(), anyLong())).thenReturn("ok");

        mockMvc.perform(get("/api/parent/children/1/progress")
                        .with(user("parent").roles("PARENT")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/parent/children/1/progress")
                        .with(user("teacher").roles("TEACHER")))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/parent/children/1/progress")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    void sensitiveMethodAnnotations_enforceRoleChecks() throws Exception {
        mockMvc.perform(post("/api/admin/announcements")
                        .content("test")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/admin/announcements")
                        .content("test")
                        .with(user("teacher").roles("TEACHER")))
                .andExpect(status().isForbidden());

        when(studentAccessService.updateRemark(anyString(), anyLong(), anyLong(), anyString())).thenReturn("ok");
        mockMvc.perform(put("/api/teacher/students/1/remarks/10")
                        .contentType("application/json")
                        .content("{\"remark\":\"updated\"}")
                        .with(user("teacher").roles("TEACHER")))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/teacher/students/1/remarks/10")
                        .contentType("application/json")
                        .content("{\"remark\":\"updated\"}")
                        .with(user("parent").roles("PARENT")))
                .andExpect(status().isForbidden());

        when(studentAccessService.getStudentProgressForParent(anyString(), anyLong())).thenReturn("ok");
        mockMvc.perform(put("/api/parent/children/1/contact")
                        .with(user("parent").roles("PARENT")))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/parent/children/1/contact")
                        .with(user("teacher").roles("TEACHER")))
                .andExpect(status().isForbidden());
    }
}
