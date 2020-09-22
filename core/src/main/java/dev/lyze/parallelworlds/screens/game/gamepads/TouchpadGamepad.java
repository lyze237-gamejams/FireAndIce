package dev.lyze.parallelworlds.screens.game.gamepads;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.lyze.parallelworlds.screens.game.entities.enums.Direction;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.screens.game.entities.enums.PlayerColor;
import dev.lyze.parallelworlds.statics.Statics;

public class TouchpadGamepad extends VirtualGamepad {
    public TouchpadGamepad(Player player, int playerNumber) {
        super(player, playerNumber);
    }

    public void setup(Stage stage) {
        var uiAtlas = Statics.assets.getGame().getSharedLevelAssets().getUiAtlas();

        var rootTable = new Table();
        rootTable.setFillParent(true);

        var additionalTable = new Table();
        additionalTable.setFillParent(true);

        if (player.getColor() == PlayerColor.Ice) {
            Table leftControlsTable = setupController(uiAtlas.getBlueUp(), uiAtlas.getBlueLeft(), uiAtlas.getBlueRight(), uiAtlas.getBlueDown());
            rootTable.add(leftControlsTable).bottom().left().padLeft(8).padBottom(8).expand();

            var rightAdditionalTable = new Table();
            rightAdditionalTable.add(setupController(uiAtlas.getBlueUp(), Direction.Up));
            additionalTable.add(rightAdditionalTable).right().padRight(8).expand();
        } else {
            Table rightControlsTable = setupController(uiAtlas.getRedUp(), uiAtlas.getRedLeft(), uiAtlas.getRedRight(), uiAtlas.getRedDown());
            rootTable.add(rightControlsTable).bottom().right().padRight(8).padBottom(8).expand();

            var leftAdditionalTable = new Table();
            leftAdditionalTable.add(setupController(uiAtlas.getRedUp(), Direction.Up));
            additionalTable.add(leftAdditionalTable).left().padLeft(8).expand();
        }

        stage.addActor(rootTable);
        stage.addActor(additionalTable);
    }

    private Table setupController(TextureAtlas.AtlasRegion up, TextureAtlas.AtlasRegion left, TextureAtlas.AtlasRegion right, TextureAtlas.AtlasRegion down) {
        var leftControlsTable = new Table();
        leftControlsTable.add();
        leftControlsTable.add(TouchpadGamepad.this.setupController(up, Direction.Up));
        leftControlsTable.add().row();

        leftControlsTable.add(setupController(left, Direction.Left));
        leftControlsTable.add();
        leftControlsTable.add(setupController(right, Direction.Right)).row();

        leftControlsTable.add();
        leftControlsTable.add(setupController(down, Direction.Down));
        leftControlsTable.add();
        return leftControlsTable;
    }

    private ImageButton setupController(TextureAtlas.AtlasRegion image, Direction direction) {
        var button = new ImageButton(new TextureRegionDrawable(image));

        var self = this;
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (direction) {
                    case Up:
                        self.jumpJustPressed = true;
                        break;
                    case Down:
                        break;
                    case Left:
                        self.leftPressed = 1;
                        break;
                    case Right:
                        self.rightPressed = 1;
                        break;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switch (direction) {
                    case Up:
                        self.jumpJustPressed = false;
                        break;
                    case Down:
                        break;
                    case Left:
                        self.leftPressed = 0;
                        break;
                    case Right:
                        self.rightPressed = 0;
                        break;
                }
            }
        });
        return button;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void reset(float delta) {
        jumpJustPressed = false;
    }

    @Override
    public void vibrate(int durationInMs, float strength) {

    }
}
