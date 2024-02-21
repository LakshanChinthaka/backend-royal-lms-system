package com.chinthaka.backendroyallmssystem.assignment;

import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AssigmentServiceImpl implements IAssigmentService{

    private final AssigmentRepo assigmentRepo;

    @Override
    public String addAssigment(AssigmentAddDTO assigmentAddDTO) {
        if (Objects.isNull(assigmentAddDTO) || Objects.isNull(assigmentAddDTO.getAssiUrl())){
            throw new NotFoundException("Details not provided");
        }
        if (assigmentRepo.existsByAssiCode(assigmentAddDTO.getAssiCode())){
            throw new AlreadyExistException("Assigment Already exists");
        }
        try {
            Assigment assigment = Assigment.builder()
                    .assiCode(assigmentAddDTO.getAssiCode())
                    .batchId(assigmentAddDTO.getBatchId())
                    .assiUrl(assigmentAddDTO.getAssiUrl())
                    .deadLine(assigmentAddDTO.getDeadLine())
                    .build();
            assigmentRepo.save(assigment);
            return "Assigment adding success";
        }catch (Exception e){
            throw new HandleException("Something went wrong while add Assigment ");
        }
    }
}
