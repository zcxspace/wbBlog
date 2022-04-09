import axios from 'axios'
axios.defaults.withCredentials = true;


let config = {
    headers: { "Content-Type": "multipart/form-data" },
};
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
    });
    return obj;
}
//获取用户信息
async function getUserInfo(path) {
    let result = await axios.post(`${path}`, {})
    return result;
}
//更换头像 
async function changeProfile(form) {
    let result = await axios.post('http://120.25.125.57:8080/xhywblog/users/photoImage',
        form
        , config
    )
    return result;
}
//更换背景
async function changeBack(form) {
    let result = await axios.post('http://120.25.125.57:8080/xhywblog/users/bagImage', form, config)
    return result;
}

//更改用户信息 
async function changeUserInfo(name, address, intro, birthday, phone, job, trait, interests, gender) {
    console.log("更新了")
    let result = axios.post(' http://120.25.125.57:8080/xhywblog/users/update', {
        name: name,
        address: address,
        intro: intro,
        birthday: birthday,
        phone: phone,
        job: job,
        interests: interests,
        trait: trait,
        gender: gender
    })
    return result;
}
// 获取动态热榜
async function GetHotWord(num) {
    await axios.post('http://120.25.125.57:8080/xhywblog/dynamic/getHotDynamic', {
        num: num,
    }).then((res) => {
        console.log(res)
    }).catch((e) => {
        console.log(e)
    })
}
// 获取公共动态 
async function GetPublic() {
    let result = await axios.post(' http://120.25.125.57:8080/xhywblog/dynamic/getNewDynamic');
    return result;
}
//发布动态

async function SentBlog(form) {

    let result = await axios.post('http://120.25.125.57:8080/xhywblog/dynamic/publish'
        , form, config);
    return result;
}
//编辑动态
async function editDynamic(form) {
    let config = {
        headers: { "Content-Type": "multipart/form-data" },
    };
    let result = await axios.post(' http://120.25.125.57:8080/xhywblog/dynamic/publish', form, config)
    return result;
}
//删除动态 
async function DeleteDynamic(id) {
    let result = await axios.post('http://120.25.125.57:8080/xhywblog/dynamic/remove', {
        id: id,
    })
    return result;
}

//点赞
async function isLike(id, action) {
    if (action == '点赞') {
        let result = await axios.post(' http://120.25.125.57:8080/xhywblog/dynamic/setLike', {
            id: id
        })
        return result;
    } else {
        let result = await axios.post('http://120.25.125.57:8080/xhywblog/dynamic/cancelLike', {
            id: id
        })
        return result;
    }


}
//发送评论接口
async function postComment(text, replyId, dynamicId, userId, floorId) {
    let result = await axios.post(' http://120.25.125.57:8080/xhywblog/comment/pushComment', {
        text: text,
        replyId: replyId,
        dynamicId: dynamicId,
        userId: userId,
        floorId: floorId,
    })
    return result;
}
//获取评论
async function getComment(dynamicId, floorId) {
    let result = await axios.post('http://120.25.125.57:8080/xhywblog/comment/list', {
        dynamicId: dynamicId,
        floorId: floorId,
    })
    return result;
}
//删除评论
async function delComment(commentId) {
    let result = await axios.get(' http://120.25.125.57:8080/xhywblog/comment/removeComment', {
        params: {
            commentId: commentId,
        }
    })
    return result;
}

//关注用户
async function follow(userId, otherId) {
    let result = await axios.post('http://120.25.125.57:8080/xhywblog/fans/subscription',
        {
            userId: userId,
            otherId: otherId,
        });
    return result;
}
//取消关注
async function unFollow(userId, otherId) {
    let result = axios.post('http://120.25.125.57:8080/xhywblog/fans/cancelSubscription', {

        userId: userId,
        otherId: otherId

    })
    return result;
}
//获取粉丝
async function getFans(userId, lookId) {
    let result = axios.post('http://120.25.125.57:8080/xhywblog/fans/getFans', {
        userId: userId,
        lookId: lookId
    });
    return result;
}
//获取关注者
async function getFollower(userId, lookId) {
    let result = axios.post('http://120.25.125.57:8080/xhywblog/fans/getBeSubscript', {
        userId: userId,
        lookId: lookId,
    });
    return result;
}
//获取话题动态
async function getTopics(topic) {
    let result = axios.get('http://120.25.125.57:8080/xhywblog/dynamic/queryTopic', {
        params: {
            topic: topic,
        }
    })
    return result;
}
//获取热榜信息
async function getHotWord() {
    let result = axios.post(' http://120.25.125.57:8080/xhywblog/dynamic/getHotDynamic');
    return result;
}
export { getHotWord, getTopics, changeBack, getFollower, getFans, unFollow, follow, delComment, getComment, postComment, GetPublic, getCaptcha, getUserInfo, SignUp, SignIn, GetHotWord, SentBlog, changeProfile, changeUserInfo, DeleteDynamic, editDynamic, isLike }

