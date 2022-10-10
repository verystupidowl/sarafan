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
      <v-btn flat :disabled="$route.path === '/settings'" v-if="profile" icon @click="showSettings">
        <v-icon>settings</v-icon>
      </v-btn>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <router-view/>
    </v-content>
    <notification-list
        :notification-action-text="notificationActionText"
        :notifications="notifications"
        @close-notification="closeNotification"
    />
  </v-app>
</template>

<script>
import {mapGetters, mapMutations, mapState} from 'vuex';
import {addHandler} from "../utils/ws";
import NotificationList from "../components/notification/NotificationList.vue";

export default {
  computed: {
    ...mapState(['profile']),
    ...mapGetters(['notificationsGetter']),
    notifications() {
      return this.notificationsGetter;
    }
  },
  components: {
    NotificationList,
  },
  methods: {
    ...mapMutations(['addCommentMutation',
      'addMessageMutation',
      'updateMessageMutation',
      'removeMessageMutation']),
    ...mapMutations(['addNotificationMutation', 'removeNotificationMutation']),
    showMessages() {
      this.$router.push('/');
    },
    showProfile() {
      this.$router.push('/user');
    },
    showRecommendations() {
      this.$router.push('/users');
    },
    closeNotification(notification) {
      this.removeNotificationMutation(notification);
    },
    showSettings() {
      this.$router.push('/settings');
    }
  },
  data() {
    return {
      notificationActionText: '',
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
            if (data.body.recipientId === this.$store.state.profile.id) {
              const notification = data.body;
              this.addNotificationMutation(notification);
              this.notificationActionText = 'subscribed to you';
              setTimeout(() => this.closeNotification(notification), 10000);
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
    console.log(this.profile)
  }
}
</script>

<style>

</style>