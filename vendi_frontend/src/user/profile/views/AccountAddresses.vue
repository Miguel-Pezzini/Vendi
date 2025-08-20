<template>
  <Header account-active />
  <v-divider />
  <v-container class="container">
    <v-row>
      <Path :old-paths="['Home']" active-path="My Account" />
      <v-spacer />
      <p>
        Welcome! <span style="color: #dbb671">{{ account.name }}</span>
      </p>
    </v-row>

    <v-row class="container margin">
      <v-col class="pa-0" cols="4">
        <SideMenu active-addresses />
      </v-col>

      <v-col>
        <v-card>
          <v-form class="rounded">
            <v-row>
              <h1 class="title">Change your account</h1>
            </v-row>
            <v-row>
              <Input variant="outlined" label="Name" v-model="account.name" />
            </v-row>
            <v-row class="ga-4">
              <Input variant="outlined" v-model="account.email" label="E-mail" />
            </v-row>
            <v-row>
              <h1 class="title">Changing password</h1>
            </v-row>
            <v-row>
              <Input variant="outlined" label="Current password" />
            </v-row>
            <v-row>
              <Input variant="outlined" label="New password" />
            </v-row>
            <v-row>
              <Input variant="outlined" label="Confirm new password" />
            </v-row>
            <v-row>
              <v-spacer />
              <Button title="CANCELAR" variant="text" />
              <Button title="SALVAR MUDANçAS" bg-color="#DBB671" />
            </v-row>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
  <Footer />
</template>

<script setup>
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import SideMenu from '../components/SideMenu.vue'
  import Input from '@/core/components/Input.vue'
  import Button from '@/core/components/Button.vue'
  import Footer from '@/core/components/Footer.vue'
  import api from '@/core/plugins/api'
  import { onMounted, ref, getCurrentInstance } from 'vue'
  const { proxy } = getCurrentInstance()

  const account = ref({
    name: '',
    email: '',
  })

  onMounted(() => {
    loadAccount()
  })

  async function loadAccount() {
    account.value = await api.get('me/addresses').catch((err) => {
      proxy.$showMessage('error', err)
    })
  }
</script>

<style scoped>
  .container {
    margin-top: 80px;
  }
  .v-card {
    padding: 40px 80px 40px 80px;
    box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
  }
  .title {
    padding-bottom: 16px;
    color: #dbb671;
    font-size: 20px;
    font-weight: 500;
  }
  .margin {
    margin-bottom: 146px;
  }
</style>
