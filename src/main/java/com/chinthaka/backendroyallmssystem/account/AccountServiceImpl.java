package com.chinthaka.backendroyallmssystem.account;

import com.chinthaka.backendroyallmssystem.auth.AuthRequest;
import com.chinthaka.backendroyallmssystem.employee.Employee;
import com.chinthaka.backendroyallmssystem.employee.EmployeeRepo;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.role.Role;
import com.chinthaka.backendroyallmssystem.security.CustomUserDetailsService;
import com.chinthaka.backendroyallmssystem.security.JwtResponse;
import com.chinthaka.backendroyallmssystem.security.JwtUtil;
import com.chinthaka.backendroyallmssystem.student.*;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnroll;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnrollRepo;
import com.chinthaka.backendroyallmssystem.studentEnrollment.response.StudentEnrollResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepo userRepo;
    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;
    private final EmployeeRepo employeeRepo;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentEnrollRepo enrollRepo;
    private final IStudentService studentService;
    private final StudentEnrollRepo studentEnrollRepo;
    private final Counter status200Counter;
    private final Counter status400Counter;
    private final Counter status500Counter;


    @Override
    public Object createAuthenticationToken(AuthRequest authenticationRequest) throws Exception {
        log.info("Start createAuthenticationToken: password:{}, username: {} ",
                authenticationRequest.getUsername(),authenticationRequest.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            status400Counter.increment();
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse(jwt);
        status400Counter.increment();;
        return jwtResponse;
    }

    //Auth controller
    @Override
    public Object getCurrentUserDetails() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            status400Counter.increment();
            return Optional.empty();
        }
       try {
           final Optional<User> user = userRepo.findByUsername(authentication.getName());
           log.info("Logged in User details: {}",user.toString());
           if (user.isEmpty()) {
               status400Counter.increment();
               return Optional.empty();
           }

           String userNic = user.get().getUserNic();
           Role userRole = user.get().getRole();
           long userId = user.get().getUserId();

           log.info("Logged in user Role: {}",userRole);
           if (Role.STUDENT.equals(userRole)){
               if (studentRepo.existsByNic(userNic)){
                   StudentResponseDTO student = studentService.studentFindById(userId);
                   System.out.println(student.toString());
                   log.info("Logged in Student details: {}",student.toString());
                   status200Counter.increment();
                   return student;
               }else {
                   return null;
               }

           }else {
               if (employeeRepo.existsByNic(userNic)){
                   Employee employee = employeeRepo.findByNic(userNic);
                  log.info("Logged in Employee details: {}",employee.toString());
                   status200Counter.increment();
                   return employee;
               }else {
                   return null;
               }
           }

       }catch (Exception e){
           log.error("Error while get profile details: {}", e.getMessage());
           status500Counter.increment();
           throw new HandleException("Some thing went wrong profile");
       }
    }

    @Override
    public String registerUser(AccountCreateRequest accountCreateRequest) {
        if (accountCreateRequest.getUserId() <= 0){
            status400Counter.increment();
            throw new HandleException("User not selected");
        }
        if (accountCreateRequest.getUsername().isEmpty()
                || accountCreateRequest.getPassword().isEmpty() || accountCreateRequest.getRole().isEmpty()){
            status400Counter.increment();
            throw new HandleException("All the details required");
        }
        if (Objects.equals("ADMIN",accountCreateRequest.getRole())){
            Employee employee = EntityUtils.getEntityDetails(accountCreateRequest.getUserId(),employeeRepo,"Employee");
        }else {
            Student student = EntityUtils.getEntityDetails(accountCreateRequest.getUserId(),studentRepo,"Student");
            if (!enrollRepo.existsByStudent(student)){
                status400Counter.increment();
                throw new NotFoundException("The student must first enroll in the course");
            }
        }

        if (userRepository.existsByUsername(accountCreateRequest.getUsername())) {
            status400Counter.increment();
            throw new AlreadyExistException("Username already taken!");
        }
        if (userRepository.existsByUserId(accountCreateRequest.getUserId())) {
            status400Counter.increment();
            throw new AlreadyExistException("User have already an account!");
        }

        // Creating a new user object
        User user = new User();
        user.setUsername(accountCreateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(accountCreateRequest.getPassword()));
        user.setRole(Role.valueOf(accountCreateRequest.getRole()));
        user.setUserId(accountCreateRequest.getUserId());
        user.setUserNic(accountCreateRequest.getUserNic());

        userRepository.save(user);
        status400Counter.increment();
        return "Account successfully created";
    }



    private StudentResponseDTO getStudentResponseDTO(Student student) {
        StudentEnroll enrollData = studentEnrollRepo.findByStudent(student);

        StudentEnrollResponseDTO enrollDetails = null;
        if (Objects.nonNull(enrollData)) {
            enrollDetails = new StudentEnrollResponseDTO(
                    enrollData.getEnrollId(),
                    enrollData.getBatch().getBatchId(),
                    enrollData.getBatch().getCode(),
                    enrollData.getCourse().getCourseId(),
                    enrollData.getCourse().getName(),
                    enrollData.getCreatedDate()
            );
        }
        StudentResponseDTO st = studentMapper.studentToStudentResponseDTO(student);
        st.setEnroll(enrollDetails);
        status400Counter.increment();
        return st;
    }
}
