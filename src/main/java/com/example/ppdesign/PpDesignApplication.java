package com.example.ppdesign;

import com.example.ppdesign.service.ProdConsBootService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class PpDesignApplication {

	public static Timer t;

	public static void main(String[] args) {
		SpringApplication.run(PpDesignApplication.class, args);
		startPollingTimer();
	}

	public static synchronized void startPollingTimer() {
		if (t == null) {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					ProdConsBootService.pollQueues();
				}
			};

			t = new Timer();
			t.scheduleAtFixedRate(task, 0, 10000);
		}
	}
}