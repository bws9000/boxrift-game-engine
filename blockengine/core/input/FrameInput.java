package com.burtsnyder.blockengine.core.input;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.function.Predicate;

public final class FrameInput {
    private final Deque<InputAction> queue = new ArrayDeque<>();

    public void enqueue(Iterable<InputAction> actions) {
        for (var a : actions) queue.addLast(a);
    }

    public InputAction poll() {
        return queue.pollFirst();
    }



    public boolean consumeIf(Predicate<InputAction> p) {
        for (Iterator<InputAction> it = queue.iterator(); it.hasNext();) {
            var a = it.next();
            if (p.test(a)) { it.remove(); return true; }
        }
        return false;
    }
    public void clear() { queue.clear(); }
}
