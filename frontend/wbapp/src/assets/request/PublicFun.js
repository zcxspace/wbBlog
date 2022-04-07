function getCreateTime(timeStamp) {
    let time = new Date(timeStamp);
    let month = time.getMonth() + 1;
    let day = time.getDate();
    let dataStr = month + "-" + day;
    let hour = time.getHours();
    let min = time.getMinutes();
    let hourStr = hour < 10 ? "0" + hour : hour;
    let minStr = min < 10 ? '0' + min : min;
    return dataStr + " " + hourStr + ":" + minStr;
}


function splitStr(str) {
    console.log(str);
    //如果有匹配的
    if (str.match(/#[^#]+#/ig)) {
        let topicArr = str.match(/#[^#]+#/ig);
        console.log(topicArr)
        let textArr = str.split(/#[^#]+#/ig);
        console.log(textArr);
        // console.log(Number())
        // console.log("  ")
        let odd = [];
        let even = [];
        for (let item of textArr) {
            //去除所有空字符串 包含空格
            // 替换文本中全部的空格 然后判断文本长度
            console.log(item.replace(/\s*/g, "").length)
            if (!item.replace(/\s*/g, "").length) {
                textArr.splice(textArr.indexOf(item), 1);
            }
        }
        console.log(textArr);
        let length = topicArr.length + textArr.length;
        // 判断匹配的第一个话题是否位于文本首位
        if (str.indexOf(topicArr[0]) == 0) {
            even = topicArr;
            odd = textArr;
        }
        else {
            even = textArr;
            odd = topicArr;
        }
        let total = [];
        for (let i = 1; i < length + 1; i++) {
            //如果 #aa# 在文本首位  则按奇数顺序插入 反之偶数顺序插入
            i % 2 != 0 ? total.push(even.shift()) : total.push(odd.shift())
        }
        console.log(total)
        return total;
    }
    else {
        let noTopic = [str];
        return noTopic;
    }


}
export { getCreateTime, splitStr }