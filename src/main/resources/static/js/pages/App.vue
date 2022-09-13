<template>
  <v-app>
    <v-toolbar app>
      <v-toolbar-title>Sarafan</v-toolbar-title>
      <v-spacer></v-spacer>
      <div>
        <div v-if="profile">{{ profile.name }}</div>
      </div>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <v-container v-if="!profile">
        Необходимо авторизироваться через <a href="/login">Google</a>
      </v-container>
      <v-container v-else>
        <messages-list/>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import MessagesList from "../components/messages/MessagesList.vue";
import {addHandler} from "../utils/ws";

export default {
  components: {MessagesList},
  comments: {
    MessagesList
  },
  computed: mapState(['profile']),
  methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
  created() {
    addHandler(data => {
      if (data.objectType === 'MESSAGE') {
        switch (data.eventType) {
          case 'CREATE':
            this.addMessageMutation(data.body)
            break
          case 'UPDATE':
            this.updateMessageMutation(data.body)
            break
          case 'REMOVE':
            this.removeMessageMutation(data.body)
            break
          default:
            console.error(`Looks like the event type if unknown "${data.eventType}"`)
        }
      } else {
        console.error(`Looks like the object type if unknown "${data.objectType}"`)
      }
    })
  }
}
</script>

<style>

</style>