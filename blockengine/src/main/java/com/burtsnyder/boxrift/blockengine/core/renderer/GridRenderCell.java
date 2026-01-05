package com.burtsnyder.boxrift.blockengine.core.renderer;

import com.burtsnyder.boxrift.blockengine.util.Coord;

public record GridRenderCell(Coord coord, int pixelX, int pixelY, int width, int height) {
}

