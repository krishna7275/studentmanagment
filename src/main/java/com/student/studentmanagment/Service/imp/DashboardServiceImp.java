package com.student.studentmanagment.Service.imp;

import com.student.studentmanagment.Service.DashboardService;
import com.student.studentmanagment.dto.DashboardStatsDTO;
import com.student.studentmanagment.repository.CourseRepository;
import com.student.studentmanagment.repository.EnrollmentRepository;
import com.student.studentmanagment.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImp implements DashboardService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public DashboardServiceImp(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {

        long totalStudent = studentRepository.count();
        long totalCourse = courseRepository.count();

        String topPerformingCourse = getTopPerformingCourse();

        YearMonth currentMonth = YearMonth.now(); // for the date System
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(LocalTime.MAX);

        long studentEnrolledThisMonth = enrollmentRepository.countDistinctStudentByEnrolledDateBetween(startDate,endDate);

        DashboardStatsDTO dashboardStatsDTO = new DashboardStatsDTO();
        dashboardStatsDTO.setTotalStudents(totalStudent);
        dashboardStatsDTO.setTotalCourses(totalCourse);
        dashboardStatsDTO.setTopPerformingCourse(topPerformingCourse);
        dashboardStatsDTO.setStudentsEnrolledThisMonth(studentEnrolledThisMonth);

        return dashboardStatsDTO;
    }

    private String getTopPerformingCourse() {
        return enrollmentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getCourse().getCourseName(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
