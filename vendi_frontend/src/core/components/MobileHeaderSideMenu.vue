<template>
  <v-navigation-drawer :model-value="showMenu" width="300" @update:model-value="updateShowMenu">
    <div class="d-flex align-center justify-space-between">
      <img src="@/assets/logo.png" alt="Logo" height="100" />
      <v-btn elevation="0" icon @click="updateShowMenu(false)">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </div>
    <v-divider></v-divider>
    <v-list nav>
      <v-list-item
        v-for="(item, i) in mobileMenuOptions"
        :key="i"
        :title="item.title"
        :color="item.color"
        :prepend-icon="typeof item.icon === 'function' ? item.icon() : item.icon"
        :to="item.to">
      </v-list-item>
    </v-list>

    <v-divider />
    <template v-slot:append>
      <v-list-item base-color="red" title="Logout" prepend-icon="mdi-logout"></v-list-item>
    </template>
  </v-navigation-drawer>
</template>

<script setup>
  defineProps({
    showMenu: {
      type: Boolean,
      default: false,
    },
    mobileMenuOptions: {
      type: Array,
      default: () => [],
    },
  })

  const emit = defineEmits(['update:showMenu'])

  const updateShowMenu = (value) => {
    emit('update:showMenu', value)
  }
</script>

<style scoped></style>
