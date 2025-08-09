<template>
  <v-navigation-drawer :model-value="showMenu" width="300" @update:model-value="updateShowMenu">
    <div class="d-flex align-center justify-space-between">
      <RouterLink to="/home">
        <img class="img" height="100" src="@/assets/logo.png" alt="Logo" />
      </RouterLink>
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
        :prepend-icon="item.prependIcon"
        :append-icon="item.appendIcon"
        :to="item.to">
      </v-list-item>
      <v-list-group value="Categories">
        <template v-slot:activator="{ props }">
          <v-list-item
            v-bind="props"
            prepend-icon="mdi-view-grid-outline"
            title="Categories"></v-list-item>
        </template>
        <v-list-item
          v-for="(categoryItem, i) in categories"
          :key="i"
          :value="categoryItem.category"
          :to="categoryItem.path"
          :title="categoryItem.name"></v-list-item>
      </v-list-group>
    </v-list>

    <v-divider />
    <template v-slot:append>
      <v-list-item base-color="red" title="Logout" prepend-icon="mdi-logout"></v-list-item>
    </template>
  </v-navigation-drawer>
</template>

<script setup>
  import headerCategories from '../constants/headerCategories'
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

  const categories = headerCategories()

  const emit = defineEmits(['update:showMenu'])

  const updateShowMenu = (value) => {
    emit('update:showMenu', value)
  }
</script>

<style scoped>
  .img:hover {
    cursor: pointer;
    transition: 0.2s;
    scale: 1.02;
  }
</style>
