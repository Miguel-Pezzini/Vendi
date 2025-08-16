<template>
  <div class="d-flex flex-column min-h-screen">
    <div class="d-flex w-100 align-center justify-space-between">
      <img v-if="$vuetify.display.mdAndUp" src="../../assets/side-image-login.png" />

      <v-form
        ref="form"
        :class="[
          'd-flex flex-column justify-center align-center w-100',
          $vuetify.display.mdAndUp ? 'mx-16' : 'my-12 mx-6',
        ]"
        @submit.prevent="register">
        <div class="w-100" style="max-width: 380px">
          <div
            class="d-flex flex-column gap-6"
            :class="{ 'text-center': !$vuetify.display.mdAndUp }">
            <h1 class="font-weight-medium text-h4">Register your account</h1>
            <p>Enter your details below</p>
          </div>

          <div class="d-flex flex-column mt-6 w-100">
            <Input v-model="name" label="Name" required />
            <Input v-model="email" label="E-mail" required validate-on="blur" :rules="emailRules" />
            <Input
              v-model="password"
              append-icon="mdi-eye"
              label="Password"
              required
              validate-on="blur"
              :min-length="6"
              type="password" />
            <Input
              v-model="repeatPassword"
              append-icon="mdi-eye"
              :rules="passwordRules"
              label="Repeat your password"
              :min-length="6"
              required
              type="password" />
          </div>

          <div class="mt-6 d-flex align-center">
            <v-btn
              height="52"
              :size="$vuetify.display.mdAndUp ? 'large' : 'default'"
              :class="$vuetify.display.mdAndUp ? 'px-12 py-4' : 'px-6 py-3'"
              type="submit"
              class="w-100"
              color="golden"
              :loading="!!$loadingState['auth/post']"
              text="Register account" />
          </div>

          <div class="d-flex justify-center align-center mt-4">
            <RouterLink class="text-decoration-none text-black opacity-80" to="/login">
              Alredy have an account? <span class="text-golden">Login!</span>
            </RouterLink>
          </div>
        </div>
      </v-form>
    </div>

    <Footer />
  </div>
</template>

<script setup>
  import { ref, getCurrentInstance } from 'vue'
  import router from '@/core/router'
  import Input from '@/core/components/Input'
  import Footer from '@/core/components/Footer.vue'
  import api from '@/core/plugins/api'

  const { proxy } = getCurrentInstance()

  const name = ref('')
  const email = ref('')
  const password = ref('')
  const repeatPassword = ref('')
  const form = ref(null)

  function validarEmail(email) {
    if (!email.includes('@')) return false
    let [local, dominio] = email.split('@')
    if (!local || !dominio) return false
    if (dominio.split('.').length < 2) return false
    if (local.length > 64 || dominio.length > 255) return false
    return true
  }

  const passwordRules = [
    (value) => {
      if (password.value === value) return true
      return 'The passwords must match!'
    },
  ]

  const emailRules = [
    (value) => {
      if (validarEmail(value)) return true
      return 'Please enter a valid email address.'
    },
  ]

  async function register() {
    const isValid = await form.value.validate()
    if (!isValid.valid) return

    api
      .register(email.value, name.value, password.value, 'USER')
      .then(() => {
        proxy.$showMessage('success', 'Your account was registered with success!')

        router.push({ name: 'Home' })
      })
      .catch((err) => {
        proxy.$showMessage('error', err)
      })
  }
</script>
