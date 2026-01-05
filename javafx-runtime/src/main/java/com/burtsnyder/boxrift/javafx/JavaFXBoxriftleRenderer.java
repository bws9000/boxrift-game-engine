package com.burtsnyder.boxrift.javafx;

import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameRenderer;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.javafx.block.BoxriftleRenderer;
import javafx.scene.Group;

public record JavaFXBoxriftleRenderer(Group pieceLayer, int blockSize) implements GameRenderer {

    public JavaFXBoxriftleRenderer() {
        this(null, 0);
    }

    @Override
    public void render(GameState state) {
        pieceLayer.getChildren().clear();
        var piece = state.getActivePiece();
        if (piece != null) {
            pieceLayer.getChildren().add(
                    BoxriftleRenderer.render(piece, blockSize)
            );
        }
    }
}
