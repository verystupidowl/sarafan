import Vue from 'vue'
import App from 'pages/App.vue'
import store from "store/store.js";
import router from "./router/router";
import 'api/resource'
import {connect} from "./utils/ws";
import Vuetify from "vuetify";
import '@babel/polyfill'
import 'vuetify/dist/vuetify.min.css'

if (profile) {
    connect();
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
})