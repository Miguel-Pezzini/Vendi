<template>
  <div class="page">
    <div class="container">
      <img
        v-if="$vuetify.display.mdAndUp"
        src="../../assets/side-image-login.png"
      >
      <v-form
        ref="form"
        :style="$vuetify.display.mdAndUp ? 'margin: 0 135px' : ' margin: 50px 30px'"
        class="form"
        @submit.prevent="logar()"
      >
        <div class="form-container">
          <div class="d-flex flex-column ga-6">
            <h1>Faça seu Login</h1>
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
              class="button-padding"
              type="submit"
              color="#DBB671"
              :loading="!!$loadingState['auth/login']"
            >
              Login
            </v-btn>
            <v-btn
              color="#000"
              variant="text"
              size="regular"
              style="text-transform: none;"
            >
              Esqueceu a senha?
            </v-btn>
          </div>
          <div class="d-flex justify-center align-center mt-4">
            <RouterLink
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

  api.carregar("auth/login", {
        email: email.value, 
        password: password.value 
  }).then(response => {
    saveTokenJWT(response.token)
    proxy.$showMessage('success', 'Login feito com sucesso.');
    router.push({name: 'Home'})
  }).catch(err => {
    console.log(err)
    proxy.$showMessage('error', err);
  }) 
}
</script>

<style scoped>
h1 {
  font-size: 36px;
  font-weight: 500;
}
a {
  text-decoration: none;
  color: #000;
  opacity: 0.8;
}
.page {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
.container {
  font-family: "Poppins", serif;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center; 
}
.form {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
}
.form-container {
  width: 380px;
}
.button-padding {
  padding: 16px 48px 16px 48px;
}
</style>
