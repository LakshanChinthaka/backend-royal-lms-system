package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;
import com.chinthaka.backendroyallmssystem.subjectAssign.request.SubjectAssignToCourseDTO;
import com.chinthaka.backendroyallmssystem.subjectAssign.response.SubjectAssignResponseDTO;

import java.util.List;

public interface ISubjectAssignService {

    String assignSubjectToCourse(SubjectAssignToCourseDTO subjectAssignToCourseDTO);

    String removeAssignSubject(long subjectId, long courseId);

    List<SubjectAssignResponseDTO> getSubjectByCourse(long courseId);
}
