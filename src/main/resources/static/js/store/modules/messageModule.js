import messagesApi from "../../api/Messages";

export default {
    state: {
        messages,
        error: {}
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
        errorGetter: state => {
            return state.error;
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
            state.error = err;
        }
    },
    actions: {
        addMessageAction({commit, state}, message) {
            messagesApi.add(message).then(result => {
                result.json().then(data => {
                    const index = state.messages.findIndex(item => item.id === data.id);
                    if (index > -1) {
                        commit('updateMessageMutation', data);
                    } else {
                        commit('addMessageMutation', data);
                    }
                });
            }, err => {
                err.json().then(errorData => {
                    commit('errorMessageMutation', {
                        status: err.status,
                        message: errorData.message,
                        timestamp: errorData.timestamp
                    });
                })
            });
            setTimeout(() => state.error = null, 3000);
        },
        async updateMessageAction({commit}, message) {
            const result = await messagesApi.update(message);
            if (result.ok) {
                const data = await result.json();
                commit('updateMessageMutation', data);
            }
        },
        async removeMessageAction({commit}, message) {
            const result = await messagesApi.remove(message.id);

            if (result.ok) {
                commit('removeMessageMutation', message);
            }
        },
    },
}