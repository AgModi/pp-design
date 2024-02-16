package com.example.ppdesign;

import com.example.ppdesign.service.exchange.ExchangeMQ;
import com.example.ppdesign.service.queue.MessageQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@Slf4j
public class PpDesignApplication {

	public static Timer t;

	public static void main(String[] args) {
		SpringApplication.run(PpDesignApplication.class, args);
		//startPollingTimer();
		removeExpiredItemsFromQueue();
	}

	/*
	* Polling mechanism, but we won't use because
	* We don't want consumer initiated polling
	* */
	@Deprecated
	public static synchronized void startPollingTimer() {
		if (t == null) {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					ExchangeMQ.pollQueues();
				}
			};

			t = new Timer();
			t.scheduleAtFixedRate(task, 0, 100000);
		}
	}

	public static synchronized void removeExpiredItemsFromQueue() {
		if (t == null) {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					MessageQueue.removeExpiredItemsAndFillDeadLetter();
				}
			};

			t = new Timer();
			t.scheduleAtFixedRate(task, 0, 100000);
		}
	}
}