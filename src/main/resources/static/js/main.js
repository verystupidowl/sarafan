
Vue.component('messages-list', {
    props: ['messages'],
    template: '<div>List</div>'
})

let app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages: [
            { id: '123', text: 'Wow!' },
            { id: '23', text: 'More!' },
            { id: '3', text: 'Another!' },
        ]
    }
});