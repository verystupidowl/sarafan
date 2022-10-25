import commentApi from "../../api/comment";
import messageModule from "./messageModule";

export default {

    state: {
        commentError: null,
    },
    getters: {
        commentErrorGetter: state => {
            return state.commentError;
        }
    },
    mutations: {
        addCommentMutation: (state, comment) => {
            const updateIndex = messageModule.state.messages.findIndex(item => item.id === comment.messageId);
            const message = messageModule.state.messages[updateIndex];

            if (!message.comments) {
                message.comments = [];
            }

            if (!message.comments.find(it => it.id === comment.id)) {
                messageModule.state.messages = [
                    ...messageModule.state.messages.slice(0, updateIndex),
                    {
                        ...message,
                        comments: [
                            ...message.comments,
                            comment
                        ]
                    },
                    ...messageModule.state.messages.slice(updateIndex + 1)
                ];
            }
        },
        errorCommentMutation: (state, err) => {
            state.commentError = err;
            setTimeout(() => state.commentError = null, 3000);
        },
    },
    actions: {
        async addCommentAction({commit, state}, comment) {
            try {
                const response = await commentApi.add(comment);
                const data = await response.json();

                commit('addCommentMutation', data);
            } catch (err) {
                const errorData = await err.json();

                commit('errorCommentMutation', {
                    status: err.status,
                    message: errorData.message,
                    timestamp: errorData.timestamp
                });
            }
        },
    }
}