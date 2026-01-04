package com.burtsnyder.blockengine.core.input;

public record InputSignal(InputKind kind, InputAction action) {
    public static InputSignal press(InputAction a)   { return new InputSignal(InputKind.PRESS, a); }
    public static InputSignal release(InputAction a) { return new InputSignal(InputKind.RELEASE, a); }
    public static InputSignal oneShot(InputAction a) { return new InputSignal(InputKind.ONE_SHOT, a); }
}
