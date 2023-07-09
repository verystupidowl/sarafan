import Vue from 'vue';
import App from 'pages/App.vue';
import store from "store/store.js";
import router from "./router/router";
import 'api/resource';
import {connect} from "./utils/ws";
import Vuetify from "vuetify";
import '@babel/polyfill';
import 'vuetify/dist/vuetify.min.css';

import * as Sentry from "@sentry/vue";
import {BrowserTracing} from "@sentry/tracing";

Sentry.init({
    Vue,
    dsn: "https://9f05c812c6e547c7819c010b56a26612@o1417785.ingest.sentry.io/6760541",
    integrations: [
        new BrowserTracing({
            routingInstrumentation: Sentry.vueRouterInstrumentation(router),
            tracingOrigins: ["localhost", "my-site-url.com", /^\//],
        }),
    ],
    tracesSampleRate: 1.0,
});

Sentry.configureScope(scope => {
    scope.setUser({
        id: profile && profile.id,
        username: profile && profile.name
    });
});

if (profile) {
    connect(profile);
}

Vue.use(Vuetify);

new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
});