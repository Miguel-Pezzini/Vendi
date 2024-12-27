<template>
  <v-form @submit.prevent="logar">
    <v-container
      width="450"
      fill-height
      class="d-flex justify-center align-center"
    >
      <v-row class="style-vrow">
        <v-col>
          <h1 class="text-center">
            Faça seu Login
          </h1>
        </v-col>
        <v-col
          cols="12"
          class="pa-2"
        >
          <Input
            v-model="user"
            label="Nome/E-mail"
            prepend-icon="mdi-account"
            :rules="userRules"
          />
        </v-col>
        <v-col
          cols="12"
          class="pa-1"
        >
          <Input
            v-model="password"
            label="Senha"
            prepend-icon="mdi-lock"
            :rules="userRules"
            type="password"
            append-icon="mdi-eye"
          />
          <p
            class="text-caption text-center text-decoration-none"
            rel="noopener noreferrer"
            target="_blank"
          >
            <RouterLink> Esqueci minha senha</RouterLink>
          </p>
        </v-col>
        <v-col
          cols="12"
          class="d-flex justify-end align-center"
        >
          <Button
            :loading="loading"
            title="Logar"
            block
            color="black"
          />
        </v-col>
        <v-col
          cols="12"
          class="d-flex justify-center align-center"
        >
          <RouterLink to="/register">
            Clique aqui para criar sua conta!
          </RouterLink>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script setup>
import { ref } from 'vue';
import Input from '../components/Input'
import Button from '../components/Button'
import router from '@/router';
const user = ref("")
const password = ref("")
const loading = ref(false)
const userRules = [
     v => !!v || 'Esse campo é obrigatório',
]

function validarInputs() {
  if(!user.value || !password.value) return false

  return true
}

function logar() {
  if(!validarInputs()) return
  
  router.push({name: 'Home'})
}
</script>

<style scoped>
body {
  font-family: "Poppins", serif;
}
a {
  text-decoration: none;
  color: #0000FF
}
form {
  background-color: #F5F5F5;
}
/* Optional: If you need to ensure that the container takes up the full height of the screen */
.v-container {
  height: 100vh; /* 100% of the viewport height */
}
.style-vrow {
  border-radius: 10px;
  padding: 15px 35px 15px 35px;
  background-color: #FFF;
}
</style>
