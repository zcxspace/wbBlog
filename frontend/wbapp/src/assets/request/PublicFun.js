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
        let odd = [];
        let even = [];
        for (let item of textArr) {
            //去除所有空字符串 包含空格
            if (!Number(item)) {
                textArr.splice(textArr.indexOf(item), 1);
            }
        }
        console.log(textArr);
        let length = topicArr.length + textArr.length;

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
            i % 2 != 0 ? total.push(odd.shift()) : total.push(even.shift())
        }
        return total;
    }
    else {
        let noTopic = [str];
        return noTopic;
    }


}
export { getCreateTime, splitStr }