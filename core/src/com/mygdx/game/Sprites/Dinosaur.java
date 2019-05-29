package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioBros;

public class Dinosaur extends Sprite {
    public World world;
    public Body b2body;
    private static final int FRAME_COLS = 7, FRAME_ROWS =6;
    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    SpriteBatch spriteBatch;
    float stateTime;

    public Dinosaur(World world){

        this.world = world;
        walkSheet = new Texture(Gdx.files.internal("dinosheet.png"));
        TextureRegion[][] tmp= TextureRegion.split(walkSheet,walkSheet.getWidth()/FRAME_COLS,walkSheet.getHeight()/FRAME_ROWS);
        TextureRegion[] walkFrames =  new TextureRegion[FRAME_COLS];
        int index =0;
        for(int i = 0; i< FRAME_COLS;i++){
            walkFrames[index++] = tmp[0][i];
        }

        walkAnimation = new Animation<TextureRegion>(0.025f,walkFrames);

        spriteBatch = new SpriteBatch();
        stateTime = 0f;
        defineDinosaur();
        setBounds(0,0,23/MarioBros.PIXEL_PER_METER,23/MarioBros.PIXEL_PER_METER);
        setRegion(tmp[1][1]);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y-getHeight()/2);
    }
    public void defineDinosaur(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100/ MarioBros.PIXEL_PER_METER,32/MarioBros.PIXEL_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape= new CircleShape();
        circleShape.setRadius(6/MarioBros.PIXEL_PER_METER);
        fixtureDef.shape = circleShape;
        b2body.createFixture(fixtureDef);
    }
}
