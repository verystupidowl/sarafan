import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";

let stompClient = null;
const handlers = [];

export function connect() {
    const socket = new SockJS('/sarafan-ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {
    }
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/messages-comments/activity', message => {
            handlers.forEach(handler => handler(JSON.parse(message.body)));
        });
    });
}

export function addHandler(handler) {
    handlers.push(handler);
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

export function sendMessage(message) {
    stompClient.send("/app/changeMessage", {}, JSON.stringify(message));
}