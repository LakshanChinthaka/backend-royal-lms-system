package com.chinthaka.backendroyallmssystem.batch;


import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.school.School;
import com.chinthaka.backendroyallmssystem.utils.Auditor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "batch")
@Builder
//@DynamicUpdate
public class Batch extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "active_statue", columnDefinition = "TINYINT default 1")
    private boolean activeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    public Batch(Long batchId, String code) {
        this.batchId = batchId;
        this.code = code;
    }

    public Batch(Long batchId, String code, Course course, School school) {
        this.batchId = batchId;
        this.code = code;
        this.course = course;
        this.school = school;
    }
}
