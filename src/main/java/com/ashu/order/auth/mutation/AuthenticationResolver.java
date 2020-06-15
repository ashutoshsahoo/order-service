package com.ashu.order.auth.mutation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ashu.order.auth.dto.LoginRequest;
import com.ashu.order.auth.dto.LoginResponse;
import com.ashu.order.auth.dto.SignupRequest;
import com.ashu.order.auth.dto.SignupResponse;
import com.ashu.order.auth.model.UserDao;
import com.ashu.order.auth.model.UserDetailsImpl;
import com.ashu.order.auth.service.UserDetailsServiceImpl;
import com.ashu.order.auth.util.JwtUtils;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
@Validated
@RequiredArgsConstructor
public class AuthenticationResolver implements GraphQLMutationResolver {

    private final AuthenticationManager authManager;

    private final JwtUtils jwtUtils;

    private final UserDetailsServiceImpl userDetailsService;

    @PreAuthorize("isAnonymous()")
    public LoginResponse login(@Valid LoginRequest request) {

        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new LoginResponse(token, userDetails.getUsername(), roles);
    }

    @PreAuthorize("isAnonymous()")
    public SignupResponse signup(@Valid SignupRequest request) {
        UserDao userDao = userDetailsService.save(request);
        Set<String> roles = userDao.getRoles().stream().map(role -> role.getName().toString())
                .collect(Collectors.toSet());
        return new SignupResponse(userDao.getUsername(), userDao.getEmail(), roles);
    }

}
