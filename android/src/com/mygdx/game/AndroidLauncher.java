package com.mygdx.game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.Block.BlockCommand;
import com.mygdx.game.Block.MultipleBlock;
import com.mygdx.game.Block.SingleBlock;
import com.mygdx.game.MarioBros;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.AnimationLayout.MY_REQUEST_CODE;
import static com.mygdx.game.AnimationLayout.myStartActivityForResult;

public class AndroidLauncher extends AndroidApplication {
	public static final int MY_REQUEST_CODE = 100;
	MarioBros marioBros;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		marioBros = new MarioBros();
		initialize(marioBros, config);
		showGuideDialog();
	}

	public void showGuideDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Mission");
		builder.setMessage("Di chuyen");
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
				Intent intent = new Intent(AndroidLauncher.this, CustomBlock.class);
//                marioBros.dispose();
				startActivityForResult(intent, MY_REQUEST_CODE);
			}
		});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
			String codeString = data.getStringExtra("Block");
			ArrayList<String> codes = new ArrayList<>(Arrays.asList(codeString.split("\n")));
			ArrayList<BlockCommand> codeGenerate = parseCodeGenerate(codes);
			marioBros.setCodeGenerate(codeGenerate);
		}
	}

	public class BlockTemp {
		public ArrayList<String> code;
		public int curCode;
		public int factor;

		public BlockTemp(ArrayList<String> code, int curCode) {
			this.code = code;
			this.curCode = curCode;
			factor = 1;
		}
	}

	protected void addMultileBlock(MultipleBlock multipleBlock, BlockTemp blockTemp) {
		for (int i = blockTemp.curCode + 1; blockTemp.code.get(i).indexOf("}") == -1; i++) {
			if (blockTemp.code.get(i).indexOf("{") != -1 || blockTemp.code.get(i).equals(""))
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
		ArrayList<BlockCommand> codeGenerate = new ArrayList<>();
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
