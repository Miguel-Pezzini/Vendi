import api from '../plugins/api'
import { createCheckoutService } from './createCheckoutService'

const checkoutService = createCheckoutService({
  apiClient: api,
})

export default checkoutService
