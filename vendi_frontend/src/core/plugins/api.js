import axios from 'axios'
import router from '../router'
import { store } from './store'

const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080'

const server = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
})

function normalizePath(resource = '') {
  return resource.startsWith('/') ? resource.slice(1) : resource
}

function setAuthSession(data) {
  localStorage.setItem('token', data.token)
  localStorage.setItem('roles', data.roles)
}

function clearAuthSession() {
  localStorage.removeItem('token')
  localStorage.removeItem('roles')
}

server.interceptors.request.use(
  (req) => {
    const token = localStorage.getItem('token')

    if (token) {
      req.headers.Authorization = `Bearer ${token}`
    }

    store.commit('startLoading', req)
    return req
  },
  (err) => Promise.reject(err?.response?.data || err)
)

server.interceptors.response.use(
  (res) => {
    store.commit('stopLoading', res)
    return res
  },
  (err) => {
    store.commit('stopLoading', err)

    if (err.response && err.response.status === 401) {
      clearAuthSession()
      router.push('/login')
    }

    return Promise.reject(err.response?.data || err)
  }
)

async function getAll(resource, params = {}) {
  const response = await server.get(normalizePath(resource), { params })
  return response.data
}

async function get(resource, id = null) {
  const path = normalizePath(resource)
  const url = id ? `${path}/${id}` : path
  const response = await server.get(url)
  return response.data
}

async function create(resource, data) {
  const response = await server.post(normalizePath(resource), data)
  return response.data
}

async function save(resource, data) {
  const response = await server.put(normalizePath(resource), data)
  return response.data
}

async function remove(resource) {
  const response = await server.delete(normalizePath(resource))
  return response.data
}

async function login(email, password) {
  const response = await server.post('auth/login', { email, password })
  setAuthSession(response.data)
  return response.data
}

async function register(email, name, password, role) {
  const response = await server.post('auth/register', { email, name, role, password })
  setAuthSession(response.data)
  return response.data
}

function logout() {
  clearAuthSession()
}

const api = {
  login,
  register,
  logout,
  get,
  getAll,
  create,
  save,
  remove,
}

export default api
