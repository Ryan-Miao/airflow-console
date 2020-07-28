export function GUID() {
  this.date = new Date()

  /* 判断是否初始化过，如果初始化过以下代码，则以下代码将不再执行，实际中只执行一次 */
  if (typeof this.newGUID !== 'function') {
    /* 生成GUID码 */
    GUID.prototype.newGUID = function() {
      this.date = new Date()
      var guidStr = ''
      const sexadecimalDate = this.hexadecimal(this.getGUIDDate(), 16)
      const sexadecimalTime = this.hexadecimal(this.getGUIDTime(), 16)
      for (var i = 0; i < 9; i++) {
        guidStr += Math.floor(Math.random() * 16).toString(16)
      }
      guidStr += sexadecimalDate
      guidStr += sexadecimalTime
      while (guidStr.length < 32) {
        guidStr += Math.floor(Math.random() * 16).toString(16)
      }
      return this.formatGUID(guidStr)
    }

    /*
        * 功能：获取当前日期的GUID格式，即8位数的日期：19700101
        */
    GUID.prototype.getGUIDDate = function() {
      return this.date.getFullYear() + this.addZero(this.date.getMonth() + 1) +
            this.addZero(this.date.getDay())
    }

    /*
        * 功能：获取当前时间的GUID格式，即8位数的时间，包括毫秒，毫秒为2位数：12300933
        */
    GUID.prototype.getGUIDTime = function() {
      return this.addZero(this.date.getHours()) + this.addZero(this.date.getMinutes()) +
            this.addZero(this.date.getSeconds()) + this.addZero(parseInt(this.date.getMilliseconds() / 10))
    }

    /*
        * 功能: 为一位数的正整数前面添加0，如果是可以转成非NaN数字的字符串也可以实现
        */
    GUID.prototype.addZero = function(num) {
      if (Number(num).toString() !== 'NaN' && num >= 0 && num < 10) {
        return '0' + Math.floor(num)
      } else {
        return num.toString()
      }
    }

    /*
        * 功能：将y进制的数值，转换为x进制的数值
        */
    GUID.prototype.hexadecimal = function(num, x, y) {
      if (y !== undefined) {
        return parseInt(num.toString(), y).toString(x)
      } else {
        return parseInt(num.toString()).toString(x)
      }
    }

    /*
        * 功能：格式化32位的字符串为GUID模式的字符串
        */
    GUID.prototype.formatGUID = function(guidStr) {
      // eslint-disable-next-line one-var
      var str1 = guidStr.slice(0, 8) + '-',
        str2 = guidStr.slice(8, 12) + '-',
        str3 = guidStr.slice(12, 16) + '-',
        str4 = guidStr.slice(16, 20) + '-',
        str5 = guidStr.slice(20)
      return str1 + str2 + str3 + str4 + str5
    }
  }
}
// 使用方法
// var guid = new GUID()
// console.log(guid.newGUID())
// return guid

// alert(guid.newGUID())
