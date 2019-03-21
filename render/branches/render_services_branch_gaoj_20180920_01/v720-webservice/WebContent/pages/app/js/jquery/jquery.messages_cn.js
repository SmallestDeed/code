var verPrefix = "<font style='color: red;font-size: small;'>";
var verSuffix = "</font>";
verPrefix ="";
verSuffix = "";
jQuery.extend(jQuery.validator.messages, {
  required		: 	verPrefix +"必填字段!"+ verSuffix,
  remote		: 	verPrefix +"请修正该字段!"+ verSuffix,
  email			: 	verPrefix +"请输入正确格式的电子邮件!"+ verSuffix,
  url			: 	verPrefix +"请输入合法的网址!"+ verSuffix,
  date			: 	verPrefix +"请输入合法的日期!",
  dateISO		: 	verPrefix +"请输入合法的日期 (ISO)."+ verSuffix,
  number		: 	verPrefix +"请输入合法的数字!"+ verSuffix,
  digits		: 	verPrefix +"只能输入整数!"+ verSuffix,
  creditcard	: 	verPrefix +"请输入合法的信用卡号!"+ verSuffix,
  equalTo		: 	verPrefix +"请再次输入相同的值!"+ verSuffix,
  accept		: 	verPrefix +"请输入拥有合法后缀名的字符串!"+ verSuffix,
  maxlength		: 	jQuery.validator.format(verPrefix +"请输入一个长度最多是 {0}的字符串!"+ verSuffix),
  minlength		: 	jQuery.validator.format(verPrefix +"请输入一个长度最少是 {0}的字符串!"+ verSuffix),
  rangelength	: 	jQuery.validator.format(verPrefix +"请输入一个长度介于 {0}和 {1}之间的字符串!"+ verSuffix),
  range			: 	jQuery.validator.format(verPrefix +"请输入一个介于 {0}和 {1}之间的值!"+ verSuffix),
  max			: 	jQuery.validator.format(verPrefix +"请输入一个最大为{0}的值!"+ verSuffix),
  min			: 	jQuery.validator.format(verPrefix +"请输入一个最小为{0}的值!"+ verSuffix)
});