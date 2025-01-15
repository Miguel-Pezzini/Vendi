<template>
  <v-text-field
    :type="mostrarSenha()"
    :label="localLabel"
    :variant="variant"
    :prepend-inner-icon="prependIcon"
    :append-inner-icon="mostrarIcon()"
    :rules="rules"
    :hide-details="hideDetails"
    :max-width="maxWidth"
    :density="density"
    @click:append-inner="show = !show"
    :required="required"
  />
</template>
  
<script setup>
import { ref, computed } from 'vue';
const show = ref(false)

const localLabel = computed(() => {
  return props.required ? `${props.label}*` : props.label;
});

const props = defineProps({
        label: {
            type: String,
            default: null
        },
        type: {
            type: String,
            default: "text"
        },
        prependIcon: {
            type: String,
            default: null
          },
          appendIcon: {
            type: String,
            default: null
          },
          rules: {
            type: Array,
            default: () => []
          },
          variant: {
            type: String,
            default: "underlined"
          },
          hideDetails: {
            type: Boolean,
            default: false
          },
          maxWidth: {
            type: Number,
            default: null,
          },
          density: {
            type: String,
            default: "default"
          },
          required: {
            type: Boolean,
            default: false
          }
    })

function mostrarSenha() {
  if(props.type === "number") return "number"
  if(props.type === "email") return "email"
  if(props.type === "text") return "text"
  return show.value ? "text": "password"
}
function mostrarIcon() {
  if(!props.appendIcon) return null
  return show.value ? "mdi-eye-off": "mdi-eye"
}

</script>
  
  <style scoped>
  
  </style>
  