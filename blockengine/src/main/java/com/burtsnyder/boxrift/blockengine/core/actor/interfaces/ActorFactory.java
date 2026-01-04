package com.burtsnyder.boxrift.blockengine.core.actor.interfaces;

import com.burtsnyder.boxrift.blockengine.core.actor.Actor;
import com.burtsnyder.boxrift.blockengine.util.Coord;

public interface ActorFactory<T extends Actor> {
    T create();
    T createAt(Coord origin);
}

