import Vue from 'vue'
import App from 'pages/App.vue'
import 'api/resource'
import {connect} from "./utils/ws";
import Vuetify from "vuetify";
import 'vuetify/dist/vuetify.min.css'

if (frontendData.profile) {
    connect();
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    render: a => a(App)
})