package com.mygdx.game;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.mygdx.game.Block.BlockCommand;
import com.mygdx.game.Block.MultipleBlock;
import com.mygdx.game.Block.SingleBlock;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class AnimationLayout extends View {
    public static final int MY_REQUEST_CODE = 100;
    private static final int STOP = 4;
    private static boolean codeGet = false;
    private static boolean firstRunCode = false;
    private static int codeCount = -1;
    private static boolean keepCount = false;
    private static ArrayList<BlockCommand> blockList;

    Paint red_fill;
    Paint red_stroke;
    public Explosion explosion;
    //    private static int step;
//    private static int type;
    private boolean startStep = true;
    private boolean endStep = false;
    private Context context;
    int level;
    int playCount = 3;
    LevelBase levelBase;

    public AnimationLayout(Context context, int level) {
        super(context);
        this.context = context;
        this.level = level;
        setBackgroundResource(R.drawable.background);
        InputStream is = null;
        AssetManager mgr = context.getAssets();
        try {
            is = mgr.open("dinosheet.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap chibiBitmap = BitmapFactory.decodeStream(is);
        switch (level) {
            case 1:



                levelBase = new Level1(this, chibiBitmap);
                break;
            case 2:
                levelBase = new Level2(this, chibiBitmap);
                break;
            case 3:
                levelBase = new Level3(this, chibiBitmap);
                break;
            case 4:
                levelBase = new Level4(this, chibiBitmap);
                break;
            case 5:
                levelBase = new Level5(this, chibiBitmap);
                break;
            case 6:
                levelBase = new Level6(this, chibiBitmap);
                break;
            case 7:
                levelBase = new Level7(this, chibiBitmap);
                break;
            case 8:
                levelBase = new Level8(this, chibiBitmap);
                break;
            case 9:
                levelBase = new Level9(this, chibiBitmap);
                break;
            default:
                break;
        }
    }

    public static void setCodeGenerate(ArrayList<BlockCommand> blockCommands) {
        blockList = blockCommands;
        codeGet = true;
        codeCount = blockList.size();
        firstRunCode = true;
    }

    protected void checkSingleBlock(SingleBlock singleBlock) {
        if (codeGet) {
            int step = singleBlock.getStep() * 100;
            switch (singleBlock.getName()) {
                case "aHead":
                    levelBase.getChibi().setMovingVector(step, 0);
                    break;
                case "Back":
                    levelBase.getChibi().setMovingVector(-(step), 0);
                    break;
                case "Left":
                    levelBase.getChibi().setMovingVector(0, -(step));
                    break;
                case "Right":
                    levelBase.getChibi().setMovingVector(0, step);
                    break;
            }
            codeGet = false;
        }
    }

    protected void checkMultipleChild(MultipleBlock multipleBlock){
        switch (multipleBlock.getName()) {
            case "repeat":
                if (multipleBlock.getCurTime() < multipleBlock.getTimes()) {
                    String s = multipleBlock.getBlock().getClass().getSimpleName();
                    switch (s) {
                        case "SingleBlock":
                            checkSingleBlock((SingleBlock) multipleBlock.getBlock());
                            multipleBlock.increaseCurBlock();
                            break;
                        case "MultipleBlock":
                            MultipleBlock tempCur = (MultipleBlock) multipleBlock.getBlock();
                            checkMultipleBlock((MultipleBlock) multipleBlock.getBlock());
                            if (tempCur.getCurTime() == tempCur.getTimes())
                                multipleBlock.increaseCurBlock();
                    }
//                        multipleBlock.increaseCurBlock();
                    if (multipleBlock.getCurBlock() == multipleBlock.getBlockListSize()) {
                        multipleBlock.resetCurBlock();
                        multipleBlock.increaseCurTime();
                    }
                    keepCount = true;
                } else {
                    keepCount = false;
                }
                break;
            case "if":
                if (multipleBlock.getIfCondition()) {
                    String s = multipleBlock.getBlock().getClass().getSimpleName();
                    switch (s) {
                        case "SingleBlock":
                            checkSingleBlock((SingleBlock) multipleBlock.getBlock());
                            break;
                        case "MultipleBlock":
                            checkMultipleBlock((MultipleBlock) multipleBlock.getBlock());
                    }
                    multipleBlock.increaseCurBlock();
                    keepCount = true;
                    if (multipleBlock.getCurBlock() == multipleBlock.getBlockListSize()) {
                        keepCount = false;
                    }
                }
                break;
            case "while":
                break;
        }
    }

    protected void checkMultipleBlock(MultipleBlock multipleBlock) {
        if (codeGet) {
            switch (multipleBlock.getName()) {
                case "repeat":
                    if (multipleBlock.getCurTime() < multipleBlock.getTimes()) {
                        String s = multipleBlock.getBlock().getClass().getSimpleName();
                        switch (s) {
                            case "SingleBlock":
                                checkSingleBlock((SingleBlock) multipleBlock.getBlock());
                                multipleBlock.increaseCurBlock();
                                break;
                            case "MultipleBlock":
                                MultipleBlock tempCur = (MultipleBlock) multipleBlock.getBlock();
                                checkMultipleBlock((MultipleBlock) multipleBlock.getBlock());
                                if (tempCur.getCurTime() == tempCur.getTimes()) {
                                    tempCur.resetCurTime();
                                    multipleBlock.increaseCurBlock();
                                }
                                break;
                        }
//                        multipleBlock.increaseCurBlock();
                        if (multipleBlock.getCurBlock() == multipleBlock.getBlockListSize()) {
                            multipleBlock.resetCurBlock();
                            multipleBlock.increaseCurTime();
                        }
                        keepCount = true;
                    } else {
                        keepCount = false;
                    }
                    break;
                case "if":
                    if (multipleBlock.getIfCondition()) {
                        String s = multipleBlock.getBlock().getClass().getSimpleName();
                        switch (s) {
                            case "SingleBlock":
                                checkSingleBlock((SingleBlock) multipleBlock.getBlock());
                                multipleBlock.increaseCurBlock();
                                break;
                            case "MultipleBlock":
                                MultipleBlock tempCur = (MultipleBlock) multipleBlock.getBlock();
                                checkMultipleBlock((MultipleBlock) multipleBlock.getBlock());
                                if (tempCur.getCurTime() == tempCur.getTimes()) {
                                    tempCur.resetCurTime();
                                    multipleBlock.increaseCurBlock();
                                }
                                break;
                        }
                        keepCount = true;
                        if (multipleBlock.getCurBlock() == multipleBlock.getBlockListSize()) {
                            multipleBlock.resetCurBlock();
                            multipleBlock.increaseCurTime();
                            keepCount = false;
                        }
                    }
                    break;
                case "while":
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawInitResource(canvas);
        if (blockList != null && codeCount != 0) {
            String s = blockList.get(blockList.size() - codeCount).getClass().getSimpleName();
            switch (s) {
                case "SingleBlock":
                    checkSingleBlock((SingleBlock) blockList.get(blockList.size() - codeCount));
                    break;
                case "MultipleBlock":
                    checkMultipleBlock((MultipleBlock) blockList.get(blockList.size() - codeCount));
            }
        }
        drawResource(canvas);
    }

    protected void drawInitResource(Canvas canvas) {
        //Default color
        red_fill = new Paint();
        red_fill.setColor(Color.RED);
        red_fill.setStyle(Paint.Style.FILL);
        //Stroke color
        red_stroke = new Paint();
        red_stroke.setColor(Color.RED);
        red_stroke.setStyle(Paint.Style.STROKE);
        red_stroke.setStrokeWidth(10);

        Paint text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(20);

        //Draw Point
        for (int i = 0; i < levelBase.listPoint.size(); i++) {
            canvas.drawCircle(levelBase.listPoint.get(i).x, levelBase.listPoint.get(i).y, 10, red_fill);
            canvas.drawText((i + 1) + "", levelBase.listPoint.get(i).x - 10, levelBase.listPoint.get(i).y + 30, text);
        }
        //Test draw chibi
        Log.e("X Y", levelBase.getChibi().x + "" + levelBase.getChibi().y);
        this.levelBase.getChibi().draw(canvas);
        if (startStep) {
            showGuideDialog();
            startStep = false;
        } else {
            checkFinish();
        }
        if (explosion != null) {
            if (!explosion.isFinish()) {
                explosion.draw(canvas);
            } else {
                endStep = true;
            }
        } else {
            canvas.drawBitmap(levelBase.getMonster().bitmap, levelBase.getMonster().x, levelBase.getMonster().y, null);
        }
    }

    protected void drawResource(Canvas canvas) {
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!endStep)
            invalidate();
        else {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            codeCount = -1;
            blockList.clear();
            blockList = null;
            showFinishDialog();
        }
    }

    protected boolean checkFinish() {
        //if (levelBase.getChibi().getX() + levelBase.getChibi().getWidth() >= levelBase.getMonster().x + levelBase.getMonster().bitmap.getWidth() && explosion == null) {
//        if (levelBase.getChibi().getX() < levelBase.getMonster().x + levelBase.getMonster().bitmap.getWidth() && levelBase.getMonster().x < levelBase.getChibi().getX() && levelBase.getChibi().getY() < levelBase.getMonster().y + levelBase.getMonster().bitmap.getHeight() && levelBase.getMonster().y < levelBase.getChibi().getY() && explosion == null) {

        if (levelBase.getChibi().getX() == levelBase.getMonster().x && levelBase.getChibi().getY() == levelBase.getMonster().y && explosion == null) {
            Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.explosion);
            explosion = new Explosion(this, bm, levelBase.getMonster().x, levelBase.getMonster().y + levelBase.getMonster().bitmap.getHeight() / 2);
            levelBase.setMonsterCount(levelBase.getMonsterCount() - 1);
            return true;
        }
        if (levelBase.getChibi().getState() == STOP && codeCount != -1 && !firstRunCode) {
            if (codeCount == 0) {
                if (explosion == null)
                    endStep = true;
            } else {
                if (!keepCount)
                    codeCount--;
                codeGet = true;
                levelBase.getChibi().resetStartXY();
            }
        } else {
            if (levelBase.getChibi().getState() == STOP && codeCount != -1)
                firstRunCode = false;
        }
        return false;
    }

    public void showFinishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        if (levelBase.getMonsterCount() == 0) {
            builder.setTitle("Congratulations");
            builder.setMessage("Chúc mừng bạn đã vượt qua thử thách\n Số điểm đạt được: " + playCount + " point\n");
            builder.setCancelable(false);
            builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    GameDisplay gameDisplay = (GameDisplay) getContext();
                    Intent intent = new Intent();
                    intent.putExtra("Finish", true);
                    intent.putExtra("Level", level + 1);
                    intent.putExtra("Next", true);
                    gameDisplay.setResult(Activity.RESULT_OK, intent);
                    gameDisplay.finish();
                }
            });
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    GameDisplay gameDisplay = (GameDisplay) getContext();
                    Intent intent = new Intent();
                    intent.putExtra("Finish", true);
                    intent.putExtra("Level", level + 1);
                    intent.putExtra("Next", false);
                    gameDisplay.setResult(Activity.RESULT_OK, intent);
                    gameDisplay.finish();
                }
            });
        } else {
            builder.setTitle("LOSE");
            builder.setMessage("Bạn chưa hoàn thành thử thách");
            builder.setCancelable(false);
            builder.setPositiveButton("Chơi lại", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    playCount--;
                    startStep = true;
                    endStep = false;
                    codeCount = -1;
                    levelBase.getChibi().setXY(20, 270);
                    invalidate();
                }
            });
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    GameDisplay gameDisplay = (GameDisplay) getContext();
                    gameDisplay.finish();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showGuideDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Mission");
        builder.setMessage(levelBase.getMisssion());
        builder.setCancelable(false);
        builder.setNegativeButton("Ready", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameDisplay gameDisplay = (GameDisplay) getContext();
                Intent intent = new Intent(gameDisplay, CustomBlock.class);
                myStartActivityForResult((FragmentActivity) getContext(),
                        intent, MY_REQUEST_CODE);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void myStartActivityForResult(FragmentActivity act, Intent in, int requestCode) {
        Fragment aux = new FragmentForResult();
        FragmentManager fm = act.getSupportFragmentManager();
        fm.beginTransaction().add(aux, "FRAGMENT_TAG").commit();
        fm.executePendingTransactions();
        aux.startActivityForResult(in, requestCode);
    }

    @SuppressLint("ValidFragment")
    public static class FragmentForResult extends Fragment {
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
                String codeString = data.getStringExtra("Block");
                ArrayList<String> codes = new ArrayList<>(Arrays.asList(codeString.split("\n")));
                ArrayList<BlockCommand> codeGenerate = parseCodeGenerate(codes);
                setCodeGenerate(codeGenerate);
            }
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }

        public class BlockTemp{
            public ArrayList<String> code;
            public int curCode;
            public int factor;
            public BlockTemp(ArrayList<String> code, int curCode){
                this.code = code;
                this.curCode = curCode;
                factor = 1;
            }
        }
        protected void addMultileBlock(MultipleBlock multipleBlock, BlockTemp blockTemp){
            for (int i = blockTemp.curCode + 1; blockTemp.code.get(i).indexOf("}")== -1; i++) {
                if (blockTemp.code.get(i).indexOf("{")!= -1 || blockTemp.code.get(i).equals(""))
                    continue;
                ArrayList<String> codePartMulti = new ArrayList<>(Arrays.asList(blockTemp.code.get(i).split(" ")));
                BlockCommand childBlock;
                int factor = 2 * blockTemp.factor;
                switch (codePartMulti.get(factor)) {
                    case "aHead":
                    case "Back":
                    case "Left":
                    case "Right":
                        blockTemp.curCode = i;
                        childBlock = new SingleBlock(codePartMulti.get(factor), Integer.parseInt(codePartMulti.get(factor + 1)));
                        multipleBlock.addBlockCommand(childBlock);
                        break;
                    case "repeat":
                        int times = Integer.parseInt(codePartMulti.get(factor + 1));
                        childBlock = new MultipleBlock(codePartMulti.get(factor), Integer.parseInt(codePartMulti.get(factor + 1)));
                        blockTemp.curCode = i;
                        blockTemp.factor++;
                        addMultileBlock((MultipleBlock) childBlock, blockTemp);
                        i = blockTemp.curCode + 1;
                        blockTemp.factor--;
                        multipleBlock.addBlockCommand(childBlock);
                        break;
                    case "if":
                        childBlock = new MultipleBlock(codePartMulti.get(factor), Boolean.parseBoolean(codePartMulti.get(factor + 1)));
                        blockTemp.curCode = i;
                        blockTemp.factor++;
                        addMultileBlock((MultipleBlock) childBlock, blockTemp);
                        i = blockTemp.curCode + 1;
                        blockTemp.factor--;
                        multipleBlock.addBlockCommand(childBlock);
                        break;
                    default:
                        break;
                }
            }
        }
        protected ArrayList<BlockCommand> parseCodeGenerate(ArrayList<String> codeList) {
            ArrayList<BlockCommand> codeGenerate = new ArrayList<>();;
            BlockTemp blockTemp = new BlockTemp(codeList, 0);
            for (int i = 0; i < codeList.size(); i++) {
                ArrayList<String> codePart = new ArrayList<>(Arrays.asList(codeList.get(i).split(" ")));
                BlockCommand temp;
                switch (codePart.get(0)) {
                    case "aHead":
                    case "Back":
                    case "Left":
                    case "Right":
                        temp = new SingleBlock(codePart.get(0), Integer.parseInt(codePart.get(1)));
                        codeGenerate.add(temp);
                        break;
                    case "repeat":
                        int times = Integer.parseInt(codePart.get(1));
                        temp = new MultipleBlock(codePart.get(0), Integer.parseInt(codePart.get(1)));
                        blockTemp.curCode = i;
                        addMultileBlock((MultipleBlock) temp, blockTemp);
                        i = blockTemp.curCode;
                        codeGenerate.add(temp);
                        break;
                    case "if":
                        temp = new MultipleBlock(codePart.get(0), Boolean.parseBoolean(codePart.get(1)));
                        blockTemp.curCode = i;
                        addMultileBlock((MultipleBlock) temp, blockTemp);
                        i = blockTemp.curCode;
                        codeGenerate.add(temp);
                        break;
                    default:
                        break;
                }
            }
            if (codeGenerate.size() == 0) {
                codeGenerate.add(new SingleBlock("aHead", 0));
            }
            return codeGenerate;
        }

    }
}


