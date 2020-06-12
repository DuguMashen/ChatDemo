//常量池
const ip = "";
let nickname = "";
let acc = "";
let currentChatObj = "";
let socketUrl = "";


//消息类型
const msgType = "client";


//构建消息对象
function WsMessage(type, fromUserId, toUserId, time, msg) {

    let obj = new Object();
    let message = new Object();
    message.content = msg;
    obj.type = type;
    obj.fromUserId = fromUserId;
    obj.toUserId = toUserId;
    obj.time = time;
    obj.map = message;
    return obj; //返回对象
}

//获取当前时间
function getCurrentTime() {
    const date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDay();
    let hour = date.getHours();
    let minute = date.getMinutes();
    let second = date.getSeconds();
    let time = year + "-" + month + "-" + day + "-" + "&nbsp;" + hour + ":" + minute + ":" + second;
    return time;
}

//处理消息
function dealMessage(wsmessage) {
    let message = JSON.parse(wsmessage);
    if (!(wsmessage instanceof WsMessage)) {
        console.log("消息类型有误");
        return;
    }
    let type = message.type;
    if (type == msgType) {

    }
}

//建立连接
function openSocket() {
    if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    } else {
        console.log("您的浏览器支持WebSocket");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        //等同于socket = new WebSocket("ws://localhost:8888/xxxx/im/25");
        //var socketUrl="${request.contextPath}/im/"+$("#userId").val();
        var socketUrl = "http://192.168.43.48:8080/imserver/" + $("#userId").val();
        socketUrl = socketUrl.replace("https", "ws").replace("http", "ws");
        console.log(socketUrl);
        if (socket != null) {
            socket.close();
            socket = null;
        }
        socket = new WebSocket(socketUrl);
        //打开事件
        socket.onopen = function () {
            console.log("websocket建立连接成功");
        };
        //获得消息事件
        socket.onmessage = function (event) {
            let data = event.data;
            dealMessage(data);
        };
        //关闭事件
        socket.onclose = function () {
            console.log("websocket已关闭");
        };
        0
        //发生了错误事件
        socket.onerror = function () {
            console.log("websocket发生了错误");
        }
    }
    //拼接消息
    function appendMessage(chatMessage) {
        if (!(chatMessage instanceof WsMessage)) {
            console.log("appendMessage,消息类型有误")
            return;
        }
        let type=chatMessage.type;
        if(type!="client"){
             console.log("appendMessage，消息类型有误2")
            return;
        }
        const fromUserId=chatMessage.fromUserId;
        const toUserId=chatMessage.toUserId;
        let leftMsg="";
        if(fromUserId==acc){

        }
    }
}

