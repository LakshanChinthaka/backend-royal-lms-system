package com.chinthaka.backendroyallmssystem.account;

import com.chinthaka.backendroyallmssystem.auth.AuthRequest;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepo userRepo;
    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;

    @Override
    public Object createAuthenticationToken(AuthRequest authenticationRequest) throws Exception {
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

        }
        return null;
    }
}
