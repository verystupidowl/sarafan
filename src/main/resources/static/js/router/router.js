import Vue from "vue";
import VueRouter from 'vue-router';
import MessagesList from "../pages/MessagesList.vue";
import Auth from "../pages/Auth.vue";
import Profile from "../pages/Profile.vue";
import Subscriptions from "../pages/Subscriptions.vue";
import NotFound from "../pages/PageNotFound.vue";
import AllUsers from "../pages/AllUsers.vue";
import Settings from "../pages/Settings.vue";

Vue.use(VueRouter);

const routes = [
    {path: '/', component: MessagesList},
    {path: '/auth', component: Auth},
    {path: '/user/:id?', component: Profile},
    {path: '/subscriptions/:id', component: Subscriptions},
    {path: '/users', component: AllUsers},
    {path: '/settings', component: Settings},
    {path: '*', component: NotFound},
];

export default new VueRouter({
    mode: 'history',
    routes
});