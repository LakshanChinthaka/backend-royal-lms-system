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
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnrollRepo;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchServiceImpl implements IBatchService {

    private final BatchRepo batchRepo;
    private final CourseRepo courseRepo;
    private final SchoolRepo schoolRepo;
    private final StudentEnrollRepo enrollRepo;
    private final Counter status200Counter;
    private final Counter status500Counter;
    private final Counter status400Counter;
    private final Counter status404Counter;

    @Override
    public BatchResponseDTO getById(long batchId) {
        final Batch batch = EntityUtils.getEntityDetails(batchId, batchRepo, "Batch");
        try {
            int studentCount = 0;
            //can't use int because, if it no data in db return null but int can't store null value
            final Integer count = enrollRepo.count(batch);
            //count is not null then count assign to studentCount
            if (Objects.nonNull(count)){
                studentCount = count;
            }

            log.info("Batch id: {} has students: {}", batchId, studentCount);
            System.out.println("Id"+studentCount);
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
                    batch.isActiveStatus(),
                    studentCount
            );
        }catch (Exception e){
            log.error("Error while fetching data: {}",e.getMessage());
            status500Counter.increment();
            throw new HandleException("Something went wrong fetching batch data");
        }
    }

    @Override
    public String deleteBatch(long batchId) {
        if (batchRepo.existsById(batchId)) {
            batchRepo.deleteById(batchId);
            status200Counter.increment();
            return "Batch id " + batchId + " deleted ";
        }
        status500Counter.increment();
        throw new NotFoundException("Batch " + batchId + " not found");
    }

    @Override
    public String addBatch(BatchDTO batchDTO) {
        if (batchDTO == null) {
            status400Counter.increment();
            throw new NotFoundException("Batch details not provide");
        }
        final Batch b = batchRepo.findByCode(batchDTO.getCode());
        if (b != null) {
            status404Counter.increment();
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
            status200Counter.increment();
            return "Batch save successful";
        } catch (Exception e) {
            status500Counter.increment();
            throw new HandleException("Something went wrong during create new Batch ");
        }
    }

    @Override
    public Page<BatchResponseDTO> getAllBatch(Pageable pageable, long courseId) {
        log.info("Start execute getAllBatch method {}",pageable);
        final Course course = EntityUtils.getEntityDetails(courseId, courseRepo,"Course");
        try {
            Page<Batch> batchPage = batchRepo.findAllByCourse(pageable,course);
            status200Counter.increment();
            return getBatchResponseDTOS(batchPage);
        }catch (Exception e){
            log.error("Error while get pageable batch details: {}", e.getMessage());
            status500Counter.increment();
            throw new HandleException("Something went wrong while get batch details");
        }

    }

    @Override
    public Page<BatchResponseDTO> getAllBatchData(Pageable pageable) {
        log.info("Start execute getAllBatch method {}",pageable);
        try {
            Page<Batch> batchPage = batchRepo.findAll(pageable);
            status200Counter.increment();
            return getBatchResponseDTOS(batchPage);
        }catch (Exception e){
            log.error("Error while get pageable batch details: {}", e.getMessage());
            status500Counter.increment();
            throw new HandleException("Something went wrong while get batch details");
        }
    }

    private Page<BatchResponseDTO> getBatchResponseDTOS(Page<Batch> batchPage) {

        return batchPage.map(batch -> {
            int studentCount = 0;
            
            //can't use int because, if it no data in db return null but int can't store null value
            final Integer count = enrollRepo.count(batch);
           
            //count is not null then count assign to studentCount
            if (Objects.nonNull(count)){
                studentCount = count;
            }
            
            log.info("Batch id: {} has students: {}", batch.getBatchId(), studentCount);
            BatchResponseDTO bt = getBatchResponseDTO(batch, studentCount);
            status200Counter.increment();
            return bt;

        });
    }

    private static BatchResponseDTO getBatchResponseDTO(Batch batch, int studentCount) {
        BatchResponseDTO bt = new BatchResponseDTO();
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
        bt.setCount(studentCount);
        return bt;
    }


//    private Page<BatchResponseDTO> getBatchResponseDTOS(Page<Batch> batchPage) {
//
//        return batchPage.map(batch -> {
//            BatchResponseDTO bt =new BatchResponseDTO();
//            bt.setBatchId(batch.getBatchId());
//            bt.setCode(batch.getCode());
//            bt.setCourseName(batch.getCourse().getName());
//            bt.setCourseCode(batch.getCourse().getCode());
//            bt.setSchoolName(batch.getSchool().getSchoolName());
//            bt.setSchoolCode(batch.getSchool().getSchoolCode());
//            bt.setCode(batch.getCode());
//            bt.setCreateBy(batch.getCreateBy());
//            bt.setCreatedDate(batch.getCreatedDate());
//            bt.setModifiedBy(batch.getModifiedBy());
//            bt.setModifiedData(batch.getModifiedData());
//            bt.setActiveStatus(batch.isActiveStatus());
//
//            return bt;
//        });

}
