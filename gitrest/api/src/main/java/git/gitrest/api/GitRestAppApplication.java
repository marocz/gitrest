package git.gitrest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GitRestAppApplication {
	

    
	public static void main(String[] args) {
		SpringApplication.run(GitRestAppApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{    return new BCryptPasswordEncoder(); }
   
}
