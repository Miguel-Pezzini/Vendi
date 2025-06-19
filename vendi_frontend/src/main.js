/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/core/plugins'
import { store } from './core/plugins/store'

// Components
import App from './App.vue'

// Composables
import { createApp } from 'vue'

const app = createApp(App)

app.config.globalProperties.$loadingState = store.state.loading

registerPlugins(app)

app.mount('#app')
