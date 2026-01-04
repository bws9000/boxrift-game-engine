package com.burtsnyder.blockengine.core.actor.interfaces;

import com.burtsnyder.blockengine.core.actor.Actor;
import com.burtsnyder.blockengine.util.Coord;

public interface ActorFactory<T extends Actor> {
    T create();
    T createAt(Coord origin);
}

