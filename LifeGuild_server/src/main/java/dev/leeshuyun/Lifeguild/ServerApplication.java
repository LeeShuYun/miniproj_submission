package dev.leeshuyun.Lifeguild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.leeshuyun.Lifeguild.repositories.AuthRepository;
import dev.leeshuyun.Lifeguild.services.TelegramService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired
	private TelegramService telegramBot;

	@Autowired
	private AuthRepository authRepo;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("app override running");
		// DO NOT DELETE
		// tried making bot angular way, tried nodejs way, tried making it separate
		// leaving it in this server for now, makes accessing DB easier
		// hopefully it won't lag?
		// telegramBot.botStart();

	}
}
