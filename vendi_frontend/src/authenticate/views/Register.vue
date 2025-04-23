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
            <h1
              class="font-weight-medium"
              style="font-size: 36px;"
            >
              Crie sua Conta
            </h1>
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
              validate-on="blur"
              :rules="emailRules"
            />
            <Input
              v-model="password"
              append-icon="mdi-eye"
              label="Senha"
              required
              validate-on="blur"
              :min-length="6"
              type="password"
            />
            <Input
              v-model="repeatPassword"
              append-icon="mdi-eye"
              :rules="passwordRules"
              label="Repita sua Senha"
              :min-length="6"
              required
              type="password"
            />
          </div>
          <div class="mt-6 d-flex justify-space-between align-center">
            <v-btn
              height="52"
              :size="$vuetify.display.mdAndUp ? 'large' : 'regular'"
              type="submit"
              :style="$vuetify.display.mdAndUp ? 'padding: 16px 48px;' : 'padding: 12px 24px'"
              class="w-100"
              color="golden"
              :loading="!!$loadingState['auth/register']"
              text="Criar Conta"
            />
          </div>
          <div class="d-flex justify-center align-center mt-4">
            <RouterLink to="/">
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
// TODO REFACTOR
const name = ref("")
const email = ref("")
const password = ref("")
const repeatPassword = ref("")
const form = ref(null)

function validarEmail(email) {
    if (!email.includes("@")) return false;
    let [local, dominio] = email.split("@");
    if (!local || !dominio) return false;
    if (dominio.split(".").length < 2) return false;
    if (local.length > 64 || dominio.length > 255) return false; 
    return true;
}

const passwordRules = [
        value => {
          if (password.value == value) return true

          return 'The passwords must match!'
        },
      ]

const emailRules = [
value => {
          if (validarEmail(value)) return true

          return 'Please enter a valid email address.'
        },
]


async function registrar() {
  const isValid = await form.value.validate()
  if(!isValid.valid) return

  api.create("auth/register", {
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
</style>
