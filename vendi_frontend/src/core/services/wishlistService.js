import api from '../plugins/api'
import productService from '../utils/productService'
import { createWishlistService } from './createWishlistService'

const wishlistService = createWishlistService({
  apiClient: api,
  productClient: productService,
})

export default wishlistService
