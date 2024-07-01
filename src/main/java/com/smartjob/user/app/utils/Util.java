package com.smartjob.user.app.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Util {

	public String generateToken(String email) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + 3600000); // 1 hour

		return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, "4512").compact();
	}

	public static boolean validMail(String emailRegex, String email) {
        return Pattern.matches(emailRegex, email);
    }
	
	public static boolean validPassword(String passwordRegex, String password) {
		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
