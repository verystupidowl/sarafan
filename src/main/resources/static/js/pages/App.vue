<template>
  <v-app>
    <v-toolbar app>
      <v-toolbar-title @click="showRecommendations">Sarafan</v-toolbar-title>
      <v-btn v-if="profile" flat :disabled="$route.path === '/'" @click="showMessages">
        Messages
      </v-btn>
      <v-spacer></v-spacer>
      <div>
        <v-btn flat v-if="profile" :disabled="$route.path === '/user' || $route.path === `/user/${profile.id}`"
               @click="showProfile">
          {{ profile.name }}
        </v-btn>
      </div>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <router-view></router-view>
    </v-content>
    <notification v-if="notification" :notification="notification" :alert="alert"/>
  </v-app>
</template>

<script>
import {mapMutations, mapState} from 'vuex';
import {addHandler} from "../utils/ws";
import Notification from "../components/Notification.vue";

export default {
  computed: mapState(['profile']),
  components: {
    Notification
  },
  methods: {
    ...mapMutations(['addCommentMutation',
      'addMessageMutation',
      'updateMessageMutation',
      'removeMessageMutation']),
    showMessages() {
      this.$router.push('/');
    },
    showProfile() {
      this.$router.push('/user');
    },
    showRecommendations() {
      this.$router.push('/users');
    }
  },
  data() {
    return {
      alert: false,
      notification: null
    }
  },
  created() {
    addHandler(data => {
      if (data.objectType === 'MESSAGE') {
        switch (data.wsEventType) {
          case 'CREATE':
            this.addMessageMutation(data.body);
            break;
          case 'UPDATE':
            this.updateMessageMutation(data.body);
            break;
          case 'REMOVE':
            this.removeMessageMutation(data.body);
            break;
          default:
            console.error(`Looks like the event type if unknown "${data.eventType}"`);
        }
      } else if (data.objectType === 'COMMENT') {
        switch (data.wsEventType) {
          case 'CREATE':
            this.addCommentMutation(data.body);
            break;
          default:
            console.error(`Looks like the event type if unknown "${data.wsEventType}"`);
        }
      } else if (data.objectType === 'NOTIFICATION') {
        switch (data.wsEventType) {
          case 'CREATE':
            if (data.body.channelId === this.$store.state.profile.id) {
              this.alert = true;
              this.notification = data.body;
              setTimeout(() => {
                this.alert = false;
                this.notification = null;
              }, 10000);
            }
            break;
          default:
            console.error(`Looks like the event type if unknown "${data.wsEventType}"`)
        }
      } else {
        console.error(`Looks like the object type if unknown "${data.objectType}"`);
      }
    })
  },
  beforeMount() {
    if (!this.profile) {
      this.$router.replace('/auth');
    }
  }
}
</script>

<style>

</style>