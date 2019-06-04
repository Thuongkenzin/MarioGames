package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioBros;
import com.mygdx.game.Sprites.Brick;
import com.mygdx.game.Sprites.Coin;

public class B2WorldCreator {


    public B2WorldCreator(World world, TiledMap tiledMap){
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //create ground body
        for(MapObject object : tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2)/ MarioBros.PIXEL_PER_METER, (rect.getY() + rect.getHeight()/2)/MarioBros.PIXEL_PER_METER);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2/MarioBros.PIXEL_PER_METER,rect.getHeight()/2/MarioBros.PIXEL_PER_METER );

            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);

        }

        // create pipes bodies fixture

        for(MapObject object : tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2)/MarioBros.PIXEL_PER_METER, (rect.getY() + rect.getHeight()/2)/MarioBros.PIXEL_PER_METER);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2/MarioBros.PIXEL_PER_METER,rect.getHeight()/2/MarioBros.PIXEL_PER_METER );

            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);

        }
        // create brick
        for(MapObject object : tiledMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(world,tiledMap,rect);
        }
        // create coin bodies
        for(MapObject object : tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(world, tiledMap, rect);
        }
    }
}
