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
};

export { getCreateTime };