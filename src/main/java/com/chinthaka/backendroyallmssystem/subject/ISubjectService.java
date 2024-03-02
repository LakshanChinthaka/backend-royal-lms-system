package com.chinthaka.backendroyallmssystem.subject;


import com.chinthaka.backendroyallmssystem.subject.request.SubjectAddDTO;
import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISubjectService {

    String addSubject(SubjectAddDTO subjectAddDTO);

    SubjectDTO subjectGetById(long subjectId);

    String deleteSubject(long subjectId);

    String editSubject(long subjectId, SubjectDTO subjectDTO);

    Page<SubjectDTO> getAllSubject(Pageable pageable);
}
