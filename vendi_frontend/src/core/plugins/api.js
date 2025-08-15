import axios from 'axios'
import router from '../router'
import { store } from './store'

const BASE_URL = 'http://localhost:8080/'

const server = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
})

const getHeaders = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem('token')}`,
  },
}

server.interceptors.request.use(
  (req) => {
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

    console.log(err)
    
    if (err.response && err.response.status === 401) {
      router.push('/login')
    }

    return Promise.reject(err.response?.data || err)
  }
)

async function getAll(resource, params = {}) {
  const response = await server.get(resource, {
    params,
    ...getHeaders,
  })
  return response.data
}

async function get(resource, id = null) {
  const url = id ? `${resource}/${id}` : resource
  const response = await server.get(url, getHeaders)
  return response.data
}

async function create(resource, data) {
  const response = await server.post(resource, data, getHeaders)
  return response.data
}

async function save(resource, data) {
  const response = await server.put(resource, data, getHeaders)
  return response.data
}

async function login(email, password) {
  const response = await server.post('auth/login', { email, password })
  localStorage.setItem('token', response.data.token)
  localStorage.setItem('roles', response.data.roles)
  return response.data
}

async function register(email, name, password, role) {
  const response = await server.post('auth/register', { email, name, role, password })
  localStorage.setItem('roles', response.data.roles)
  localStorage.setItem('token', response.data.token)

    server.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`
  return response.data
}

const api = {
  login,
  register,
  get,
  getAll,
  create,
  save,
}

export default api