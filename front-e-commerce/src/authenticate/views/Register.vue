<template>
  <div class="page">
    <div class="container">
      <img src="../../assets/side-image-login.png">
      <v-form class="form" ref="form" @submit.prevent="logar()">
        <div class="form-container">
          <div class="d-flex flex-column ga-6">
            <h1>Crie sua Conta</h1>
            <p>Insira seus dados abaixo</p>
          </div>
          <div class="d-flex flex-column mt-6 w-100">
            <Input
              v-model="user"
              label="Nome de usuário"
              required
            />
            <Input
              v-model="email"
              label="E-mail"
              required
            />
            <Input
              appendIcon="mdi-eye"
              v-model="password"
              label="Senha"
              required
              :rules="passwordRules"
              :min-Length="6"
              type="password"
            />
            <Input
              appendIcon="mdi-eye"
              v-model="repeatPassword"
              label="Repita sua Senha"
              :min-Length="6"
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
              :loading="loading"
              @click="logar()"
            >
              Criar Conta
            </v-btn>
          </div>
          <div class="d-flex justify-center align-center mt-4">
            <RouterLink
              to="/"
            >Ja possui conta? <span style="color: #DBB671">Logar!</span></RouterLink>
          </div>
          

        </div>
      </v-form>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import router from '@/core/router';
import Input from '@/core/components/Input'
import Footer from '@/core/components/Footer.vue';

const user = ref("")
const password = ref("")
const repeatPassword = ref("")
const loading = ref(false)
const form = ref(null)

const passwordRules = [
        value => {
          if (repeatPassword.value == value) return true

          return 'As senhas tem que ser iguais'
        },
      ]


async function logar() {
  const isValid = await form.value.validate()
  if(!isValid.valid) return

  loading.value = true;
  router.push({name: 'Home'})
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
  margin: 0 135px;
}
.form-container {
  width: 380px;
}
.button-padding {
  padding: 16px 48px 16px 48px;
}
</style>
