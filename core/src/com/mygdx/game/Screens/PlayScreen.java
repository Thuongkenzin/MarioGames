package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MarioBros;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Mario;

public class PlayScreen implements Screen {
    private MarioBros game;
    private Hud hud;
    private OrthographicCamera orthographicCamera;
    private Viewport gamePort;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private Mario player;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    public PlayScreen(MarioBros game){
        this.game = game;

        orthographicCamera = new OrthographicCamera();
        gamePort = new FitViewport(MarioBros.V_WIDTH/MarioBros.PIXEL_PER_METER,MarioBros.V_HEIGHT/MarioBros.PIXEL_PER_METER,orthographicCamera);

        hud = new Hud(game.batch);

        tiledMap = new TmxMapLoader().load("level1.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/MarioBros.PIXEL_PER_METER);
        orthographicCamera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-10),true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        //create Mario character
        player = new Mario(world);

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //create ground body
        for(MapObject object : tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2)/MarioBros.PIXEL_PER_METER, (rect.getY() + rect.getHeight()/2)/MarioBros.PIXEL_PER_METER);

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

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2)/MarioBros.PIXEL_PER_METER, (rect.getY() + rect.getHeight()/2)/MarioBros.PIXEL_PER_METER);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2/MarioBros.PIXEL_PER_METER,rect.getHeight()/2/MarioBros.PIXEL_PER_METER );

            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
        // create coin bodies
        for(MapObject object : tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2)/MarioBros.PIXEL_PER_METER, (rect.getY() + rect.getHeight()/2)/MarioBros.PIXEL_PER_METER);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2/MarioBros.PIXEL_PER_METER,rect.getHeight()/2/MarioBros.PIXEL_PER_METER );

            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);

        }
    }
    @Override
    public void show() {

    }

    public void handleInput(float dt){
       if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
           player.b2body.applyLinearImpulse(new Vector2(0,4f),player.b2body.getWorldCenter(),true);
       if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=2)
           player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
    }
    public void update(float dt){
        handleInput(dt);
        // add fps game
        world.step(1/60f,6,2);
        orthographicCamera.position.x = player.b2body.getPosition().x;
        orthographicCamera.update();
        orthogonalTiledMapRenderer.setView(orthographicCamera);
    }
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        orthogonalTiledMapRenderer.render();
        //render our Box2DDebugLines
        box2DDebugRenderer.render(world,orthographicCamera.combined);

        //set our batch to now draw what the Hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
