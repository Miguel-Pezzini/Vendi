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
        <SideMenu active-my-account />
      </v-col>

      <v-col>
        <v-card>
          <v-form class="rounded" @submit.prevent="saveAccount">
            <v-row>
              <h1 class="title">Change your account</h1>
            </v-row>
            <v-row>
              <Input
                v-model="account.name"
                variant="outlined"
                label="Name"
                required
                :rules="nameRules" />
            </v-row>
            <v-row class="ga-4">
              <Input
                v-model="account.email"
                variant="outlined"
                label="E-mail"
                type="email"
                required />
            </v-row>
            <v-row>
              <v-spacer />
              <Button
                title="SALVAR DADOS"
                type="submit"
                bg-color="#DBB671"
                :loading="savingAccount" />
            </v-row>

            <v-divider class="section-divider" />

            <v-row>
              <h1 class="title">Changing password</h1>
            </v-row>
            <v-row>
              <Input
                v-model="passwordForm.currentPassword"
                variant="outlined"
                label="Current password"
                type="password"
                append-icon="toggle"
                required
                :rules="currentPasswordRules" />
            </v-row>
            <v-row>
              <Input
                v-model="passwordForm.newPassword"
                variant="outlined"
                label="New password"
                type="password"
                append-icon="toggle"
                required
                :min-length="6" />
            </v-row>
            <v-row>
              <Input
                v-model="passwordForm.confirmPassword"
                variant="outlined"
                label="Confirm new password"
                type="password"
                append-icon="toggle"
                required
                :rules="confirmPasswordRules" />
            </v-row>
            <v-row>
              <v-spacer />
              <Button
                title="CANCELAR"
                type="button"
                variant="text"
                @click="resetForm" />
              <Button
                title="ALTERAR SENHA"
                type="button"
                bg-color="#DBB671"
                :loading="savingPassword"
                @click="changePassword" />
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
  import { computed, getCurrentInstance, onMounted, ref } from 'vue'

  const { proxy } = getCurrentInstance()

  const account = ref({
    name: '',
    email: '',
  })

  const originalAccount = ref({
    name: '',
    email: '',
  })

  const passwordForm = ref({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  })

  const savingAccount = ref(false)
  const savingPassword = ref(false)

  const nameRules = [(value) => !!value?.trim() || 'Name is required.']
  const currentPasswordRules = computed(() =>
    passwordForm.value.newPassword || passwordForm.value.confirmPassword
      ? [(value) => !!value || 'Current password is required.']
      : []
  )
  const confirmPasswordRules = computed(() => [
    (value) => !!value || 'Please confirm the new password.',
    (value) => value === passwordForm.value.newPassword || 'Passwords do not match.',
  ])

  onMounted(() => {
    loadAccount()
  })

  async function loadAccount() {
    try {
      const me = await api.get('me')
      account.value = {
        name: me.name ?? '',
        email: me.email ?? '',
      }
      originalAccount.value = { ...account.value }
    } catch (err) {
      proxy.$showMessage('error', normalizeError(err))
    }
  }

  async function saveAccount() {
    const payload = {
      name: account.value.name.trim(),
      email: account.value.email.trim(),
    }

    if (!payload.name || !payload.email) {
      proxy.$showMessage('error', 'Name and e-mail are required.')
      return
    }

    savingAccount.value = true

    try {
      const updatedAccount = await api.save('me', payload)
      account.value = {
        name: updatedAccount.name ?? payload.name,
        email: updatedAccount.email ?? payload.email,
      }
      originalAccount.value = { ...account.value }
      proxy.$showMessage('success', 'Account updated successfully.')
    } catch (err) {
      proxy.$showMessage('error', normalizeError(err))
    } finally {
      savingAccount.value = false
    }
  }

  async function changePassword() {
    const payload = {
      currentPassword: passwordForm.value.currentPassword,
      newPassword: passwordForm.value.newPassword,
    }

    if (!payload.currentPassword || !payload.newPassword || !passwordForm.value.confirmPassword) {
      proxy.$showMessage('error', 'Fill in all password fields.')
      return
    }

    if (payload.newPassword.length < 6) {
      proxy.$showMessage('error', 'The new password must have at least 6 characters.')
      return
    }

    if (payload.newPassword !== passwordForm.value.confirmPassword) {
      proxy.$showMessage('error', 'Passwords do not match.')
      return
    }

    savingPassword.value = true

    try {
      await api.save('me/password', payload)
      clearPasswordForm()
      proxy.$showMessage('success', 'Password updated successfully.')
    } catch (err) {
      proxy.$showMessage('error', normalizeError(err))
    } finally {
      savingPassword.value = false
    }
  }

  function resetForm() {
    account.value = { ...originalAccount.value }
    clearPasswordForm()
  }

  function clearPasswordForm() {
    passwordForm.value = {
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
    }
  }

  function normalizeError(err) {
    if (typeof err === 'string') return err
    if (err && typeof err === 'object') {
      return Object.values(err).join(' ')
    }
    return 'Unable to save your changes.'
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
  .section-divider {
    margin: 24px 0;
  }
</style>
