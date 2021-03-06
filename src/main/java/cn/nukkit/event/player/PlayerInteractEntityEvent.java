package cn.nukkit.event.player;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.item.Item;
import cn.nukkit.player.Player;
import com.nukkitx.math.vector.Vector3f;

/**
 * Created by CreeperFace on 1. 1. 2017.
 */
public class PlayerInteractEntityEvent extends PlayerEvent implements Cancellable {


    private static final HandlerList handlers = new HandlerList();

    protected final Entity entity;
    protected final Item item;
    protected final Vector3f clickedPos;

    public PlayerInteractEntityEvent(Player player, Entity entity, Item item, Vector3f clickedPos) {
        this.player = player;
        this.entity = entity;
        this.item = item;
        this.clickedPos = clickedPos;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Item getItem() {
        return this.item;
    }

    public Vector3f getClickedPos() {
        return clickedPos;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
