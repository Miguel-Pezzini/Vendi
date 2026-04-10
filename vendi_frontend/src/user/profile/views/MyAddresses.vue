<template>
  <Header account-active />
  <v-divider />
  <v-container class="container">
    <v-row>
      <Path :old-paths="['Home']" active-path="Manage addresses" />
      <v-spacer />
      <Button @click="dialogAddAddress()" title="Add Address" variant="outlined" color="golden" />
    </v-row>

    <v-row class="container margin">
      <v-col class="pa-0" cols="4">
        <SideMenu active-addresses />
      </v-col>

      <v-col>
        <span v-if="!addresses.length" class="opacity-50">
          You haven't added any address yet. Click "Add Address" to get started!
        </span>
        <v-row>
          <v-col v-for="address in addresses" :key="address.id" md="6" cols="12">
            <v-card class="pa-0 ma-0">
              <v-card-title>
                {{ address.street }}, {{ address.city }} - {{ address.state }}
              </v-card-title>

              <v-card-subtitle> CEP: {{ address.zipCode }} </v-card-subtitle>

              <v-card-text>
                <v-chip v-if="address.id === activeAddressId" color="green" label small>
                  Ativo
                </v-chip>
              </v-card-text>

              <v-card-actions>
                <v-btn icon @click="openEditDialog(address)">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <v-btn icon color="red" @click="deleteAddress(address.id)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
                <v-btn
                  text
                  color="blue"
                  v-if="address.id !== activeAddressId"
                  @click="setActive(address.id)">
                  Definir como ativo
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
  <Footer />
  <v-dialog v-model="dialog" max-width="500px">
    <v-card class="pa-4 ma-0">
      <v-card-title class="text-golden">
        {{ editingAddressId ? 'Editing Address' : 'New Address' }}
      </v-card-title>

      <v-card-text class="pa-2">
        <v-row dense>
          <v-col cols="12">
            <Input prependIcon="mdi-map-marker-outline" v-model="form.street" label="Rua" />
          </v-col>
          <v-col cols="6">
            <Input prependIcon="mdi-city-variant-outline" v-model="form.city" label="Cidade" />
          </v-col>
          <v-col cols="6">
            <Input v-model="form.state" label="Estado" />
          </v-col>
          <v-col cols="12">
            <Input prependIcon="mdi-mailbox-outline" v-model="form.zipCode" label="CEP" />
          </v-col>
        </v-row>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn text @click="closeDialog">Cancel</v-btn>
        <v-btn color="golden" :loading="saving" @click="saveAddress">
          Save
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
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

  onMounted(() => {
    loadPage()
  })

  const addresses = ref([])
  const account = ref({})
  const dialog = ref(false)
  const saving = ref(false)
  const editingAddressId = ref(null)
  const form = ref({
    street: '',
    city: '',
    state: '',
    zipCode: '',
  })
  const activeAddressId = computed(() => account.value?.currentAddress?.id ?? null)

  async function loadPage() {
    await Promise.all([loadAddresses(), loadAccount()])
  }

  async function loadAddresses() {
    const response = await api.get('me/addresses').catch((err) => {
      proxy.$showMessage('error', err)
      return null
    })

    if (response) {
      addresses.value = response
    }
  }

  async function loadAccount() {
    const response = await api.get('me').catch((err) => {
      proxy.$showMessage('error', err)
      return null
    })

    if (response) {
      account.value = response
    }
  }

  function resetForm() {
    form.value = {
      street: '',
      city: '',
      state: '',
      zipCode: '',
    }
  }

  function dialogAddAddress() {
    editingAddressId.value = null
    resetForm()
    dialog.value = true
  }

  function openEditDialog(address) {
    editingAddressId.value = address.id
    form.value = {
      street: address.street ?? '',
      city: address.city ?? '',
      state: address.state ?? '',
      zipCode: address.zipCode ?? '',
    }
    dialog.value = true
  }

  function closeDialog() {
    dialog.value = false
    editingAddressId.value = null
    resetForm()
  }

  function validateForm() {
    if (!form.value.street?.trim() || !form.value.city?.trim() || !form.value.state?.trim() || !form.value.zipCode?.trim()) {
      proxy.$showMessage('error', 'Fill in street, city, state and zip code before saving the address.')
      return false
    }

    return true
  }

  function buildPayload() {
    return {
      street: form.value.street.trim(),
      city: form.value.city.trim(),
      state: form.value.state.trim(),
      zipCode: form.value.zipCode.trim(),
    }
  }

  async function saveAddress() {
    if (!validateForm()) {
      return
    }

    saving.value = true
    const wasEditing = Boolean(editingAddressId.value)

    const request = wasEditing
      ? api.save(`me/addresses/${editingAddressId.value}`, buildPayload())
      : api.create('me/addresses', buildPayload())

    const savedAddress = await request.catch((err) => {
      proxy.$showMessage('error', err)
      return null
    })

    saving.value = false

    if (!savedAddress) {
      return
    }

    closeDialog()
    await loadPage()
    proxy.$showMessage('success', wasEditing ? 'Address updated successfully.' : 'Address created successfully.')
  }

  async function deleteAddress(addressId) {
    if (!window.confirm('Do you want to remove this address?')) {
      return
    }

    const deleted = await api.remove(`me/addresses/${addressId}`).then(() => true).catch((err) => {
      proxy.$showMessage('error', err)
      return false
    })

    if (!deleted) {
      return
    }

    await loadPage()
    proxy.$showMessage('success', 'Address removed successfully.')
  }

  async function setActive(addressId) {
    const activated = await api.save(`me/addresses/${addressId}/active`).then(() => true).catch((err) => {
      proxy.$showMessage('error', err)
      return false
    })

    if (!activated) {
      return
    }

    await loadAccount()
    proxy.$showMessage('success', 'Active address updated successfully.')
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
