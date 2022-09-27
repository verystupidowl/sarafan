import commentApi from "../../api/comment";
import messageModule from "./messageModule";

export default {
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
    },
    actions: {
        async addCommentAction({commit, state}, comment) {
            const response = await commentApi.add(comment);
            const data = await response.json();
            commit('addCommentMutation', data);
        },
    }
}