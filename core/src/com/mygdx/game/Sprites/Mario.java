package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MarioBros;
import com.mygdx.game.Screens.PlayScreen;



public class Mario extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    public State currentState;
    public State previousState;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer =0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0;i<4;i++){
            frames.add(new TextureRegion(getTexture(),i*16,11,16,16));
        }
        marioRun = new Animation(0.1f,frames);
        frames.clear();

        for(int i=4;i<6;i++){
            frames.add(new TextureRegion(getTexture(),i*16,11,16,16));
        }
        marioJump = new Animation(0.1f,frames);
        defineMario();
        marioStand = new TextureRegion(getTexture(),1,11,16,16);
        setBounds(0,0,16/MarioBros.PIXEL_PER_METER,16/MarioBros.PIXEL_PER_METER);
        setRegion(marioStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() /2,b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
               region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
               break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
                default:
                    region = marioStand;
                    break;
        }

        if((b2body.getLinearVelocity().x<0||!runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }else if((b2body.getLinearVelocity().x >0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y<0)&& previousState == State.JUMPING)
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y<0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x !=0)
            return State.RUNNING;
        else
            return State.STANDING;

    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/ MarioBros.PIXEL_PER_METER,32/MarioBros.PIXEL_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape= new CircleShape();
        circleShape.setRadius(6/MarioBros.PIXEL_PER_METER);
        fixtureDef.shape = circleShape;
        b2body.createFixture(fixtureDef);
    }
}
