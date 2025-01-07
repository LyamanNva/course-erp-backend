package com.example.course_erp_backend;

import com.example.course_erp_backend.enums.user.UserStatus;
import com.example.course_erp_backend.models.mybatis.user.User;
import com.example.course_erp_backend.models.properties.security.SecurityProperties;
import com.example.course_erp_backend.repository.UserRepository;
import com.example.course_erp_backend.services.security.AccessTokenManager;
import com.example.course_erp_backend.services.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@SpringBootApplication
@RequiredArgsConstructor
public class CourseErpBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CourseErpBackendApplication.class, args);
	}


//	private final SecurityProperties securityProperties;

	private final AccessTokenManager accessTokenManager;

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

//		User user =User.builder()
//				.email("leman@gmail.com")
//				.name("Leman")
//				.surname("Nuri")
//				.password(passwordEncoder.encode("123456"))
//				.roleId(1L)
//				.phoneNumber("121335")
//				.status(UserStatus.ACTIVE)
//				.build();
//
//		userService.insert(user);

//		System.out.println(userService.getByEmail("leman@gmail.com"));

//		User user=User.builder().email("lemannuriyeva@email.com").build();
//		user.setId(1L);
//
//		final String token=accessTokenManager.generate(user);
//
//		System.out.println(token);
////
//		System.out.println(
//				accessTokenManager.read(token).get("email", String.class)
//		);
//		System.out.println(securityProperties);
//		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
//		keyGenerator.initialize(2048);
//		KeyPair kp = keyGenerator.genKeyPair();
//		PublicKey publicKey = kp.getPublic();
//		PrivateKey privateKey = kp.getPrivate();
//
//		String encodedPublicKey= Base64.getEncoder().encodeToString(publicKey.getEncoded());
//		String encodedPrivateKey= Base64.getEncoder().encodeToString(privateKey.getEncoded());
//
//		System.out.println(convertToPublicKey(encodedPublicKey));
//		System.out.println();
//		System.out.println(convertToPrivateKey(encodedPrivateKey));

	}
	private static String convertToPrivateKey(String key) {
		StringBuilder result = new StringBuilder();
		result.append("-----BEGIN PRIVATE KEY-----\n");
		result.append(key);
		result.append("\n-----END PRIVATE KEY-----");
		return result.toString();
	}

	private static String convertToPublicKey(String key) {
		StringBuilder result = new StringBuilder();
		result.append("-----BEGIN PUBLIC KEY-----\n");
		result.append(key);
		result.append("\n-----END PUBLIC KEY-----");
		return result.toString();
	}
}
