<template>
  <v-text-field
    :type="mostrarSenha()"
    :label="localLabel"
    :variant="variant"
    :prepend-inner-icon="prependIcon"
    :append-inner-icon="mostrarIcon()"
    :rules="computedRules"
    :hide-details="hideDetails"
    :max-width="maxWidth"
    :density="density"
    :prefix="prefix"
    :required="required"
    :validate-on="validateOn"
    @click:append-inner="show = !show" />
</template>

<script setup>
  import { ref, computed } from 'vue'
  const show = ref(false)

  const localLabel = computed(() => {
    return props.required ? `${props.label}*` : props.label
  })

  const computedRules = computed(() => {
    const autoRules = []

    if (props.required) {
      autoRules.push((value) => !!value || 'Este campo é obrigatório.')
    }
    if (props.minLength) {
      autoRules.push(
        (value) =>
          value.length >= props.minLength ||
          `O campo deve ter ao menos ${props.minLength} caracteres.`
      )
    }

    if (props.type === 'email') {
      autoRules.push((value) => /.+@.+\..+/.test(value) || 'Por favor, insira um e-mail válido.')
    }

    // Combina regras automáticas com as fornecidas manualmente
    return [...autoRules, ...props.rules]
  })

  const props = defineProps({
    label: {
      type: String,
      default: null,
    },
    type: {
      type: String,
      default: 'text',
    },
    prependIcon: {
      type: String,
      default: null,
    },
    appendIcon: {
      type: String,
      default: null,
    },
    rules: {
      type: Array,
      default: () => [],
    },
    variant: {
      type: String,
      default: 'underlined',
    },
    hideDetails: {
      type: Boolean,
      default: false,
    },
    maxWidth: {
      type: Number,
      default: null,
    },
    density: {
      type: String,
      default: 'default',
    },
    required: {
      type: Boolean,
      default: false,
    },
    minLength: {
      type: Number,
      default: null,
    },
    validateOn: {
      type: String,
      default: undefined,
    },
    prefix: {
      type: String,
      default: null,
    },
  })

  function mostrarSenha() {
    if (props.type !== 'password') return props.type
    return show.value ? 'text' : 'password'
  }
  function mostrarIcon() {
    if (!props.appendIcon) return null
    return show.value ? 'mdi-eye-off' : 'mdi-eye'
  }
</script>

<style scoped></style>
