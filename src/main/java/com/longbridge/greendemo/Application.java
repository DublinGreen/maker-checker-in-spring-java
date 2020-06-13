package com.longbridge.greendemo;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The type Application.
 *
 * @author idisimagha dublin-green
 */

@Configuration
@EnableSwagger2
@SpringBootApplication
public class Application {

	static Logger log = Logger.getLogger(Application.class.getName());

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("SPRING BOOT STARTED..............");
	}
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	   auth.inMemoryAuthentication().withUser("user").password("{noop}pass").roles("USER").and()
//	   .withUser("admin").password("{noop}pass").roles("ADMIN");
//	}
}
