package com.sica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

public class RabbitAmqpRunner implements CommandLineRunner {

	@Autowired
	private ConfigurableApplicationContext ctx;

	@Override
	public void run(String... arg) throws Exception {
//		int duration = 1000 * 60 * 10;
//		System.out.println("Ready ... running for " + duration + "ms");
//		Thread.sleep(duration);
//		ctx.close();
	}
}
