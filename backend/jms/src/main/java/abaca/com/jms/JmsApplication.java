package abaca.com.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class JmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmsApplication.class, args);
	}

}
