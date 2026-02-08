package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * A simple implementation of a collision map for the JPacman player.
 * <p>
 * It uses a number of instanceof checks to implement the multiple dispatch for the 
 * collisionmap. For more realistic collision maps, this approach will not scale,
 * and the recommended approach is to use a {@link CollisionInteractionMap}.
 *
 * @author Arie van Deursen, 2014
 *
 */

public class PlayerCollisions extends CollisionInteractionMap {
    private PointCalculator pointCalculator;
    /**
    * Create a simple player-based collision map, informing the
    * point calculator about points to be added.
    *
    * @param pointCalculator
    * Strategy for calculating points.
    */
    public PlayerCollisions(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
        this.onCollision(Player.class, Ghost.class, (player, ghost) -> {
            pointCalculator.collidedWithAGhost(player, ghost);
            player.removeLife();
            if(!player.isAlive()) {
                player.setKiller(ghost);
            }
        });
        this.onCollision(Player.class, Pellet.class, (player, pellet) -> {
            pointCalculator.consumedAPellet(player, pellet);
            pellet.leaveSquare();
        });
    }
}
