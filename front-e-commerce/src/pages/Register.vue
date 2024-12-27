<template>
  <v-form @submit.prevent="registrou">
    <v-container
      width="450"
      fill-height
      class="d-flex justify-center align-center"
    >
      <v-row class="style-vrow">
        <v-col>
          <h1 class="text-center">
            Crie sua conta
          </h1>
        </v-col>
        <v-col
          cols="12"
          class="pa-1"
        >
          <Input
            v-model="name"
            label="Nome"
            prepend-icon="mdi-account"
            :rules="userRules"
          />
        </v-col>
        <v-col
          cols="12"
          class="pa-1"
        >
          <Input
            v-model="email"
            type="email"
            label="E-mail"
            prepend-icon="mdi-email"
            :rules="userRules"
          />
        </v-col>
        <v-col
          cols="12"
          class="pa-1"
        >
          <Input
            v-model="password"
            type="password"
            label="Senha"
            prepend-icon="mdi-lock"
            :rules="passwordRules"
            append-icon="mdi-eye"
          />
        </v-col>
        <v-col
          cols="12"
          class="pa-1"
        >
          <Input
            v-model="passwordRepeat"
            type="password"
            label="Senha novamente"
            prepend-icon="mdi-lock"
            :rules="confirmPasswordRules"
            append-icon="mdi-eye"
          />
        </v-col>
        <v-col
          cols="12"
          class="d-flex justify-end align-center"
        >
          <Button
            title="Criar Conta"
            block
            color="black"
          />
        </v-col>
        <v-col
          cols="12"
          class="d-flex justify-center align-center"
        >
          <RouterLink to="/login">
            Clique aqui caso você já tenha uma conta!
          </RouterLink>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>
  
  <script setup>
  import Input from '../components/Input'
  import Button from '../components/Button'
  import router from '@/router';
  import { ref } from 'vue';

  const name = ref("")
  const email = ref("")
  const password = ref("")
  const passwordRepeat = ref("")

  const userRules = [
    v => !!v || 'Esse campo é obrigatório',
  ]

  const passwordRules = [
    v => !!v || 'Esse campo é obrigatório',
    v => v.length >= 6 || 'A senha deve ter pelo menos 6 caracteres',
  ]

  const confirmPasswordRules =  [
    v => !!v || 'Esse campo é obrigatório',
    v => v === password.value || 'As senhas não coincidem',
  ];
  function validarInputs() {
    if(!name.value || !email.value || !password.value || !passwordRepeat.value) return false
    if(password.value !== passwordRepeat.value) return false
    if(password.value.length < 6) return false

    return true
  }
  function registrou() {
    if(!validarInputs()) return
    router.push({ name: 'Home' })
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
  