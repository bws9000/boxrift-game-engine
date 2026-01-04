package com.burtsnyder.blockengine.core.renderer;

import com.burtsnyder.blockengine.util.Coord;

public record GridRenderCell(Coord coord, int pixelX, int pixelY, int width, int height) {
}

