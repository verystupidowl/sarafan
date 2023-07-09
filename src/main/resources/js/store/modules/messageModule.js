import messagesApi from "../../api/Messages";
import store from "../store";

export default {
    state: {
        messages,
        messageError: null
    },
    getters: {
        sortedMessages: state => selected => {
            switch (selected) {
                case 'Newest':
                    return (state.messages || []).sort((a, b) => -(a.id - b.id));
                case 'Oldest':
                    return (state.messages || []).sort((a, b) => a.id - b.id);
                case 'By name' :
                    return (state.messages || []).sort((a, b) => a.author.name.localeCompare(b.author.name) || -(a.id - b.id));
                case 'By population' :
                    return (state.messages || []).sort((a, b) => -(a.comments.length - b.comments.length) || -(a.id - b.id));
            }
        },
        messageErrorGetter: state => {
            return state.messageError;
        }
    },
    mutations: {
        addMessageMutation: (state, message) => {
            state.messages = [
                message,
                ...state.messages
            ];
        },
        updateMessageMutation: (state, message) => {
            const updateIndex = state.messages.findIndex(item => item.id === message.id);

            state.messages = [
                ...state.messages.slice(0, updateIndex),
                message,
                ...state.messages.slice(updateIndex + 1)
            ];
        },
        removeMessageMutation: (state, message) => {
            const deletionIndex = state.messages.findIndex(item => item.id === message.id);

            if (deletionIndex > -1) {
                state.messages = [
                    ...state.messages.slice(0, deletionIndex),
                    ...state.messages.slice(deletionIndex + 1)
                ];
            }
        },
        errorMessageMutation: (state, err) => {
            state.messageError = err;
            setTimeout(() => state.messageError = null, 3000);
        }
    },
    actions: {
        async addMessageAction({commit, state}, message) {
            try {
                const result = await messagesApi.add(message, store.state.profile);
                const data = await result.json();
                const index = state.messages.findIndex(item => item.id === data.id);
                if (index > -1) {
                    commit('updateMessageMutation', data);
                } else {
                    commit('addMessageMutation', data);
                }
            } catch (err) {
                const errorData = await err.json();
                commit('errorMessageMutation', {
                    status: err.status,
                    message: errorData.message,
                    timestamp: errorData.timestamp
                });
            }
        },
        async updateMessageAction({commit}, message) {
            try {
                const result = await messagesApi.update(message);
                const data = await result.json();

                commit('updateMessageMutation', data);
            } catch (err) {
                const errorData = await err.json();

                commit('errorMessageMutation', {
                    status: err.status,
                    message: errorData.message,
                    timestamp: errorData.timestamp
                });
            }
        },
        async removeMessageAction({commit}, message) {
            try {
                await messagesApi.remove(message.id);

                commit('removeMessageMutation', message);
            } catch (err) {
                const errorData = await err.json();
                commit('errorMessageMutation', {
                    status: err.status,
                    massage: errorData.status,
                    timestamp: errorData.timestamp
                });
            }
        }
        ,
    },
};