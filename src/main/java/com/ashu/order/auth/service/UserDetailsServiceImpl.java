package com.ashu.order.auth.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashu.order.auth.dto.SignupRequest;
import com.ashu.order.auth.exception.EmailAlreadyExistsException;
import com.ashu.order.auth.exception.RoleDoesNotExistException;
import com.ashu.order.auth.exception.UsernameAlreadyExistsException;
import com.ashu.order.auth.model.Role;
import com.ashu.order.auth.model.RoleType;
import com.ashu.order.auth.model.UserDao;
import com.ashu.order.auth.model.UserDetailsImpl;
import com.ashu.order.auth.repository.RoleRepository;
import com.ashu.order.auth.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) {

		UserDao user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		return UserDetailsImpl.build(user);
	}

	public UserDao save(SignupRequest request) {

		if (userRepository.existsByUsername(request.getUsername())) {
			throw new UsernameAlreadyExistsException(request.getUsername());
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException(request.getUsername());
		}

		Set<String> strRoles = request.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleType.ROLE_ORDER_VIEWER)
					.orElseThrow(RoleDoesNotExistException::new);
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleType.ROLE_ORDER_ADMINISTRATOR)
							.orElseThrow(() -> new RoleDoesNotExistException(role));
					roles.add(adminRole);
					break;
				case "app":
					Role modRole = roleRepository.findByName(RoleType.ROLE_APP_ADMINISTRATOR)
							.orElseThrow(() -> new RoleDoesNotExistException(role));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(RoleType.ROLE_ORDER_VIEWER)
							.orElseThrow(() -> new RoleDoesNotExistException(role));
					roles.add(userRole);
				}
			});
		}

		// Create new user's account
		UserDao user = new UserDao(request.getUsername(), request.getEmail(), encoder.encode(request.getPassword()),
				roles);
		return userRepository.save(user);
	}

}
