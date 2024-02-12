package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.subjectAssign.request.SubjectAssignToCourseDTO;

public interface ISubjectAssignService {

    String assignSubjectToCourse(SubjectAssignToCourseDTO subjectAssignToCourseDTO);
}
