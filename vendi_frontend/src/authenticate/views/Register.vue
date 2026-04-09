<template>
  <div class="auth-page">
    <main class="auth-shell">
      <section v-if="mdAndUp" class="auth-visual" aria-hidden="true">
        <div class="auth-visual__panel auth-visual__panel--register">
          <span class="auth-visual__badge">Create your Vendi account</span>

          <div class="auth-visual__copy">
            <p class="auth-visual__eyebrow">Crie sua conta</p>
            <h2 class="auth-visual__title">Cadastre-se para começar a usar a Vendi.</h2>
            <p class="auth-visual__text">
              Preencha seus dados para entrar no marketplace e manter suas informações reunidas em
              um só lugar.
            </p>
          </div>

          <ul class="auth-visual__list">
            <li>Salve seus dados para futuras compras</li>
            <li>Gerencie sua conta dentro do mesmo fluxo da loja</li>
            <li>Tenha um acesso simples desde o primeiro uso</li>
          </ul>
        </div>
      </section>

      <section class="auth-content">
        <v-form ref="form" class="auth-card auth-card--register" @submit.prevent="register">
          <div class="auth-mobile-intro">
            <span class="auth-mobile-intro__badge">Create your Vendi profile</span>
            <h2 class="auth-mobile-intro__title">Create your account</h2>
            <p class="auth-mobile-intro__text">
              Register to start shopping and managing your information in one place.
            </p>
          </div>

          <div class="auth-copy">
            <span class="auth-eyebrow">Create your account</span>
            <h1 class="auth-title">Create your account</h1>
            <p class="auth-subtitle">Add your details below to get started with Vendi.</p>
          </div>

          <div class="auth-fields">
            <Input v-model="name" class="auth-input" label="Name" required />
            <Input
              v-model="email"
              class="auth-input"
              label="E-mail"
              required
              validate-on="blur"
              :rules="emailRules" />
            <Input
              v-model="password"
              class="auth-input"
              append-icon="mdi-eye"
              label="Password"
              required
              validate-on="blur"
              :min-length="6"
              type="password" />
            <Input
              v-model="repeatPassword"
              class="auth-input"
              append-icon="mdi-eye"
              :rules="passwordRules"
              label="Repeat your password"
              :min-length="6"
              required
              type="password" />
          </div>

          <div class="auth-actions auth-actions--stacked">
            <v-btn
              class="auth-submit"
              height="52"
              type="submit"
              color="golden"
              :loading="!!$loadingState['auth/post']"
              text="Register account" />
          </div>

          <div class="auth-link-row">
            <RouterLink class="auth-link" to="/login">
              Already have an account? <span class="text-golden">Login!</span>
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
