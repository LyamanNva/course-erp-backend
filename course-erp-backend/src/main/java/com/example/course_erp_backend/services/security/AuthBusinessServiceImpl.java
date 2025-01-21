package com.example.course_erp_backend.services.security;

import com.example.course_erp_backend.exception.BaseException;
import com.example.course_erp_backend.models.dto.RefreshTokenDto;
import com.example.course_erp_backend.models.enums.branch.BranchStatus;
import com.example.course_erp_backend.models.mappers.CourseEntityMapper;
import com.example.course_erp_backend.models.mappers.UserEntityMapper;
import com.example.course_erp_backend.models.mybatis.branch.Branch;
import com.example.course_erp_backend.models.mybatis.course.Course;
import com.example.course_erp_backend.models.mybatis.employee.Employee;
import com.example.course_erp_backend.models.mybatis.role.Role;
import com.example.course_erp_backend.models.mybatis.user.User;
import com.example.course_erp_backend.models.payload.auth.LoginPayload;
import com.example.course_erp_backend.models.payload.auth.RefreshTokenPayload;
import com.example.course_erp_backend.models.payload.auth.SignUpPayload;
import com.example.course_erp_backend.models.response.auth.LoginResponse;
import com.example.course_erp_backend.services.branch.BranchService;
import com.example.course_erp_backend.services.course.CourseService;
import com.example.course_erp_backend.services.employee.EmployeeService;
import com.example.course_erp_backend.services.role.RoleService;
import com.example.course_erp_backend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.course_erp_backend.models.enums.response.ErrorResponseMessages.EMAIL_ALREADY_REGISTERED;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthBusinessServiceImpl implements AuthBusinessService {

    private final static String BRANCH_NAME_DEFAULT_PATTERN="%s Default branch";
    private final AuthenticationManager authenticationManager;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private  final RoleService roleService;
    private final CourseService courseService;
    private final BranchService branchService;
    private final EmployeeService employeeService;
    @Override
    public LoginResponse login(LoginPayload payload) {
        authenticate(payload);

        return prepareLoginResponse(payload.getEmail(), payload.isRememberMe());

    }

    @Override
    public LoginResponse refresh(RefreshTokenPayload payload) {
        return prepareLoginResponse(
                refreshTokenManager.getEmail(payload.getRefreshToken()),
                payload.isRememberMe());

    }

    @Override
    public void logout() {
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("{} user logout succeed",userDetails.getUsername());
    }

    @Override
    public void signup(SignUpPayload payload) {

        if (userService.checkByEmail(payload.getEmail())) {
            throw BaseException.of(EMAIL_ALREADY_REGISTERED);
        }
        Role defaultRole=roleService.getDefaultRole();
        //User insert
        User user= UserEntityMapper.INSTANCE.fromSignUpPayloadToUser(
                payload,
                passwordEncoder.encode(payload.getPassword()),
                defaultRole.getId()
        );

        userService.insert(user);
        //Course insert
        Course course= CourseEntityMapper.INSTANCE.fromSignUpPayload(payload);
        courseService.insert(course);

        //default branch insert
        branchService.insert(populateDefaultBranch(payload,course));

        //employee insert
        employeeService.insert(Employee.builder().userId(user.getId()).build());
    }

    @Override
    public void setAuthentication(String email) {
        UserDetails userDetails=userDetailsService.loadUserByUsername(email);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities())
        );
    }
    //private util methods

    private void authenticate(LoginPayload request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw e.getCause() instanceof BaseException?
                    (BaseException) e.getCause():
                    BaseException.unexpected();
        }
    }

    private LoginResponse prepareLoginResponse(String email,boolean rememberMe) {
        User user=userService.getByEmail(email);
        return LoginResponse.builder()
                .accessToken(accessTokenManager.generate(user))
                .refreshToken(refreshTokenManager.generate(
                        RefreshTokenDto.builder().user(user).rememberMe(rememberMe)
                                .build()
                ))
                .build();
    }

    private Branch populateDefaultBranch(SignUpPayload payload,Course course) {
        return Branch.builder().name(BRANCH_NAME_DEFAULT_PATTERN.formatted(payload.getCourseName()))
                .status(BranchStatus.ACTIVE)
                .address(payload.getAddress())
                .courseId(course.getId())
                .build();
    }
}
