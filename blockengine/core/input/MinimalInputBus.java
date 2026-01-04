package com.burtsnyder.blockengine.core.input;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A thread-safe implementation of {@link InputBus} that stores incoming
 * {@link InputSignal} events in a non-blocking queue.
 * <p>
 * Suitable for use in multithreaded environments, such as a agame loop
 * receiving input from another thread.
 */
public class MinimalInputBus implements InputBus {
    /** // thread-safe queue for passing input signals between threads */
    private final ConcurrentLinkedQueue<InputSignal> q = new ConcurrentLinkedQueue<>();

    /**
     * Adds a signal to the queue.
     * @param signal the input signal to add
     */
    @Override
    public void emit(InputSignal signal) { q.offer(signal); }

    /**
     * Retrieves and removes the next signal from the queue, or {@code null} if empty.
     * @return the next input signal, or null if none available
     */
    @Override
    public InputSignal poll() { return q.poll(); }
}