package com.chinthaka.backendroyallmssystem.school;


import com.chinthaka.backendroyallmssystem.school.request.SchoolDTO;
import com.chinthaka.backendroyallmssystem.school.response.SchoolResponseDTO;

import java.util.List;



public interface ISchoolService {

    String editSchool(long schoolId, SchoolDTO schoolDTO);

    String deleteSchool(long schoolId);

    SchoolDTO subjectGetById(long schoolId);

    String addSchool(SchoolDTO schoolDTO);

    List<SchoolResponseDTO> findAllSchools();
}
