export function createCartService({ apiClient, productClient }) {
  async function hydrateCart(cart) {
    const items = await Promise.all(
      (cart.items || []).map(async (item) => ({
        ...item,
        product: await productClient.hydrateProductSummary(item.product),
      }))
    )

    return {
      ...cart,
      items,
    }
  }

  async function getCart() {
    const cart = await apiClient.get('cart')
    return hydrateCart(cart)
  }

  async function addItem(productId, quantity = 1) {
    const cart = await apiClient.create('cart/items', { productId, quantity })
    return hydrateCart(cart)
  }

  async function removeItem(productId) {
    const cart = await apiClient.remove(`cart/items/${productId}`)
    return hydrateCart(cart)
  }

  return {
    getCart,
    addItem,
    removeItem,
  }
}
