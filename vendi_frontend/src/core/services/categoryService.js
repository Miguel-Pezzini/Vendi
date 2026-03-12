import api from '../plugins/api'

function buildCategoryTree(categories) {
  const categoriesById = new Map(
    categories.map((category) => [
      category.id,
      {
        ...category,
        childCategories: [],
      },
    ])
  )

  const roots = []

  categoriesById.forEach((category) => {
    if (category.fatherCategoryId && categoriesById.has(category.fatherCategoryId)) {
      categoriesById.get(category.fatherCategoryId).childCategories.push(category)
      return
    }

    roots.push(category)
  })

  return roots
}

async function getCategories() {
  const categories = await api.getAll('category')
  return buildCategoryTree(categories)
}

const categoryService = {
  getCategories,
}

export default categoryService
