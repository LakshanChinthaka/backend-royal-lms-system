package com.chinthaka.backendroyallmssystem.utils;

import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
public class EntityUtils {

    // check is empty
    public static Object isEmpty(Object object) {
        try {
            if (Objects.isNull(object)) {
                log.warn("Object is Empty");
                throw new NotFoundException("Student details not provided");
            }
            log.info("Object is not Empty");
        } catch (Exception e) {
            log.error("Error while checking for null object", e);
            throw new HandleException("Something went wrong checking object null");
        }
        return null;
    }

    //get entity details
    public static <T> T getEntityDetails(long id, JpaRepository<T, Long> repository, String entityName) {
        if (id <= 0){
            throw new NotFoundException("Id can not be null");
        }
        log.info("Start fetching {} entity details ", entityName);
        Supplier<NotFoundException> exceptionSupplier =
                () -> new NotFoundException(entityName + " Id - " + id + " not found");
        return repository.findById(id).orElseThrow(exceptionSupplier);
    }


//    //get entity by nic
//    public static <T> T getEntityByNic(String nic, JpaRepository<T, Long> repository, String entityName) {
//        if (nic.isBlank()){
//            throw new NotFoundException(entityName+" Nic can not be null");
//        }
//        log.info("Start fetching {} entity details ", entityName);
//        Supplier<NotFoundException> exceptionSupplier =
//                () -> new NotFoundException(entityName +  " not found");
//        return repository.findByNic(nic).orElseThrow(exceptionSupplier);
//    }


    public static boolean isCourseExist(long courseId, JpaRepository<Course, Long> courseRepo) {
        return courseRepo.existsById(courseId);
    }

    public static <T> boolean isEntityExist(long id, JpaRepository<T, Long> repository) {
        try {
            if (id != -1) {
                log.info("Start checking existence entity details");
                return repository.existsById(id);
            }
            throw new NotFoundException("Id not found");
        } catch (Exception e) {
            log.error("Error checking entity existence: {}", e.getMessage());
            throw new HandleException("Some thing went wrong accessing database ");
        }
    }


    public static String convertToDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
        return dateTime.format(formatter);
    }

}
