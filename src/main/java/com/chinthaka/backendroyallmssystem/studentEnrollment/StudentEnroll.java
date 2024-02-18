package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.utils.Auditor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Table(name = "student_enroll")
@Builder
public class StudentEnroll extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollId;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "student_id",nullable = false)
//    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "batch_id",nullable = false)
    private Batch batch;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;

    public StudentEnroll(Student student, Batch batch, Course course) {
        this.student = student;
        this.batch=batch;
        this.course = course;
    }
}
