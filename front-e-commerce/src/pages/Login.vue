<template>
  <v-form @submit.prevent="logar">
    <v-container
      width="450"
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
            <RouterLink>Esqueci minha senha</RouterLink>
          </p>
        </v-col>
        <v-col
          cols="12"
          class="d-flex justify-end align-center"
        >
          <Button
            bg-color="black"
            :loading="loading"
            title="Logar"
            block
            color="white"
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
import Input from '../core/Input'
import Button from '../core/Button'
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
  color: #DBB671;
}
form {
  background-color: #F5F5F5;
}
.v-container {
  height: 100vh;
}
.style-vrow {
  border-radius: 10px;
  padding: 15px 35px 15px 35px;
  background-color: #FFF;
}
</style>
