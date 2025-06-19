import { reactive } from 'vue'

export default {
  install(app) {
    const snackbarData = reactive({
      visible: false,
      message: '',
      color: 'success', // Ou 'error' dependendo do tipo de mensagem
    })

    // Função global showMessage
    app.config.globalProperties.$showMessage = (type, message) => {
      snackbarData.message = message
      snackbarData.color = type === 'error' ? 'red' : 'green' // Cor para erro ou sucesso
      snackbarData.visible = true
    }

    // Definir um componente global para o Snackbar
    app.provide('snackbar', snackbarData)
  },
}
