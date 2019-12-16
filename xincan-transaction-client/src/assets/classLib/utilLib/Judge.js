/**
 * @Author Jack_Jo
 * @Date 2018/4/15
 * @Description: 验证方法
 */

export default{
  //常规名称验证
  isName:function (value) {
    let reg = /^[\u4e00-\u9fa50-9a-zA-Z]([\u4e00-\u9fa50-9a-zA-Z_]*[\u4e00-\u9fa50-9a-zA-Z])*$/;
    if(!reg.test(value)){
      return false;
    }
    return true;
  },
  //邮箱验证 支持 多邮箱
  isEmail:function (value) {
    var reg = new RegExp("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$"); //正则表达式
    if(value.length > 0){
      var obj = value.split(",");
      for(var i=0;i<obj.length;i++){
        if(!reg.test(obj[i])){
          return false;
        }
      }
      return true;
    }
  },
  //多邮箱验证
  isMoreEmail:function(e) {
    var reg = new RegExp("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$"); //正则表达式
    if(e.length > 0){
      var obj = e.split(",");
      for(var i=0;i<obj.length;i++){
        if(!reg.test(obj[i])){
          return false;
        }
      }
      return true;
    }else {

    }
  },
  //SNMP地址验证 支持 多SNMP地址
  isSnmp :function(value) {
    var reg = new RegExp("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?[1-9])))(/\\d{1,5}))"); //正则表达式
    if(value.length > 0){
      var obj = value.split(",");
      for(var i=0;i<obj.length;i++){
        if(!reg.test(obj[i])){
          return false;
        }
      }
      return true;
    }
  },
  //手机号码验证 支持 多手机号码
  isPhone:function (value) {
    var reg = new RegExp("^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$"); //正则表达式
    if(value.length > 0){
      var obj = value.split(",");
      for(var i=0;i<obj.length;i++){
        if(!reg.test(obj[i])){
          return false;
        }
      }
      return true;
    }
  },

  //是否重复
  isRepeat:function (list,key,value) {
    for(let i in list){
      if(list[i][key]===value){
        return false;
      }
    }
    return true;
  },
  //验证Cron表达式
  cronValidate:function(e) {
    var cron = e;
    var result = this.validateCronExpression(cron);
    return result;
  },
  validateCronExpression:function(value) {
    var results = true;
    if (value == null || value.length == 0) {
      return false;
    }

    // split and test length
    var expressionArray = value.split(" ");
    var len = expressionArray.length;

    if ((len != 6) && (len != 7)) {
      return false;
    }

    // check only one question mark
    var match = value.match(/\?/g);
    if (match != null && match.length > 1) {
      return false;
    }

    // check only one question mark
    var dayOfTheMonthWildcard = "";

    // if appropriate length test parts
    // [0] Seconds 0-59 , - * /
    if (this.isNotWildCard(expressionArray[0], /[\*]/gi)) {
      if (!this.segmentValidator("([0-9\\\\,-\\/])", expressionArray[0], [0, 59], "seconds")) {
        return false;
      }
    }

    // [1] Minutes 0-59 , - * /
    if (this.isNotWildCard(expressionArray[1], /[\*]/gi)) {
      if (!this.segmentValidator("([0-9\\\\,-\\/])", expressionArray[1], [0, 59], "minutes")) {
        return false;
      }
    }

    // [2] Hours 0-23 , - * /
    if (this.isNotWildCard(expressionArray[2], /[\*]/gi)) {
      if (!this.segmentValidator("([0-9\\\\,-\\/])", expressionArray[2], [0, 23], "hours")) {
        return false;
      }
    }

    // [3] Day of month 1-31 , - * ? / L W C
    if (this.isNotWildCard(expressionArray[3], /[\*\?]/gi)) {
      if (!this.segmentValidator("([0-9LWC\\\\,-\\/])", expressionArray[3], [1, 31], "days of the month")) {
        return false;
      }
    } else {
      dayOfTheMonthWildcard = expressionArray[3];
    }

    // [4] Month 1-12 or JAN-DEC , - * /
    if (this.isNotWildCard(expressionArray[4], /[\*]/gi)) {
      expressionArray[4] = this.convertMonthsToInteger(expressionArray[4]);
      if (!this.segmentValidator("([0-9\\\\,-\\/])", expressionArray[4], [1, 12], "months")) {
        return false;
      }
    }

    // [5] Day of week 1-7 or SUN-SAT , - * ? / L C #
    if (this.isNotWildCard(expressionArray[5], /[\*\?]/gi)) {
      expressionArray[5] = this.convertDaysToInteger(expressionArray[5]);
      if (!this.segmentValidator("([0-9LC#\\\\,-\\/])", expressionArray[5], [1, 7], "days of the week")) {
        return false;
      }
    } else {
      if (dayOfTheMonthWildcard == String(expressionArray[5])) {
        return false;
      }
    }

    // [6] Year empty or 1970-2099 , - * /
    if (len == 7) {
      if (this.isNotWildCard(expressionArray[6], /[\*]/gi)) {
        if (!this.segmentValidator("([0-9\\\\,-\\/])", expressionArray[6], [1970, 2099], "years")) {
          return false;
        }
      }
    }
    return true;
  },
  // isNotWildcard 静态方法;
  isNotWildCard:function(value,expression) {
    var match = value.match(expression);
    return (match == null || match.length == 0) ? true : false;
  },
  // convertDaysToInteger 静态方法;
  convertDaysToInteger:function(value) {
    var v = value;
    v = v.replace(/SUN/gi, "1");
    v = v.replace(/MON/gi, "2");
    v = v.replace(/TUE/gi, "3");
    v = v.replace(/WED/gi, "4");
    v = v.replace(/THU/gi, "5");
    v = v.replace(/FRI/gi, "6");
    v = v.replace(/SAT/gi, "7");
    return v;
  },
  // convertMonthsToInteger 静态方法;
  convertMonthsToInteger:function(value) {
    var v = value;
    v = v.replace(/JAN/gi, "1");
    v = v.replace(/FEB/gi, "2");
    v = v.replace(/MAR/gi, "3");
    v = v.replace(/APR/gi, "4");
    v = v.replace(/MAY/gi, "5");
    v = v.replace(/JUN/gi, "6");
    v = v.replace(/JUL/gi, "7");
    v = v.replace(/AUG/gi, "8");
    v = v.replace(/SEP/gi, "9");
    v = v.replace(/OCT/gi, "10");
    v = v.replace(/NOV/gi, "11");
    v = v.replace(/DEC/gi, "12");
    return v;
  },
  // segmentValidator 静态方法;
  segmentValidator:function(expression, value, range, segmentName) {
    var v = value;
    var numbers = new Array();

    // first, check for any improper segments
    var reg = new RegExp(expression, "gi");
    if (!reg.test(v)) {
      return false;
    }

    // check duplicate types
    // check only one L
    var dupMatch = value.match(/L/gi);
    if (dupMatch != null && dupMatch.length > 1) {
      return false;
    }

    // look through the segments
    // break up segments on ','
    // check for special cases L,W,C,/,#,-
    var split = v.split(",");
    var i = -1;
    var l = split.length;
    var match;

    while (++i < l) {
      // set vars
      var checkSegment = split[i];
      var n;
      var pattern = /(\w*)/;
      match = pattern.exec(checkSegment);

      // if just number
      pattern = /(\w*)\-?\d+(\w*)/;
      match = pattern.exec(checkSegment);

      if (match
        && match[0] == checkSegment
        && checkSegment.indexOf("L") == -1
        && checkSegment.indexOf("l") == -1
        && checkSegment.indexOf("C") == -1
        && checkSegment.indexOf("c") == -1
        && checkSegment.indexOf("W") == -1
        && checkSegment.indexOf("w") == -1
        && checkSegment.indexOf("/") == -1
        && (checkSegment.indexOf("-") == -1 || checkSegment
          .indexOf("-") == 0) && checkSegment.indexOf("#") == -1) {
        n = match[0];

        if (n && !(isNaN(n)))
          numbers.push(n);
        else if (match[0] == "0")
          numbers.push(n);
        continue;
      }
      // includes L, C, or w
      pattern = /(\w*)L|C|W(\w*)/i;
      match = pattern.exec(checkSegment);

      if (match
        && match[0] != ""
        && (checkSegment.indexOf("L") > -1
          || checkSegment.indexOf("l") > -1
          || checkSegment.indexOf("C") > -1
          || checkSegment.indexOf("c") > -1
          || checkSegment.indexOf("W") > -1 || checkSegment
            .indexOf("w") > -1)) {

        // check just l or L
        if (checkSegment == "L" || checkSegment == "l")
          continue;
        pattern = /(\w*)\d+(l|c|w)?(\w*)/i;
        match = pattern.exec(checkSegment);

        // if something before or after
        if (!match || match[0] != checkSegment) {
          continue;
        }

        // get the number
        var numCheck = match[0];
        numCheck = numCheck.replace(/(l|c|w)/ig, "");

        n = Number(numCheck);

        if (n && !(isNaN(n)))
          numbers.push(n);
        else if (match[0] == "0")
          numbers.push(n);
        continue;
      }

      var numberSplit;

      // includes /
      if (checkSegment.indexOf("/") > -1) {
        // take first #
        numberSplit = checkSegment.split("/");

        if (numberSplit.length != 2) {
          continue;
        } else {
          n = numberSplit[0];

          if (n && !(isNaN(n)))
            numbers.push(n);
          else if (numberSplit[0] == "0")
            numbers.push(n);
          continue;
        }
      }

      // includes #
      if (checkSegment.indexOf("#") > -1) {
        // take first #
        numberSplit = checkSegment.split("#");

        if (numberSplit.length != 2) {
          continue;
        } else {
          n = numberSplit[0];

          if (n && !(isNaN(n)))
            numbers.push(n);
          else if (numberSplit[0] == "0")
            numbers.push(n);
          continue;
        }
      }

      // includes -
      if (checkSegment.indexOf("-") > 0) {
        // take both #
        numberSplit = checkSegment.split("-");

        if (numberSplit.length != 2) {
          continue;
        } else if (Number(numberSplit[0]) > Number(numberSplit[1])) {
          continue;
        } else {
          n = numberSplit[0];

          if (n && !(isNaN(n)))
            numbers.push(n);
          else if (numberSplit[0] == "0")
            numbers.push(n);
          n = numberSplit[1];

          if (n && !(isNaN(n)))
            numbers.push(n);
          else if (numberSplit[1] == "0")
            numbers.push(n);
          continue;
        }
      }

    }
    // lastly, check that all the found numbers are in range
    i = -1;
    l = numbers.length;

    if (l == 0)
      return false;

    while (++i < l) {
      // alert(numbers[i]);
      if (numbers[i] < range[0] || numbers[i] > range[1]) {
        return false;
      }
    }
    return true;
  }
}
