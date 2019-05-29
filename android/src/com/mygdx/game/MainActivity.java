package com.mygdx.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnBlockTest;
    private Button btnCustomTest;
    private Button btnCharacterTest;
    private ArrayList<Button>levelButton;
    public static final int MY_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBlockTest = (Button) findViewById(R.id.btnBlockTest);
        btnBlockTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Activity.class);
                startActivity(intent);
            }
        });
        btnCustomTest = (Button)findViewById(R.id.btnCustomTest);
        btnCustomTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CustomBlock.class);
                startActivity(intent);
            }
        });
        btnCharacterTest = (Button)findViewById(R.id.btnCharacterTest);
        btnCharacterTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AndroidLauncher.class);
                startActivity(intent);
            }
        });
        levelButton = new ArrayList<Button>();
        levelButton.add((Button)findViewById(R.id.btnlv1));
        levelButton.add((Button)findViewById(R.id.btnlv2));
        levelButton.add((Button)findViewById(R.id.btnlv3));
        levelButton.add((Button)findViewById(R.id.btnlv4));
        levelButton.add((Button)findViewById(R.id.btnlv5));
        levelButton.add((Button)findViewById(R.id.btnlv6));
        levelButton.add((Button)findViewById(R.id.btnlv7));
        levelButton.add((Button)findViewById(R.id.btnlv8));
        levelButton.add((Button)findViewById(R.id.btnlv9));
        for (int i = 0; i < 9; i++) {
            final int level = i + 1;
            levelButton.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,GameDisplay.class);
                    intent.putExtra("Level", level);
                    startActivityForResult(intent, MY_REQUEST_CODE);
                }
            });
        }
        for (int i = 1; i < 9; i++) {
            levelButton.get(i).setEnabled(false);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean finish = data.getBooleanExtra("Finish", false);
            if(finish) {
                int nextLevel = data.getIntExtra("Level", 1);
                levelButton.get(nextLevel - 1).setEnabled(true);
                boolean next = data.getBooleanExtra("Next", false);
                if (next) {
                    Intent intent = new Intent(MainActivity.this, GameDisplay.class);
                    intent.putExtra("Level", nextLevel);
                    startActivityForResult(intent, MY_REQUEST_CODE);
                }
            }
        }
    }
}
