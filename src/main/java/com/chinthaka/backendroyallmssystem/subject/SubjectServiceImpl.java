package com.chinthaka.backendroyallmssystem.subject;

import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignRepo;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignToCourse;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SubjectServiceImpl implements ISubjectService {

    private final SubjectRepo subjectRepo;
    private final SubjectMapper subjectMapper;
    private final SubjectAssignRepo subjectAssignRepo;


    @Override
    public String addSubject(SubjectDTO subjectDTO) {
        if (subjectDTO == null) {
            throw new NotFoundException("Subject details not provide");
        }
        final Subject s = subjectRepo.findBySubjectCode(subjectDTO.getSubjectCode());
        if (s != null) {
            throw new AlreadyExistException("Subject already Exist");
        }
        try {
            Subject subject = new Subject(
                    subjectDTO.getSubjectId(),
                    subjectDTO.getSubjectCode(),
                    subjectDTO.getName()
            );
            subjectRepo.save(subject);
            return "Subject save successful";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during student registration");
        }
    }

    @Override
    public SubjectDTO subjectGetById(long subjectId) {
        Subject subject = EntityUtils.getEntityDetails(subjectId, subjectRepo, "Subject");
        SubjectDTO subjectDTO = subjectMapper.subjectToSubjectDTO(subject);
        return subjectDTO;
    }

    @Override
    @Transactional
    public String deleteSubject(long subjectId) {
        log.info("Start delete subject method subject ID: {}", subjectId);
        Subject subject = EntityUtils.getEntityDetails(subjectId, subjectRepo, "Subject");
        try {

            List<Long> subjectIds = new ArrayList<>();
            List<SubjectAssignToCourse> list = subjectAssignRepo.findAllBySubjects(subject);
            for (SubjectAssignToCourse s : list) {
                subjectIds.add(s.getSubjects().getSubjectId());
            }
            subjectAssignRepo.deleteAll(list);

            subjectRepo.deleteById(subjectId);

            return "Delete Successfully";

        } catch (Exception e) {
            log.error("Error while deleting subject details: {}", e.getMessage());
            throw new HandleException("Something went to wrong while deleting subject");
        }
    }

    @Override
    //have some problem during update subject can't change subject code because it is unique true
    public String editSubject(long subjectId, SubjectDTO subjectDTO) {
        Subject subject = EntityUtils.getEntityDetails(subjectId, subjectRepo, "Subject");
        final Subject updatedSubject = subjectMapper.subjectSaveDTOtoSubject(subjectDTO);
        try {
            subjectRepo.save(updatedSubject);
            return "Subject id " + subjectId + " updated";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during update student");
        }

    }

    @Override
    public Page<SubjectDTO> getAllSubject(Pageable pageable) {
        log.info("Start execute getAllSubject method");
        try {
            Page<Subject> subjectPage = subjectRepo.findAll(pageable);
            return subjectPage.map(s -> {
                SubjectDTO sub = new SubjectDTO();
                sub.setSubjectId(s.getSubjectId());
                sub.setSubjectCode(s.getSubjectCode());
                sub.setName(s.getName());
                sub.setCreateBy(s.getCreateBy());
                sub.setCreatedDate(s.getCreatedDate());
                sub.setModifiedBy(s.getModifiedBy());
                sub.setModifiedData(s.getModifiedData());
                return sub;
            });
        } catch (Exception e) {
            log.error("Error while fetching subject details {}", e.getMessage());
            throw new HandleException("Something went wrong while fetching subject details");
        }
    }

}
