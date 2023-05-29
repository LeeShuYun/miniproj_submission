package dev.leeshuyun.TelegramBot;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.leeshuyun.TelegramBot.services.TelegramService;

@SpringBootApplication
public class TelegramBotApplication implements CommandLineRunner {

	@Autowired
	TelegramService telegramBot;

	@Value("${telegram.token}")
	private String TELEGRAM_CHATID;

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// DO NOT DELETE - this is for polling Telegram. checks every second
		boolean x = true;
		long displayMinutes = 0;
		long starttime = System.currentTimeMillis();
		System.out.println("Checking for messages:");
		while (x) {
			long startTime = System.currentTimeMillis();
			long elapsedTime = System.currentTimeMillis() - startTime;
			long timeTillNextDisplayChange = 1000 - (elapsedTime % 1000);
			Thread.sleep(timeTillNextDisplayChange);

			// get the messages
			telegramBot.getUserMessagesByChatid(TELEGRAM_CHATID);

			// timer
			TimeUnit.SECONDS.sleep(1);
			long timepassed = System.currentTimeMillis() - starttime;
			long secondspassed = timepassed / 1000;
			if (secondspassed == 60) {
				secondspassed = 0;
				starttime = System.currentTimeMillis();
			}
			if ((secondspassed % 60) == 0)
				displayMinutes++;

			System.out.println(displayMinutes + "::" + secondspassed);
		}
	}

}
