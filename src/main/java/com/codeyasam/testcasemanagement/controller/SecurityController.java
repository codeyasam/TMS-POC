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
		EndUserDTO endUserDTO = new EndUserDTO();
		endUserDTO.setUsername(principal.getName());
		return new SingleDataResponse.Builder<EndUserDTO>()
				.setData(endUserDTO)
				.setPrompt("Successfully retrieved current logged in user.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
}
