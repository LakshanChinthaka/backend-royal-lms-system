package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.subject.Subject;
import com.chinthaka.backendroyallmssystem.utils.Auditor;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "subject_assign")
public class SubjectAssignToCourse extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assign_id")
    private long assignId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subjects;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;
}
