import Vue from "vue";
import VueResource from 'vue-resource';

Vue.use(VueResource);

const notifications = Vue.resource('/notifications/change-notifications{/id}');

export default {
    changeNotifications: user => notifications.update({id: user.id}, user),
}