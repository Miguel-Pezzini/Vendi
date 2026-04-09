import api from '../plugins/api'
import imageService from './imageService'
import { createProductService } from './createProductService'

const productService = createProductService({
  apiClient: api,
  imageUtils: imageService,
})

export default productService
