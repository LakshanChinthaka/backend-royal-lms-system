package com.chinthaka.backendroyallmssystem.school;

import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.school.request.SchoolDTO;
import com.chinthaka.backendroyallmssystem.school.response.SchoolResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SchoolServiceImpl implements ISchoolService {

    private final SchoolRepo schoolRepo;
    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(SchoolRepo schoolRepo, SchoolMapper schoolMapper) {
        this.schoolRepo = schoolRepo;
        this.schoolMapper = schoolMapper;
    }

    @Override
    public String addSchool(SchoolDTO schoolDTO) {
        if (schoolDTO == null) {
            throw new NotFoundException("School details not provide");
        }
        final School s = schoolRepo.findBySchoolCode(schoolDTO.getSchoolCode());
        if (s != null) {
            throw new AlreadyExistException("This School code already Exist");
        }
        try {
            School school = new School(
                    schoolDTO.getSchoolID(),
                    schoolDTO.getSchoolCode(),
                    schoolDTO.getSchoolName()
            );
            schoolRepo.save(school);
            return "School successfully created";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during create new School ");
        }
    }

    @Override
    public List<SchoolResponseDTO> findAllSchools() {
       try {
           final List<School> school = schoolRepo.findAllByOrderByCreatedDateDesc();
           List<SchoolResponseDTO> schoolResponseDTOS = schoolMapper.listSchoolToListSchoolResponseDto(school);
           return schoolResponseDTOS;
       }catch (Exception e){
           throw new HandleException("Something went wrong during get all  School ");
       }
    }

    @Override
    public SchoolDTO subjectGetById(long schoolId) {
        School school = EntityUtils.getEntityDetails(schoolId,schoolRepo,"School");
        SchoolDTO schoolDTO = schoolMapper.schooToSchoolDTO(school);
        return schoolDTO;
    }

    @Override
    // have some error
    public String editSchool(long schoolId, SchoolDTO schoolDTO) {
        School school = EntityUtils.getEntityDetails(schoolId,schoolRepo,"School");
        try {
            school.setSchoolName(schoolDTO.getSchoolName());
            schoolRepo.save(school);
            return "School code: " + school.getSchoolCode() + " updated";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during update School");
        }
    }

    @Override
    public String deleteSchool(long schoolId) {
        if (schoolRepo.existsById(schoolId)) {
            schoolRepo.deleteById(schoolId);
            return "School id " + schoolId + " deleted ";
        }
        throw new NotFoundException("School " + schoolId + " not found");
    }


}
