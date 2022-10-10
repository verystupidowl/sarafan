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
                ...state.messages,
                message
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
        addMessageAction: ({commit, state}, message) => {
            messagesApi.add(message, store.state.profile)
                .then(result => {
                    result.json()
                        .then(data => {
                            const index = state.messages.findIndex(item => item.id === data.id);
                            if (index > -1) {
                                commit('updateMessageMutation', data);
                            } else {
                                commit('addMessageMutation', data);
                            }
                        });
                }, err => {
                    err.json()
                        .then(errorData => {
                            commit('errorMessageMutation', {
                                status: err.status,
                                message: errorData.message,
                                timestamp: errorData.timestamp
                            });
                        });
                });
        },
        updateMessageAction: ({commit}, message) => {
            messagesApi.update(message)
                .then(result =>
                        result.json()
                            .then(data =>
                                commit('updateMessageMutation', data)
                            ),
                    err => {
                        err.json()
                            .then(errorData => {
                                commit('errorMessageMutation', {
                                    status: err.status,
                                    message: errorData.message,
                                    timestamp: errorData.timestamp
                                });
                            });
                    });
        },
        async removeMessageAction({commit}, message) {
            const result = await messagesApi.remove(message.id);

            if (result.ok) {
                commit('removeMessageMutation', message);
            }
        },
    },
}