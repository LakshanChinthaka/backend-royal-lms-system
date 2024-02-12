package com.chinthaka.backendroyallmssystem.batch;

import com.chinthaka.backendroyallmssystem.batch.request.BatchDTO;
import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;
import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.course.CourseRepo;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.school.School;
import com.chinthaka.backendroyallmssystem.school.SchoolRepo;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements IBatchService{

    private final BatchRepo batchRepo;
    private final CourseRepo courseRepo;
    private final SchoolRepo schoolRepo;
    private final BatchMapper batchMapper;

    @Override
    public BatchResponseDTO getById(long batchId) {
       final Batch batch = EntityUtils.getEntityDetails(batchId,batchRepo,"Batch");
       BatchResponseDTO batchResponseDTO = new BatchResponseDTO(
         batch.getBatchId(),
         batch.getCode(),
         batch.getCourse().getName(),
         batch.getCourse().getCode(),
         batch.getSchool().getSchoolName(),
         batch.getSchool().getSchoolCode()
       );
        return batchResponseDTO;
    }

    @Override
    public String deleteBatch(long batchId) {
        if (batchRepo.existsById(batchId)) {
            batchRepo.deleteById(batchId);
            return "Batch id " + batchId + " deleted ";
        }
        throw new NotFoundException("Batch " + batchId + " not found");
    }

    @Override
    public String addBatch(BatchDTO batchDTO) {
        if (batchDTO == null) {
            throw new NotFoundException("Batch details not provide");
        }
        final Batch b = batchRepo.findByCode(batchDTO.getCode());
        if (b != null) {
            throw new AlreadyExistException("Batch already Exist");
        }
        Course course = EntityUtils.getEntityDetails(batchDTO.getCourseId(),courseRepo,"Course");
        School school = EntityUtils.getEntityDetails(batchDTO.getSchoolId(),schoolRepo,"School");
        try {
            Batch batch = new Batch(
                    batchDTO.getBatchId(),
                    batchDTO.getCode(),
                    course,
                    school
            );
            batchRepo.save(batch);
            return "Batch save successful";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during create new Batch ");
        }
    }
}
