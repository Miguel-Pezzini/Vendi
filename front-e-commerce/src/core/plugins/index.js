/**
 * plugins/index.js
 *
 * Automatically included in `./src/main.js`
 */

// Plugins
import vuetify from './vuetify'
import router from '@/core/router'
import MessagePlugin from './messagePlugin'

export function registerPlugins (app) {
  app
    .use(vuetify)
    .use(router)
    .use(MessagePlugin)
    
}
