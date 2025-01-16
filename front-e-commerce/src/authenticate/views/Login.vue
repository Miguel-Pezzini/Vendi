<template>
  <div class="page">
    <div class="container">
      <img src="../../assets/side-image-login.png">
      <v-form class="form" ref="form" @submit.prevent="logar()">
        <div class="form-container">
          <div class="d-flex flex-column ga-6">
            <h1>Faça seu Login</h1>
            <p>Insira seus dados abaixo</p>
          </div>
          <div class="d-flex flex-column mt-6 w-100">
            <Input
              v-model="user"
              label="E-mail ou nome de usuário"
              required
            />
            <Input
              appendIcon="mdi-eye"
              v-model="password"
              label="Senha"
              required
              type="password"
            />
          </div>
          <div class="mt-6 d-flex justify-space-between align-center">
            <v-btn
              height="52"
              size="large"
              class="button-padding"
              color="#DBB671"
              :loading="loading"
              @click="logar()"
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
            >Não possui conta? <span style="color: #DBB671">Criar uma!</span></RouterLink>
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
const loading = ref(false)
const form = ref(null)


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
