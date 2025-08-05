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
      const mainResource = req.url.split('/')[0]?.split('&')[0]
      const recursoTotal = mainResource + '/' + req.method
      if (state.loading[recursoTotal] === undefined) {
        state.loading[recursoTotal] = 0
      }

      ++state.loading[recursoTotal]
      state.loading.any = true
    },
    stopLoading(state, res) {
      const mainResource = res.config.url.split('/')[0]?.split('&')[0]
      const recursoTotal = mainResource + '/' + res.config.method
      if (state.loading[recursoTotal] === 1) {
        delete state.loading[recursoTotal] // Remove a chave do objeto
      } else {
        --state.loading[recursoTotal]
      }
      state.loading.any = false
    },
  },
})
