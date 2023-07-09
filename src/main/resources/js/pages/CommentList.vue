<template>
  <v-list two-line>
    <v-subheader>
      Comments
    </v-subheader>
    <template v-for="(item, index) in comments">
      <v-divider v-if="index > 0" :key="item.id">
      </v-divider>
      <comment-item :comment="item" :key="'item' + index"/>
    </template>

    <comment-form :message-id="messageId"/>
  </v-list>
</template>

<script>
import CommentItem from "../components/comment/CommentItem.vue";
import CommentForm from "../components/comment/CommentForm.vue";

export default {
  name: "CommentList",
  components: {CommentForm, CommentItem},
  props: ['comments', 'messageId'],
};
</script>

<style scoped>

</style>