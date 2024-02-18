package com.chinthaka.backendroyallmssystem.course;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.school.School;
import com.chinthaka.backendroyallmssystem.utils.Auditor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DynamicUpdate
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course")
public class Course extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "total_credit")
    private int totalCredit;

    @Column(name = "total_hours")
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

    @JsonIgnore
    @OneToMany(mappedBy="course",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Batch> batchList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="school_id")
    private School school;
}
