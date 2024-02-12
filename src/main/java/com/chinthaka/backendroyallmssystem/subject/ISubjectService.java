package com.chinthaka.backendroyallmssystem.subject;


import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;

public interface ISubjectService {

    String addSubject(SubjectDTO subjectDTO);

    SubjectDTO subjectGetById(long subjectId);

    String deleteSubject(long subjectId);

    String editSubject(long subjectId, SubjectDTO subjectDTO);
}
