<template>
  <div
    class="d-flex flex-column"
    style="min-height: 100vh;"
  >
    <div class="d-flex align-center w-100 justify-space-between">
      <img
        v-if="$vuetify.display.mdAndUp"
        src="../../assets/side-image-login.png"
      >
      <v-form
        ref="form"
        :style="$vuetify.display.mdAndUp ? 'margin: 0 135px' : ' margin: 50px 30px'"
        class="d-flex flex-column justify-center align-center w-100"
        @submit.prevent="logar()"
      >
        <div style="width: 380px;">
          <div class="d-flex flex-column ga-6">
            <h1
              style="font-size: 36px;"
              class="font-weight-medium"
            >
              Faça seu Login
            </h1>
            <p>Insira seus dados abaixo</p>
          </div>
          <div class="d-flex flex-column mt-6 w-100">
            <Input
              v-model="email"
              label="E-mail ou nome de usuário"
              required
            />
            <Input
              v-model="password"
              append-icon="mdi-eye"
              label="Senha"
              required
              type="password"
            />
          </div>
          <div
            :style="$vuetify.display.mdAndUp ? 'justify-content: space-between' : 'justify-content: start; gap: 12px'"
            class="mt-6 d-flex align-center"
          >
            <v-btn
              height="52"
              :size="$vuetify.display.mdAndUp ? 'large' : 'regular'"
              :style="$vuetify.display.mdAndUp ? 'padding: 16px 48px;' : 'padding: 12px 24px'"
              type="submit"
              color="golden"
              text="Login"
              :loading="!!$loadingState['auth/login']"
            />
            <v-btn
              color="black"
              variant="text"
              size="regular"
              text=" Esqueceu a senha?"
              style="text-transform: none;"
            />
          </div>
          <div class="d-flex justify-center align-center mt-4">
            <RouterLink
              class="text-decoration-none"
              style="color: black; opacity: 0.8;"
              to="/register"
            >
              Não possui conta? <span style="color: #DBB671">Criar uma!</span>
            </RouterLink>
          </div>
        </div>
      </v-form>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue';
import router from '@/core/router';
import Input from '@/core/components/Input'
import Footer from '@/core/components/Footer.vue';
import api from '@/core/plugins/api';
import { saveTokenJWT } from '@/core/utils/saveTokenJWT';

const { proxy } = getCurrentInstance();

const email = ref("")
const password = ref("")
const form = ref(null)

async function logar() {
  const isValid = await form.value.validate()
  if(!isValid.valid) return

  await api.carregar("auth/login", {
        email: email.value, 
        password: password.value 
  }).catch(err => {
    proxy.$showMessage('error', err);
  }) 

  saveTokenJWT(response.token)
  proxy.$showMessage('success', 'Login feito com sucesso.');
  router.push({name: 'Home'})
}
</script>
