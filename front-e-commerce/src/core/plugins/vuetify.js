/**
 * plugins/vuetify.js
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

// Composables
import { createVuetify } from 'vuetify'
import { VNumberInput } from 'vuetify/labs/VNumberInput'

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  components: {
    VNumberInput,
  },
  icons: {
    defaultSet: 'mdi', // This is already the default value - only for display purposes
  },
  theme: {
    defaultTheme: 'light',
  },
})
