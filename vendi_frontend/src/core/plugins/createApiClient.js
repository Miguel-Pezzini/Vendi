const fallbackStorage = {
  getItem() {
    return null
  },
  setItem() {},
  removeItem() {},
}

export function normalizePath(resource = '') {
  return resource.startsWith('/') ? resource.slice(1) : resource
}

export function setAuthSession(storage, data) {
  storage.setItem('token', data.token)
  storage.setItem('roles', data.roles)
}

export function clearAuthSession(storage) {
  storage.removeItem('token')
  storage.removeItem('roles')
}

export function createRequestInterceptor({ storage, store }) {
  return (req) => {
    const token = storage.getItem('token')

    if (token) {
      req.headers.Authorization = `Bearer ${token}`
    }

    store?.commit?.('startLoading', req)
    return req
  }
}

export function createRequestErrorInterceptor() {
  return (err) => Promise.reject(err?.response?.data || err)
}

export function createResponseSuccessInterceptor({ store }) {
  return (res) => {
    store?.commit?.('stopLoading', res)
    return res
  }
}

export function createResponseErrorInterceptor({ storage, navigateToLogin, store }) {
  return (err) => {
    store?.commit?.('stopLoading', err)

    if (err.response && err.response.status === 401) {
      clearAuthSession(storage)
      navigateToLogin?.()
    }

    return Promise.reject(err.response?.data || err)
  }
}

export function createApiClient({ server, storage = fallbackStorage, navigateToLogin, store }) {
  server.interceptors.request.use(
    createRequestInterceptor({ storage, store }),
    createRequestErrorInterceptor()
  )

  server.interceptors.response.use(
    createResponseSuccessInterceptor({ store }),
    createResponseErrorInterceptor({ storage, navigateToLogin, store })
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
    setAuthSession(storage, response.data)
    return response.data
  }

  async function register(email, name, password, role) {
    const response = await server.post('auth/register', { email, name, role, password })
    setAuthSession(storage, response.data)
    return response.data
  }

  function logout() {
    clearAuthSession(storage)
  }

  return {
    login,
    register,
    logout,
    get,
    getAll,
    create,
    save,
    remove,
  }
}
