package com.chinthaka.backendroyallmssystem.subject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepo extends JpaRepository<Subject,Long> {

    Subject findBySubjectCode(String subjectSaveDTO);
}
