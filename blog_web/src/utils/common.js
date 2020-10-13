import {Message} from 'element-ui';

export default {
  // 复制属性
  copyProperty(newP, oldP) {
    for (let key in newP) {
      newP[key] = oldP[key];
    }
  },
  // 日期格式函数 20100101 ->> 2010-01-01
  dateFormat(row, column, cellValue, index) {
    if (!!cellValue) {
      if (!isNaN(cellValue)) {
        cellValue = cellValue + '';
        cellValue = cellValue.substring(0, 4) + "-" + cellValue.substring(4, 6) + "-" + cellValue.substring(6, 8);
      }
    }
    return cellValue;
  },
  // 全局消息提示方法
  kMessage(msg, type) {
    Message.closeAll();
    Message({
      message: msg,
      center: true,
      type: type,
      duration: 1000
    });
  }
}


