import messagesApi from "../../api/Messages";
import messageModule from "./messageModule";

export default {
    state:{
        ...frontendData,
    },
    mutations: {
        addMessagePageMutation: (state, messages) => {
            const targetMessages = messageModule.state.messages
                .concat(messages)
                .reduce((res, val) => {
                    res[val.id] = val
                    return res
                }, {})

            messageModule.state.messages = Object.values(targetMessages)
        },
        updateTotalPagesMutation: (state, totalPages) => {
            state.totalPages = totalPages
        },
        updateCurrentPageMutation: (state, currentPage) => {
            state.currentPage = currentPage
        }
    },
    actions: {
        async loadPageAction({commit, state}) {
            const response = await messagesApi.page(state.currentPage + 1)
            const data = await response.json()

            commit('addMessagePageMutation', data.messages)
            commit('updateTotalPagesMutation', data.totalPages)
            commit('updateCurrentPageMutation', data.currentPage, data.totalPages - 1)
        }
    }
}