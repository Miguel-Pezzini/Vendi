import api from '../plugins/api'
import productService from '../utils/productService'
import { createOrderService } from './createOrderService'

const orderService = createOrderService({
  apiClient: api,
  productClient: productService,
})

export default orderService
