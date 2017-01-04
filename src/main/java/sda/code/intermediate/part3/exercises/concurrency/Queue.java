package sda.code.intermediate.part3.exercises.concurrency;

import java.util.concurrent.BlockingQueue;

public class Queue {
	private static final int NUM_OF_CONSUMER_THREADS = 5;
	private static BlockingQueue<Integer> queue;

	/**
	 * Zadanie: zastosować kolejkę do komunikacji między producentami i
	 * konsumerami.
	 */
	public static void main(String[] args) {
		for (int i = 0; i < NUM_OF_CONSUMER_THREADS * 5; ++i) {
			queue.add(i);
		}
	}

}
