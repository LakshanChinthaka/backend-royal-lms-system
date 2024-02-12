package com.chinthaka.backendroyallmssystem.subject;

import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements ISubjectService {

    private final SubjectRepo subjectRepo;
    private final SubjectMapper subjectMapper;


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
        Subject subject = EntityUtils.getEntityDetails(subjectId,subjectRepo,"Subject");
        SubjectDTO subjectDTO = subjectMapper.subjectToSubjectDTO(subject);
        return subjectDTO;
    }

    @Override
    public String deleteSubject(long subjectId) {
        if (subjectRepo.existsById(subjectId)) {
            subjectRepo.deleteById(subjectId);
            return "Subject id " + subjectId + " deleted ";
        }
        throw new NotFoundException("Subject " + subjectId + " not found");
    }

    @Override
    //have some problem during update subject can't change subject code because it is unique true
    public String editSubject(long subjectId, SubjectDTO subjectDTO) {
        Subject subject = EntityUtils.getEntityDetails(subjectId,subjectRepo,"Subject");
        final Subject updatedSubject = subjectMapper.subjectSaveDTOtoSubject(subjectDTO);
        try {
            subjectRepo.save(updatedSubject);
            return "Subject id " + subjectId +" updated";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during update student");
        }

    }

}
