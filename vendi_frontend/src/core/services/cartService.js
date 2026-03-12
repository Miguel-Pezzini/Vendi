import api from '../plugins/api'
import productService from '../utils/productService'

async function hydrateCart(cart) {
  const items = await Promise.all(
    (cart.items || []).map(async (item) => ({
      ...item,
      product: await productService.hydrateProductSummary(item.product),
    }))
  )

  return {
    ...cart,
    items,
  }
}

async function getCart() {
  const cart = await api.get('cart')
  return hydrateCart(cart)
}

async function addItem(productId, quantity = 1) {
  const cart = await api.create('cart/items', { productId, quantity })
  return hydrateCart(cart)
}

async function removeItem(productId) {
  const cart = await api.remove(`cart/items/${productId}`)
  return hydrateCart(cart)
}

const cartService = {
  getCart,
  addItem,
  removeItem,
}

export default cartService
