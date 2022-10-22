import Vue from "vue";
import VueResource from 'vue-resource';

Vue.use(VueResource);

const profile = Vue.resource('/profile/get-all');

export default {
    get: () => profile.get(),
}