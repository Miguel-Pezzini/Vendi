function normalizeWishlistItem(item) {
  return {
    ...item,
    product: {
      ...item.product,
      isInWishList: true,
    },
  }
}

export function createWishlistService({ apiClient, productClient }) {
  async function hydrateWishlist(wishlist) {
    const items = await Promise.all(
      (wishlist.items || []).map(async (item) => ({
        ...normalizeWishlistItem(item),
        product: {
          ...(await productClient.hydrateProductSummary(item.product)),
          isInWishList: true,
        },
      }))
    )

    return {
      ...wishlist,
      items,
      totalItems: items.length,
    }
  }

  async function getWishlist() {
    const wishlist = await apiClient.get('wishlist')
    return hydrateWishlist(wishlist)
  }

  async function addItem(productId) {
    const wishlist = await apiClient.create('wishlist/items', { productId })
    return hydrateWishlist(wishlist)
  }

  async function removeItem(productId) {
    const wishlist = await apiClient.remove(`wishlist/items/${productId}`)
    return hydrateWishlist(wishlist)
  }

  return {
    getWishlist,
    addItem,
    removeItem,
  }
}
