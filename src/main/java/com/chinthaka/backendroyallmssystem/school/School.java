package com.chinthaka.backendroyallmssystem.school;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.course.Course;
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
@Table(name = "school")
public class School extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolID;

    @Column(name = "school_name",nullable = false)
    private String schoolName;

    @Column(name = "code", unique = true,nullable = false)
    private String schoolCode;

    @Column(name = "active_statue")
    private boolean activeStatus = true;

    @OneToMany(mappedBy="school", cascade = CascadeType.ALL)
    private List<Course> courseList;

    @OneToMany(mappedBy="school", cascade = CascadeType.ALL)
    private List<Batch> batchList;


    public School(Long schoolID, String schoolCode, String schoolName) {
        this.schoolID = schoolID;
        this.schoolCode = schoolCode;
        this.schoolName = schoolName;

    }

}
