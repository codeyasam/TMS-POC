package com.codeyasam.testcasemanagement.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.testcasemanagement.dto.EndUserDTO;
import com.codeyasam.testcasemanagement.dto.response.SingleDataResponse;

@RestController
@RequestMapping("/principal")
public class SecurityController {
	
	@RequestMapping(value="/currentUser", method=RequestMethod.GET)
	public SingleDataResponse<EndUserDTO> retrieveCurrentUser(Principal principal) {
		SingleDataResponse.Builder<EndUserDTO> builder = new SingleDataResponse.Builder<>();
		if (principal != null) {
			EndUserDTO endUserDTO = new EndUserDTO();
			endUserDTO.setUsername(principal.getName());
			builder.setData(endUserDTO)
				.setPrompt("Successfully retrieve logged in user.")
				.setStatus(HttpStatus.OK.value());
		} else {
			builder.setPrompt("User not found: User is not logged in.")
				.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return builder.build();
	}
	
}
