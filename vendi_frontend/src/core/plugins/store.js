import { createStore } from 'vuex'

// Create a new store instance.
export const store = createStore({
  state() {
    return {
      loading: {
        any: null,
      },
    }
  },
  mutations: {
    startLoading(state, req) {
      const mainResource = req.url.replace(/^\//, '').split('/')[0]?.split('?')[0]
      const recursoTotal = mainResource + '/' + req.method
      if (state.loading[recursoTotal] === undefined) {
        state.loading[recursoTotal] = 0
      }

      ++state.loading[recursoTotal]
      state.loading.any = true
    },
    stopLoading(state, res) {
      const requestUrl = res?.config?.url || ''
      const requestMethod = res?.config?.method || 'get'
      const mainResource = requestUrl.replace(/^\//, '').split('/')[0]?.split('?')[0]
      const recursoTotal = mainResource + '/' + requestMethod
      if (state.loading[recursoTotal] === 1) {
        delete state.loading[recursoTotal]
      } else if (state.loading[recursoTotal] > 1) {
        --state.loading[recursoTotal]
      }

      state.loading.any = Object.keys(state.loading).some(
        (key) => key !== 'any' && state.loading[key] > 0
      )
    },
  },
})
