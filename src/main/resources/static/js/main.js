import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import {connect} from "./utils/ws";

if (frontentData.profile) {
    connect();
}

Vue.use(VueResource)

new Vue({
    el: '#app',
    render: a => a(App)
})