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
                n.channelId === notification.channelId && n.authorId === notification.authorId && n.notificationType === notification.notificationType
            );
            if (not.length === 0)
                state.notifications.push(notification);
        },
        removeNotificationMutation: (state, notification) => {
            const index = state.notifications.findIndex(n => n.creationDate === notification.creationDate);
            state.notifications.splice(index, 1);
        }
    },
}