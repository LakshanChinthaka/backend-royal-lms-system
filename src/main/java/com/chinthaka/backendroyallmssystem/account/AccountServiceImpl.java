package com.chinthaka.backendroyallmssystem.account;

import com.chinthaka.backendroyallmssystem.auth.AuthRequest;
import com.chinthaka.backendroyallmssystem.employee.Employee;
import com.chinthaka.backendroyallmssystem.employee.EmployeeMapper;
import com.chinthaka.backendroyallmssystem.employee.EmployeeRepo;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.role.Role;
import com.chinthaka.backendroyallmssystem.security.CustomUserDetailsService;
import com.chinthaka.backendroyallmssystem.security.JwtResponse;
import com.chinthaka.backendroyallmssystem.security.JwtUtil;
import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.student.StudentMapper;
import com.chinthaka.backendroyallmssystem.student.StudentRepo;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepo userRepo;
    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;
    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Object createAuthenticationToken(AuthRequest authenticationRequest) throws Exception {
        log.info("Start createAuthenticationToken: password:{}, username: {} ",
                authenticationRequest.getUsername(),authenticationRequest.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse(jwt);
        return jwtResponse;
    }

    @Override
    public Object getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        final Optional<User> user = userRepo.findByUsername(authentication.getName());
        if (Role.STUDENT.equals(user.get().getRole())){
            Student student = EntityUtils.getEntityDetails(user.get().getUserId(),studentRepo,"Student");
            return studentMapper.studentToStudentResponseDTO(student);
        }else {
//            Employee employee = EntityUtils.getEntityDetails(user.get().getUserId(),employeeRepo,"Employee");
//            return employeeMapper.employeeToEmployeeResponseDTO(employee);
            return null;
        }
    }

    @Override
    public String registerUser(AccountCreateRequest accountCreateRequest) {
        if (accountCreateRequest.getUserId() <= 0){
            throw new HandleException("User not selected");
        }
        if (accountCreateRequest.getUserId() <= 0 || accountCreateRequest.getUsername().isEmpty()
                || accountCreateRequest.getPassword().isEmpty() || accountCreateRequest.getRole().isEmpty()){
            throw new HandleException("All the details required");
        }
        if (userRepository.existsByUsername(accountCreateRequest.getUsername())) {
            throw new AlreadyExistException("Username already taken!");
        }
        if (userRepository.existsByUserId(accountCreateRequest.getUserId())) {
            throw new AlreadyExistException("User have already an account!");
        }

        // Creating a new user object
        User user = new User();
        user.setUsername(accountCreateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(accountCreateRequest.getPassword()));
        user.setRole(Role.valueOf(accountCreateRequest.getRole()));
        user.setUserId(accountCreateRequest.getUserId());

        userRepository.save(user);
        return "Account successfully created";
    }
}
