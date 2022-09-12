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
        <messages-list :messages="messages"/>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import MessagesList from "../components/messages/MessagesList.vue";
import {addHandler} from "../utils/ws";

export default {
  components: {MessagesList},
  comments: {
    MessagesList
  },
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  created() {
    addHandler(data => {
      if (data.objectType === 'MESSAGE') {
        const index = this.messages.findIndex(item => item.id === data.body.id)
        switch (data.eventType) {
          case 'CREATE':
          case 'UPDATE':
            if (index > -1) {
              this.messages.splice(index, 1, data.body)
            } else {
              this.messages.push(data.body)
            }
            break
          case 'REMOVE':
            this.messages.splice(index, 1)
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