import store from "../store";
import notificationApi from '../../api/notifications';

export default {
    state: {
        notifications: [],
    },

    getters: {
        notificationsGetter: state => {
            return state.notifications.sort((a, b) => b.creationDate - a.creationDate);
        }
    },

    mutations: {
        addNotificationMutation: (state, notification) => {
            const not = state.notifications.filter(n =>
                n.recipientId === notification.recipientId && n.senderId === notification.senderId && n.notificationType === notification.notificationType
            );
            if (not.length === 0)
                state.notifications.push(notification);
        },
        removeNotificationMutation: (state, notification) => {
            const index = state.notifications.findIndex(n => n.creationDate === notification.creationDate);
            state.notifications.splice(index, 1);
        },
        changeNotificationMutation: (state, user) => {
            store.state.profile = user;
        }
    },
    actions: {
        async changeNotificationAction({state, commit}, notifications) {
            const user = {
                id: store.state.profile.id,
                notificationTypes: notifications
            }
            const response = await notificationApi.changeNotifications(user);
            const data = await response.json();
            commit('changeNotificationMutation', data);
        }
    }
}