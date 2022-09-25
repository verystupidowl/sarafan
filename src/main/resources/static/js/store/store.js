import Vue from 'vue'
import Vuex from 'vuex'
import messageModule from "./modules/messageModule";
import commentModule from "./modules/commentModule";
import pageModule from "./modules/pageModule";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        messageModule,
        commentModule,
        pageModule
    },
    state: {
        profile: profile,
    },
})