package dev.prashant.jdbcclient;

import dev.prashant.jdbcclient.model.Post;
import dev.prashant.jdbcclient.service.PostService;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			@Qualifier("jdbcClientPostService") PostService postService,
			JdbcConnectionDetails jdbcConnectionDetails) {

		return args -> {
			LOGGER.info("URL : {}",jdbcConnectionDetails.getJdbcUrl());
			LOGGER.info("DriverClassName : {}",jdbcConnectionDetails.getDriverClassName());
			LOGGER.info("UserName : {}",jdbcConnectionDetails.getUsername());
			LOGGER.info("Password : {}",jdbcConnectionDetails.getPassword());

			postService.create(new Post(
							"001",
							"Hello World",
							"Hello-world",
							LocalDate.now(),
							5,
							"spring-jdbc-client"
			));

			List<Post> postList = postService.findAll();
			postList.forEach((post) -> LOGGER.info(post.toString()));

			LOGGER.info("Post --> {}", postService.findById("001"));
		};
	}
}
