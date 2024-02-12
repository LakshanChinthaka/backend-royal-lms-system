package com.chinthaka.backendroyallmssystem.course;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.school.School;
import com.chinthaka.backendroyallmssystem.utils.Auditor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course")
public class Course extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "total_credit", nullable = false)
    private int totalCredit;

    @Column(name = "total_hours", nullable = false)
    private int totalHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type courseType;

    @Column(name = "fees")
    private double fees;

    @Column(name = "duration")
    private String duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "medium")
    private Medium medium;

    @Column(name = "active_statue", columnDefinition = "TINYINT default 1")
    private boolean active_statue;

    @OneToMany(mappedBy="course",cascade = CascadeType.ALL)
    private List<Batch> batchList;

    @ManyToOne
    @JoinColumn(name="school_id")
    private School school;
}
