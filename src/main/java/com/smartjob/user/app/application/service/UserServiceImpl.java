package com.smartjob.user.app.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartjob.user.app.domain.model.User;
import com.smartjob.user.app.domain.port.UserRepository;
import com.smartjob.user.app.infrastructure.conf.ParameterConf;
import com.smartjob.user.app.infrastructure.exception.InvalidDataException;
import com.smartjob.user.app.infrastructure.exception.UserAlreadyExistsException;
import com.smartjob.user.app.utils.Util;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Util util;
	@Autowired
	private ParameterConf conf;

	private String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private String FORMAT_EMAIL_INVALID = "Formato de correo incorrecto";
	private String EMAIL_REGISTER = "El correo ya está registrado";


	/**
     * Registra un nuevo usuario.
     *
     * @param user Objeto User con los datos del usuario a registrar.
     * @return El usuario registrado.
     * @throws UserAlreadyExistsException Si el correo ya está registrado.
     */
	public User registerUser(User user) {
		// valida formato de email
		validateEmail(user.getEmail());
		validatePassword(user.getPassword());
		// valida si el correo ya esta registrado
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserAlreadyExistsException(EMAIL_REGISTER);
		}

		user.setCreated(LocalDateTime.now());
		user.setModified(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());
		user.setActive(true);
		user.setLastLogin(LocalDateTime.now());
		user.setId(UUID.randomUUID().getMostSignificantBits());
		// Generación del token
		String token = util.generateToken(user.getEmail());
		user.setToken(token);

		return userRepository.save(user);
	}

	/**
     * Valida el formato del correo electrónico.
     *
     * @param email Dirección de correo electrónico a validar.
     * @throws UserAlreadyExistsException Si el formato es incorrecto.
     */
	public void validateEmail(String email) {
		if (!util.validMail(EMAIL_REGEX, email)) {
			throw new UserAlreadyExistsException(FORMAT_EMAIL_INVALID);
		}
	}

	/**
     * Valida el formato de contraseña.
     *
     * @param password a validar.
     * @throws InvalidDataException Si el formato es incorrecto.
     */
	public void validatePassword(String password) {
		if (!Util.validPassword(conf.passwordRegex(), password)) {
			throw new InvalidDataException(conf.passwordValidationMessage());
		}		
	}
}
