package main.java.characters.projectiles;

import main.java.characters.Hero;

import java.awt.geom.Point2D;

public class EnemyBubble extends Bubble {
    public EnemyBubble(Point2D.Double point, boolean isMovingRight) {
        super(point, isMovingRight);
        setCharacterType(CharacterType.ENEMY_BUBBLE);
    }

    @Override
    public String getName() {
        return "enemyBubble";
    }

    @Override
    public void collideWith(Hero h) {
        markToRemove();
        h.setIsDead(true);
    }
}
