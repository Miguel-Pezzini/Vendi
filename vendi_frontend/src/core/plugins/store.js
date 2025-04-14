import { createStore } from 'vuex'

// Create a new store instance.
export const store = createStore({
  state () {
    return {
      loading: {
        any: null,
      }
    }
  },
  mutations: {
    startLoading (state, url) {
        const recursoArray = url.split("/");
        const recursoTotal = recursoArray[3] + '/' + recursoArray[4];

        if (state.loading[recursoTotal] === undefined) {
            state.loading[recursoTotal] = 0;
        }

        ++state.loading[recursoTotal]
        state.loading.any = true
    },
    stopLoading(state, url) {
        const recursoArray = url.split("/");
        const recursoTotal = recursoArray[3] + '/' + recursoArray[4];
        if (state.loading[recursoTotal] === 1) { 
            delete state.loading[recursoTotal]; // Remove a chave do objeto
        } else {
            --state.loading[recursoTotal]
        }
        state.loading.any = false
    }
  }
})