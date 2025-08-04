<template>
  <v-tooltip v-if="tooltip" location="top">
    <template #activator="{ props: tooltipProps }">
      <v-icon :color="color" v-bind="tooltipProps" :size="size" :icon="icon" />
    </template>
    <span>{{ tooltip }}</span>
  </v-tooltip>

  <v-icon v-else :color="color" :size="size" :icon="icon" />
</template>

<script setup>
  const props = defineProps({
    size: { type: String, default: null },
    icon: { type: String, required: true },
    tooltip: { type: String, default: null },
    color: { type: String, default: null },
  })
</script>
