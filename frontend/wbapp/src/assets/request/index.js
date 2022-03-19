import axios from 'axios'
axios.defaults.withCredentials = true;
//接收验证码请求
async function getCaptcha() {
    return await axios.post('http://120.25.125.57:8080/xhywblog/users/captcha/?time=' + new Date().getTime())
        .then((res) => {
            return res.request.responseURL;
        }).catch(e => { console.log(e) })
}

// 发送注册请求
async function SignUp(email, password, gender, captcha) {

    axios.post(' http://120.25.125.57:8080/xhywblog/users/register', {
        email: email,
        password: password,
        gender: Number(gender),
        captcha: captcha,
        xhrFields: { withCredentials: true },
    }).then((res) => {
        console.log(res.data.message)
        console.log(res);
    }).catch(e => { console.log(e) })
}


// 发送登录请求
async function SignIn(email, password, captcha) {
    let obj = await axios.post(' http://120.25.125.57:8080/xhywblog/users/login', {
        email: email,
        password: password,
        captcha: captcha,
    })
    return obj;
}


// 获取动态热榜
async function GetHotWord(num) {
    await axios.post('http://120.25.125.57:8080/xhywblog/dynamic/getHotDynamic', {
        num = num,
    }).then((res) => {
        console.log(res)
    }).catch((e) => {
        console.log(e)
    })
}

export { getCaptcha, SignUp, SignIn, GetHotWord }

