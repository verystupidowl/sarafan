import Vue from "vue";
import VueResource from 'vue-resource';

Vue.use(VueResource);

const profile = Vue.resource('/profile{/id}');

export default {
    get: id => profile.get({id: id}),
    changeSubscription: channelId => Vue.http.post(`/profile/change-subscription/${channelId}`),
    subscriberList: channelId => Vue.http.get(`/profile/get-subscribers/${channelId}`),
    changeSubscriptionStatus: subscriberId => Vue.http.post(`/profile/change-status/${subscriberId}`),
    subscriptionList: channelId => Vue.http.get(`/profile/get-subscriptions/${channelId}`),
}