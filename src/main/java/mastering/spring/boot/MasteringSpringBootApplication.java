package mastering.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MasteringSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasteringSpringBootApplication.class, args);
	}

}
