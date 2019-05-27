package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioBros;


public abstract class InteractiveTileObject {
    private World world;
    private TiledMap tiledMap;
    private TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.tiledMap = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth()/2)/ MarioBros.PIXEL_PER_METER, (bounds.getY() + bounds.getHeight()/2)/MarioBros.PIXEL_PER_METER);

        body = world.createBody(bodyDef);

        shape.setAsBox(bounds.getWidth()/2/MarioBros.PIXEL_PER_METER,bounds.getHeight()/2/MarioBros.PIXEL_PER_METER );

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
}
