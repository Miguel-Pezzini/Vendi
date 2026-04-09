<template>
  <div class="auth-page">
    <main class="auth-shell">
      <section v-if="mdAndUp" class="auth-visual" aria-hidden="true">
        <div class="auth-visual__panel">
          <span class="auth-visual__badge">Vendi marketplace</span>

          <div class="auth-visual__copy">
            <p class="auth-visual__eyebrow">Acesse sua conta</p>
            <h2 class="auth-visual__title">Entre para continuar comprando.</h2>
            <p class="auth-visual__text">
              Faça login para acompanhar seu carrinho, visualizar seus dados e seguir usando a
              Vendi sem perder o contexto da navegação.
            </p>
          </div>

          <ul class="auth-visual__list">
            <li>Consulte seu carrinho com mais facilidade</li>
            <li>Gerencie sua conta e seus dados pessoais</li>
            <li>Continue navegando pelo marketplace sem começar do zero</li>
          </ul>
        </div>
      </section>

      <section class="auth-content">
        <v-form ref="form" class="auth-card" @submit.prevent="logar">
          <div class="auth-mobile-intro">
            <span class="auth-mobile-intro__badge">Vendi marketplace</span>
            <h2 class="auth-mobile-intro__title">Acesse sua conta</h2>
            <p class="auth-mobile-intro__text">
              Faça login para continuar comprando e gerenciar sua conta.
            </p>
          </div>

          <div class="auth-copy">
            <span class="auth-eyebrow">Login</span>
            <h1 class="auth-title">Log In</h1>
            <p class="auth-subtitle">Enter your email and password to access your account.</p>
          </div>

          <div class="auth-fields">
            <Input v-model="email" class="auth-input" label="Email address" required />
            <Input
              v-model="password"
              class="auth-input"
              append-icon="mdi-eye"
              label="Password"
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
