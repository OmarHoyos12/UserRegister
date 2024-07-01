package com.smartjob.user.app.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smartjob.user.app.domain.model.Phone;
import com.smartjob.user.app.domain.model.User;
import com.smartjob.user.app.domain.port.UserRepository;
import com.smartjob.user.app.infrastructure.conf.ParameterConf;
import com.smartjob.user.app.infrastructure.exception.UserAlreadyExistsException;
import com.smartjob.user.app.utils.Util;

public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private Util util;

	@Mock
	private ParameterConf conf;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRegisterUserValidUser() {
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
		user.setPassword("Pasduhhh5");
		user.setPhones(phones);
		user.setCreated(LocalDateTime.now());
		user.setModified(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());
		user.setToken("AASDFDXX54562");
		user.setActive(true);

		//when
		when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

		when(util.generateToken(user.getEmail())).thenReturn("test-token");

		when(userRepository.save(user)).thenReturn(user);

		when(conf.passwordRegex()).thenReturn("^[a-zA-Z0-9]+$");

		User usuarioRegistrado = userService.registerUser(user);

		//then
		assertNotNull(usuarioRegistrado);
		assertEquals(user.getEmail(), usuarioRegistrado.getEmail());
		assertTrue(usuarioRegistrado.isActive());
		assertNotNull(usuarioRegistrado.getToken());
		verify(userRepository).save(user);
	}

	@Test
	public void testRegisterUserDuplicateEmail() {
		//given
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword("Pasduhhh5");
		when(conf.passwordRegex()).thenReturn("^[a-zA-Z0-9]+$");
		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
		assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
	}

	@Test
	public void testValidateEmailValidEmail() {
		String correoValido = "test@example.com";
		assertDoesNotThrow(() -> userService.validateEmail(correoValido));
	}

	@Test
	public void testValidateEmailInvalidEmail() {
		String correoInvalido = "correo-invalido";
		assertThrows(UserAlreadyExistsException.class, () -> userService.validateEmail(correoInvalido));
	}

	@Test
	public void testValidatePasswordCorrectFormat() {
		String passwordValido = "Pasduhhh5";
		when(conf.passwordRegex()).thenReturn("^[a-zA-Z0-9]+$");
		assertDoesNotThrow(() -> userService.validatePassword(passwordValido));
	}

}
