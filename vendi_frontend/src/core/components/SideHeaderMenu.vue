<template>
  <v-navigation-drawer :model-value="showMenu" width="400" @update:model-value="updateShowMenu">
    <div class="d-flex align-center justify-space-between">
      <img src="@/assets/logo.png" alt="Logo" height="100" />
      <v-btn elevation="0" icon @click="updateShowMenu(false)">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </div>
    <v-divider></v-divider>
    <v-list-item link title="List Item 1"></v-list-item>
    <v-list-item link title="List Item 2"></v-list-item>
    <v-list-item link title="List Item 3"></v-list-item>
    <v-divider />
  </v-navigation-drawer>
</template>

<script setup>
  defineProps({
    showMenu: {
      type: Boolean,
      default: false,
    },
  })

  const emit = defineEmits(['update:showMenu'])

  const updateShowMenu = (value) => {
    emit('update:showMenu', value)
  }
</script>

<style scoped></style>
