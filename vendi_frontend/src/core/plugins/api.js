import axios from 'axios'
import { store } from './store'
import { createApiClient } from './createApiClient'

const BASE_URL = import.meta.env?.VITE_API_URL || 'http://localhost:8080'

const server = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
})

const api = createApiClient({
  server,
  storage: globalThis.localStorage,
  navigateToLogin: async () => {
    const { default: router } = await import('../router')
    router.push('/login')
  },
  store,
})

export default api
