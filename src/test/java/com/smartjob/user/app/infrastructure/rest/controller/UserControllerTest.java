package com.smartjob.user.app.infrastructure.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartjob.user.app.application.service.UserService;
import com.smartjob.user.app.domain.model.Phone;
import com.smartjob.user.app.domain.model.User;

@WebMvcTest
public class UserControllerTest {

	@InjectMocks
	UserController controller;
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testRegisterUser() throws Exception {
    	//given    	
    	Phone phone = new Phone();
    	List<Phone> phones = new ArrayList<Phone>();
    	
    	phone.setCityCode("1");
    	phone.setCountryCode("57");
    	phone.setId(1L);
    	phone.setNumber("1234567");
   		
    	phones.add(phone);
    	
        User user = new User();
        user.setId(1L);
        user.setName("nameTest");
        user.setEmail("test@smart.com");
        user.setPassword("Password1");
        user.setPhones(phones);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken("AASDFDXX54562");
        user.setActive(true);

        given(userService.registerUser(any(User.class)))
        .willAnswer((invocation) -> invocation.getArgument(0));
        
        //when
        ResultActions response = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
            
        when(userService.registerUser(user)).thenReturn(user);
        
        ResponseEntity<?> expected = controller.registerUser(user);
      
    }
}