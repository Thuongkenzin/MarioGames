package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.mygdx.game.Block.MultipleBlock;
import com.mygdx.game.Block.SingleBlock;
import com.mygdx.game.MarioBros;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Dinosaur;
import com.mygdx.game.Sprites.Mario;
import com.mygdx.game.Tools.B2WorldCreator;

public class PlayScreen implements Screen {
    private MarioBros game;
    private Hud hud;
    private OrthographicCamera orthographicCamera;
    private Viewport gamePort;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private Mario player;
    private Dinosaur dinosaur;
    private TextureAtlas atlas;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    public PlayScreen(MarioBros game){
        this.game = game;

        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        orthographicCamera = new OrthographicCamera();
        gamePort = new FitViewport(MarioBros.V_WIDTH/MarioBros.PIXEL_PER_METER,MarioBros.V_HEIGHT/MarioBros.PIXEL_PER_METER,orthographicCamera);

        hud = new Hud(game.batch);

        //tiledMap = new TmxMapLoader().load("level1.tmx");
        tiledMap = new TmxMapLoader().load("MapGame/Map2.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/MarioBros.PIXEL_PER_METER);
        orthographicCamera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-10),true);
        box2DDebugRenderer = new Box2DDebugRenderer();
       dinosaur = new Dinosaur(world);
        //create Mario character
        player = new Mario(world, this);

        //new B2WorldCreator(world,tiledMap);
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



    }
    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {

    }

    public void moveStep(int step){

        for(int k =0;k<step;k++ ) {
            // moi buoc thi can 1 vong lap 22 lan
            for (int i = 0; i < 22; i++) {
                //kiem tra gia tri velocity neu chay nhanh qua thi khong tang len nua
                if (dinosaur.b2body.getLinearVelocity().x <= 2.5)
                    dinosaur.b2body.applyLinearImpulse(new Vector2(0.1f, 0), dinosaur.b2body.getWorldCenter(), true);
                    //dinosaur.b2body.applyForce(new Vector2(1.5f, 0), dinosaur.b2body.getWorldCenter(), true);
            }
            //xet velocity ve khong nhung khong chay duoc
            dinosaur.b2body.setLinearVelocity(0.1f, dinosaur.b2body.getLinearVelocity().y);
//        for(int j= 0;j< step;j++) {
//
//            dinosaur.b2body.setLinearVelocity(1, 0);
//            for (int i = 0; i < 15; i++) {
//                if (dinosaur.b2body.getLinearVelocity().x <= 2) {
//                    dinosaur.b2body.applyLinearImpulse(new Vector2(0.1f, 0), dinosaur.b2body.getWorldCenter(), true);
//
//                }
//                dinosaur.b2body.setLinearVelocity(0, 0);
//            }
//        }
        }

    }
    protected void checkSingleBlock(SingleBlock singleBlock) {
        if (MarioBros.codeGet) {
            int step = singleBlock.getStep();
            switch (singleBlock.getName()) {
                case "aHead":
                    moveStep(step);
                    moveStep(step);
                    moveStep(step);
                    moveStep(step);

                    break;
                case "Back":
                    player.b2body.applyLinearImpulse(new Vector2(-0.5f,0),player.b2body.getWorldCenter(),true);
                    break;
                case "Left":
                    break;
                case "Right":
                    break;
            }
            MarioBros.codeGet = false;
        }
    }
    public void handleInput(float dt){
//        if(MarioBros.blockList !=null) {
//            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
//        }
        if (MarioBros.blockList != null && MarioBros.codeCount != 0) {
            String s = MarioBros.blockList.get(MarioBros.blockList.size() - MarioBros.codeCount).getClass().getSimpleName();
            switch (s) {
                case "SingleBlock":
                    checkSingleBlock((SingleBlock) MarioBros.blockList.get(MarioBros.blockList.size() - MarioBros.codeCount));
                    break;
                case "MultipleBlock":
                    //checkMultipleBlock((MultipleBlock) MarioBros.blockList.get(MarioBros.blockList.size() - MarioBros.codeCount));
                    break;
            }
        }
       if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=2)
           //player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
           dinosaur.b2body.applyLinearImpulse(new Vector2(0.1f, 0), dinosaur.b2body.getWorldCenter(), true);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
    }

    public void update(float dt){

        // add fps game
       // player.update(dt);
        dinosaur.update(dt);
        world.step(1/60f,6,2);
        handleInput(dt);
        //orthographicCamera.position.x = player.b2body.getPosition().x;
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

        game.batch.setProjectionMatrix(orthographicCamera.combined);
        game.batch.begin();
        player.draw(game.batch);
        dinosaur.draw(game.batch);
        game.batch.end();
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
        world.dispose();
        box2DDebugRenderer.dispose();
        tiledMap.dispose();
        orthogonalTiledMapRenderer.dispose();
        hud.dispose();
    }
}
