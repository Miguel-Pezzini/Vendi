import api from '../plugins/api'
import productService from '../utils/productService'
import { createCartService } from './createCartService'

const cartService = createCartService({
  apiClient: api,
  productClient: productService,
})

export default cartService
