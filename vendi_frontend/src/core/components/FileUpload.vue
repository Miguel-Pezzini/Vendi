<template>
  <v-file-upload
    v-model="formattedValue"
    :max-width="maxWidth"
    :density="density"
    :show-size="showSize"
    :multiple="multiple"
    :clearable="clearable"
    :title="title"
    :subtitle="subtitle"
    :divider-text="dividerText">
  </v-file-upload>
</template>

<script setup>
  import { computed } from 'vue'

  const emit = defineEmits(['update:modelValue'])

  const formattedValue = computed({
    get: () => props.modelValue,
    set: (val) => emit('update:modelValue', val),
  })

  const props = defineProps({
    density: { type: String, default: 'default' },
    maxWidth: { type: Number, default: null },
    modelValue: { type: [File, Array], default: null },
    multiple: { type: Boolean, default: false },
    showSize: { type: Boolean, default: false },
    clearable: { type: Boolean, default: false },
    title: { type: String, default: 'Drag and drop files here' },
    subtitle: { type: String, default: undefined },
    dividerText: { type: String, default: undefined },
  })
</script>
