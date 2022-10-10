import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";

let stompClient = null;
const handlers = [];

export function connect(profile) {
    const socket = new SockJS('/sarafan-ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {
    }
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame);
        if (profile.notificationTypes.findIndex(type => type === 'SUBSCRIBE') !== -1) {
            stompClient.subscribe('/subscribe-notification/activity', message => {
                handlers.forEach(handler => handler(JSON.parse(message.body)));
            });
        }
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