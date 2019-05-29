'use strict';

Blockly.JavaScript['character_go'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value = Blockly.JavaScript.valueToCode(block, 'VALUE',
      Blockly.JavaScript.ORDER_NONE) || '0';
  return 'Character.' + block.getFieldValue('DIR') +
      '(' + value + ', \'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['character_turn'] = function(block) {
  // Generate JavaScript for turning left or right.
  var value = Blockly.JavaScript.valueToCode(block, 'VALUE',
      Blockly.JavaScript.ORDER_NONE) || '0';
  return 'Character.' + block.getFieldValue('DIR') +
      '(' + value + ', \'block_id_' + block.id + '\');\n';
};
Blockly.JavaScript['block_go'] = function(block) {
  var dropdown_typego = block.getFieldValue('typeGo');
  var number_step = block.getFieldValue('step');
  // TODO: Assemble JavaScript into code variable.
  var code = dropdown_typego + " " + number_step + "\n"; //+ '(' + number_step + ');\n';
  return code;
};

Blockly.JavaScript['block_turn'] = function(block) {
  var dropdown_turn = block.getFieldValue('Turn');
  var number_step = block.getFieldValue('step');
  // TODO: Assemble JavaScript into code variable.
  var code = dropdown_turn + " " + number_step + "\n";
  return code;
};

Blockly.JavaScript['block_repeat'] = function(block) {
  var number_name = block.getFieldValue('NAME');
  var statements_repeat = Blockly.JavaScript.statementToCode(block, 'Repeat');
  // TODO: Assemble JavaScript into code variable.
  var code = "repeat" + " " + number_name + "\n{\n" + statements_repeat + "}\n";
  return code;
};

Blockly.JavaScript['block_if'] = function(block) {
  var value_name = Blockly.JavaScript.valueToCode(block, 'NAME', Blockly.JavaScript.ORDER_NONE);
  var statements_if = Blockly.JavaScript.statementToCode(block, 'If');
  // TODO: Assemble JavaScript into code variable.
  var code = "if" + " " + value_name + "\n{\n"  + statements_if + "}\n";
  return code;
};

Blockly.JavaScript['block_bool'] = function(block) {
  var dropdown_bool = block.getFieldValue('bool');
  // TODO: Assemble JavaScript into code variable.
  var code = "" + dropdown_bool + "\n";
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript.workspaceToCodeWithId = Blockly.JavaScript.workspaceToCode;

Blockly.JavaScript.workspaceToCode = function(workspace) {
  var code = this.workspaceToCodeWithId(workspace);
  // Strip out block IDs for readability.
  code = goog.string.trimRight(code.replace(/(,\s*)?'block_id_[^']+'\)/g, ')'))
  return code;
};