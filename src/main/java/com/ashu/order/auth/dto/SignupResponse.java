package com.ashu.order.auth.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {

	private String username;

	private String email;

	private Set<String> roles = new HashSet<>();
}
