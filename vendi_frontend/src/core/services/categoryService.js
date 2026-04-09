import api from '../plugins/api'
import { createCategoryService } from './createCategoryService'

const categoryService = createCategoryService({
  apiClient: api,
})

export default categoryService
