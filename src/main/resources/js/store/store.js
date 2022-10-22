import Vue from 'vue';
import Vuex from 'vuex';
import messageModule from "./modules/messageModule";
import commentModule from "./modules/commentModule";
import pageModule from "./modules/pageModule";
import notificationModule from "./modules/notificationModule";

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        messageModule,
        commentModule,
        pageModule,
        notificationModule
    },
    state: {
        profile: profile,
    },
});