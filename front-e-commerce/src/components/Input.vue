<template>
  <v-text-field
    :type="mostrarSenha()"
    :label="label"
    :variant="variant"
    :prepend-inner-icon="prependIcon"
    :append-inner-icon="mostrarIcon()"
    :rules="rules"
    :hide-details="hideDetails"
    @click:append-inner="show = !show"
  />
</template>
  
<script setup>
import { ref } from 'vue';
const show = ref(false)

const props = defineProps({
        label: {
            type: String,
            default: "Label"
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
            default: v => v
          },
          variant: {
            type: String,
            default: "underlined"
          },
          hideDetails: {
            type: Boolean,
            default: false
          }
    })

function mostrarSenha() {
  console.log(props.rules)
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
  