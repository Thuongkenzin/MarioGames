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

import javax.xml.soap.Text;

public class Dinosaur extends Sprite {
    public enum State{RUNNING,JUMPING,STANDING};
    public World world;
    public Body b2body;
    private static final int FRAME_COLS = 7, FRAME_ROWS =6;
    TextureRegion dinosaurStand;
    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    SpriteBatch spriteBatch;
    float stateTime;
    public State currentState;
    public State previousState;

    public Dinosaur(World world){

        this.world = world;
        walkSheet = new Texture(Gdx.files.internal("dinosheet.png"));
        TextureRegion[][] tmp= TextureRegion.split(walkSheet,walkSheet.getWidth()/FRAME_COLS,walkSheet.getHeight()/FRAME_ROWS);
        TextureRegion[] walkFrames =  new TextureRegion[FRAME_COLS];
        int index =0;
        for(int i = 0; i< FRAME_COLS;i++){
            walkFrames[index++] = tmp[1][i];
        }

        walkAnimation = new Animation<TextureRegion>(0.025f,walkFrames);

        dinosaurStand = tmp[0][1];
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
        defineDinosaur();
        setBounds(0,0,100/MarioBros.PIXEL_PER_METER,100/MarioBros.PIXEL_PER_METER);
        setRegion(dinosaurStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2 + 20/MarioBros.PIXEL_PER_METER,b2body.getPosition().y-getHeight()/2 + 15/MarioBros.PIXEL_PER_METER);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        currentState = getState();
        switch (currentState){
            case RUNNING:
                region=  walkAnimation.getKeyFrame(stateTime,true);
                break;

            case JUMPING:
            case STANDING:
                default:
                    region = dinosaurStand;
                    break;
        }

        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y<0)&& previousState == State.JUMPING)
            return State.JUMPING;
        else if(b2body.getLinearVelocity().x !=0)
            return State.RUNNING;
        else
            return State.STANDING;
    }


    public void defineDinosaur(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100/ MarioBros.PIXEL_PER_METER,100/MarioBros.PIXEL_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape= new CircleShape();
        circleShape.setRadius(20/MarioBros.PIXEL_PER_METER);
        fixtureDef.shape = circleShape;
        b2body.createFixture(fixtureDef);
    }
}
