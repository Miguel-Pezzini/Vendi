<template>
  <div class="d-flex flex-column min-h-screen">
    <div class="d-flex align-center w-100 justify-space-between">
      <img v-if="$vuetify.display.mdAndUp" src="../../assets/side-image-login.png" />

      <v-form
        ref="form"
        :class="[
          'd-flex flex-column justify-center align-center w-100',
          $vuetify.display.mdAndUp ? 'mx-16' : 'my-12 mx-6',
        ]"
        @submit.prevent="logar">
        <div class="w-100" style="max-width: 380px">
          <div
            class="d-flex flex-column gap-6"
            :class="{ 'text-center': !$vuetify.display.mdAndUp }">
            <h1 class="font-weight-medium text-h4">Log In</h1>
            <p>Enter your details below</p>
          </div>

          <div class="d-flex flex-column mt-6 w-100">
            <Input v-model="email" label="Enter your E-mail" required />
            <Input
              v-model="password"
              append-icon="mdi-eye"
              label="Senha"
              required
              type="password" />
          </div>

          <div
            class="mt-6 d-flex align-center"
            :class="$vuetify.display.mdAndUp ? 'justify-space-between' : 'justify-start gap-3'">
            <v-btn
              height="52"
              :size="$vuetify.display.mdAndUp ? 'large' : 'default'"
              :class="$vuetify.display.mdAndUp ? 'px-12 py-4' : 'px-6 py-3'"
              type="submit"
              color="golden"
              text="Login"
              :loading="!!$loadingState['auth/post']" />
            <v-btn
              color="black"
              variant="text"
              size="default"
              text="Forgot your password?"
              class="text-none" />
          </div>

          <div class="d-flex justify-center align-center mt-4">
            <RouterLink class="text-decoration-none text-black opacity-80" to="/register">
              Don't have an account? <span class="text-golden">Create one!</span>
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

  const email = ref('')
  const password = ref('')
  const form = ref(null)

  async function logar() {
    const isValid = await form.value.validate()
    if (!isValid.valid) return

    api
      .login(email.value, password.value)
      .then(() => {
        proxy.$showMessage('success', 'Login successful. Welcome back!')
        router.push({ name: 'Home' })
      })
      .catch(() => {
        proxy.$showMessage('error', 'Invalid credentials, try again')
      })
  }
</script>
