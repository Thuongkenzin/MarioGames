package com.mygdx.game;

import android.content.Intent;
import android.os.Bundle;

public class GameDisplay  extends Activity {
    private GameSurface s;
    private AnimationLayout animationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        //Delete title
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//        // Set display Activity.
//        //this.setContentView(new GameSurface(this));
//
//        Intent intent = getIntent();
//        ArrayList<String> code = intent.getStringArrayListExtra("Block");
//        int type = -1;
//        int step;
//        switch (code.get(0)){
//            case "aHead":
//                type = 0;
//                 step = Integer.parseInt(code.get(1));
//                s = new GameSurface(this, step, type);
//                this.setContentView(s);
//                break;
//            case "Back":
//                type = 1;
//                 step = Integer.parseInt(code.get(1));
//                 s = new GameSurface(this, step, type);
//                this.setContentView(s);
//                break;
//        }

        Intent intent = getIntent();
        int level = intent.getIntExtra("Level", 1);
        animationLayout = new AnimationLayout(this, level);
        setContentView(animationLayout);
    }

//    @Override
//    protected void onPause(){
//        super.onPause();
//        //animationLayout.pause();
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        //s.resume();
//    }

}
