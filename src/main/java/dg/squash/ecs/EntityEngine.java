package dg.squash.ecs;

import dg.squash.ecs.components.NameComponent;
import dg.squash.exceptions.NoSuchNameException;
import dg.squash.utils.Pair;

import java.util.ArrayList;
import java.util.List;


public class EntityEngine {

    private final List<Entity> entities;
    private final List<Pair<Entity, Entity>> collisions;

    public EntityEngine() {
        this.entities = new ArrayList<>();
        this.collisions = new ArrayList<>();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Pair<Entity, Entity>> getCollisions() {
        return collisions;
    }

    public void addCollision(final Pair<Entity, Entity> collision) {
        if (!entities.contains(collision.getT1()))
            entities.add(collision.getT1());
        if (!entities.contains(collision.getT2()))
            entities.add(collision.getT2());
        collisions.add(collision);
    }

    public Entity getEntity(String name) {
        for (Entity e : entities)
            if (e.hasComponent(NameComponent.class) && e.getComponent(NameComponent.class).show().equals(name))
                return e;
        throw new NoSuchNameException(name);
    }

    public boolean hasEntity(String name) {
        for (Entity e : entities)
            if (e.hasComponent(NameComponent.class) && e.getComponent(NameComponent.class).show().equals(name))
                return true;

        return false;
    }

    public void removeCollision(final Pair<Entity, Entity> collision) {
        collisions.remove(collision);
    }

    public void removeEntity(final Entity entity) {
        entities.remove(entity);
        collisions.removeAll(findPairs(entity));
    }

    private List<Pair<Entity, Entity>> findPairs(final Entity entity) {
        List<Pair<Entity, Entity>> removals = new ArrayList<>();
        for (Pair pair : collisions)
            if ((pair.getT1() == entity) || (pair.getT2() == entity))
                removals.add(pair);
        return removals;
    }

}
