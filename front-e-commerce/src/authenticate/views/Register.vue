<template>
  <div class="page">
    <div class="container">
      <img src="../../assets/side-image-login.png">
      <v-form
        ref="form"
        class="form"
        @submit.prevent="registrar()"
      >
        <div class="form-container">
          <div class="d-flex flex-column ga-6">
            <h1>Crie sua Conta</h1>
            <p>Insira seus dados abaixo</p>
          </div>
          <div class="d-flex flex-column mt-6 w-100">
            <Input
              v-model="name"
              label="Nome de usuário"
              required
            />
            <Input
              v-model="email"
              label="E-mail"
              required
            />
            <Input
              v-model="password"
              append-icon="mdi-eye"
              label="Senha"
              required
              :rules="passwordRules"
              :min-length="6"
              type="password"
            />
            <Input
              v-model="repeatPassword"
              append-icon="mdi-eye"
              label="Repita sua Senha"
              :min-length="6"
              required
              type="password"
            />
          </div>
          <div class="mt-6 d-flex justify-space-between align-center">
            <v-btn
              height="52"
              size="large"
              type="submit"
              class="button-padding w-100"
              color="#DBB671"
              :loading="!!$loadingState['auth/register']"
            >
              Criar Conta
            </v-btn>
          </div>
          <div class="d-flex justify-center align-center mt-4">
            <RouterLink
              to="/"
            >
              Ja possui conta? <span style="color: #DBB671">Logar!</span>
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

const name = ref("")
const email = ref("")
const password = ref("")
const repeatPassword = ref("")
const form = ref(null)

const passwordRules = [
        value => {
          if (repeatPassword.value == value) return true

          return 'As senhas tem que ser iguais'
        },
      ]


async function registrar() {
  const isValid = await form.value.validate()
  if(!isValid.valid) return

  api.carregar("auth/register", {
        name: name.value,
        email: email.value, 
        password: password.value,
        role: "USER"
  }).then(response => {
    proxy.$showMessage('success', 'Conta registrada com sucesso.');
    saveTokenJWT(response.token);
    router.push({name: 'Home'})
  }) .catch(err => {
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
  margin: 0 135px;
}
.form-container {
  width: 380px;
}
.button-padding {
  padding: 16px 48px 16px 48px;
}
</style>
