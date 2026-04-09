<template>
  <div class="auth-page">
    <main class="auth-shell">
      <section v-if="mdAndUp" class="auth-visual" aria-hidden="true">
        <div class="auth-visual__image-wrapper">
          <img class="auth-visual__image" src="../../assets/side-image-login.png" alt="" />
        </div>
      </section>

      <section class="auth-content">
        <v-form ref="form" class="auth-card" @submit.prevent="logar">
          <div class="auth-copy">
            <span class="auth-eyebrow">Welcome back</span>
            <h1 class="auth-title">Log In</h1>
            <p class="auth-subtitle">Enter your details below</p>
          </div>

          <div class="auth-fields">
            <Input v-model="email" class="auth-input" label="Enter your E-mail" required />
            <Input
              v-model="password"
              class="auth-input"
              append-icon="mdi-eye"
              label="Senha"
              required
              type="password" />
          </div>

          <div class="auth-actions">
            <v-btn
              class="auth-submit"
              height="52"
              type="submit"
              color="golden"
              text="Login"
              :loading="!!$loadingState['auth/post']" />
            <v-btn
              class="auth-secondary-action text-none"
              color="black"
              variant="text"
              text="Forgot your password?" />
          </div>

          <div class="auth-link-row">
            <RouterLink class="auth-link" to="/register">
              Don't have an account? <span class="text-golden">Create one!</span>
            </RouterLink>
          </div>
        </v-form>
      </section>
    </main>
    <Footer />
  </div>
</template>

<script setup>
  import { ref, getCurrentInstance } from 'vue'
  import { useDisplay } from 'vuetify'
  import router from '@/core/router'
  import Input from '@/core/components/Input'
  import Footer from '@/core/components/Footer.vue'
  import api from '@/core/plugins/api'
  import '@/authenticate/styles/auth-layout.css'

  const { proxy } = getCurrentInstance()
  const { mdAndUp } = useDisplay()

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
