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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BatchServiceImpl implements IBatchService {

    private final BatchRepo batchRepo;
    private final CourseRepo courseRepo;
    private final SchoolRepo schoolRepo;

    public BatchServiceImpl(BatchRepo batchRepo, CourseRepo courseRepo, SchoolRepo schoolRepo) {
        this.batchRepo = batchRepo;
        this.courseRepo = courseRepo;
        this.schoolRepo = schoolRepo;
    }

    @Override
    public BatchResponseDTO getById(long batchId) {
        final Batch batch = EntityUtils.getEntityDetails(batchId, batchRepo, "Batch");
        return new BatchResponseDTO(
                batch.getBatchId(),
                batch.getCode(),
                batch.getCourse().getName(),
                batch.getCourse().getCode(),
                batch.getSchool().getSchoolName(),
                batch.getSchool().getSchoolCode(),
                batch.getCreateBy(),
                batch.getCreatedDate(),
                batch.getModifiedBy(),
                batch.getModifiedData(),
                batch.isActiveStatus()

        );
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
        Course course = EntityUtils.getEntityDetails(batchDTO.getCourseId(), courseRepo, "Course");
        School school = EntityUtils.getEntityDetails(batchDTO.getSchoolId(), schoolRepo, "School");
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

    @Override
    public Page<BatchResponseDTO> getAllBatch(Pageable pageable, long courseId) {
        log.info("Start execute getAllBatch method {}",pageable);
        final Course course = EntityUtils.getEntityDetails(courseId, courseRepo,"Course");
        try {
            Page<Batch> batchPage = batchRepo.findAllByCourse(pageable,course);
            return getBatchResponseDTOS(batchPage);
        }catch (Exception e){
            log.error("Error while get pageable batch details: {}", e.getMessage());
            throw new HandleException("Something went wrong while get batch details");
        }

    }

    @Override
    public Page<BatchResponseDTO> getAllBatchData(Pageable pageable) {
        log.info("Start execute getAllBatch method {}",pageable);
        try {
            Page<Batch> batchPage = batchRepo.findAll(pageable);
            return getBatchResponseDTOS(batchPage);
        }catch (Exception e){
            log.error("Error while get pageable batch details: {}", e.getMessage());
            throw new HandleException("Something went wrong while get batch details");
        }
    }

    private Page<BatchResponseDTO> getBatchResponseDTOS(Page<Batch> batchPage) {
        return batchPage.map(batch -> {
            BatchResponseDTO bt =new BatchResponseDTO();
            bt.setBatchId(batch.getBatchId());
            bt.setCode(batch.getCode());
            bt.setCourseName(batch.getCourse().getName());
            bt.setCourseCode(batch.getCourse().getCode());
            bt.setSchoolName(batch.getSchool().getSchoolName());
            bt.setSchoolCode(batch.getSchool().getSchoolCode());
            bt.setCode(batch.getCode());
            bt.setCreateBy(batch.getCreateBy());
            bt.setCreatedDate(batch.getCreatedDate());
            bt.setModifiedBy(batch.getModifiedBy());
            bt.setModifiedData(batch.getModifiedData());
            bt.setActiveStatus(batch.isActiveStatus());

            return bt;
        });
    }

}
