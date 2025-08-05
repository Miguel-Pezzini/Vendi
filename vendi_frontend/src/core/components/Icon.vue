<template>
  <v-tooltip v-if="tooltip" location="top">
    <template #activator="{ props }">
      <v-icon :color="color" v-bind="props" :size="size" :icon="icon" />
    </template>
    <span>{{ tooltip }}</span>
  </v-tooltip>

  <v-icon v-else :color="color" :size="size" :icon="icon" />
</template>

<script setup>
  defineProps({
    size: { type: String, default: null },
    icon: { type: String, required: true },
    tooltip: { type: String, default: null },
    color: { type: String, default: null },
  })
</script>
