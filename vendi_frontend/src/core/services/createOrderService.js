export function createOrderService({ apiClient, productClient }) {
  async function hydrateOrderItem(item) {
    const product = item.product ? await productClient.hydrateProductSummary(item.product) : null

    return {
      ...item,
      product,
    }
  }

  async function hydrateOrder(order) {
    return {
      ...order,
      items: await Promise.all((order.items || []).map(hydrateOrderItem)),
    }
  }

  async function getOrders() {
    return apiClient.getAll('orders')
  }

  async function getOrderById(orderId) {
    const order = await apiClient.get('orders', orderId)
    return hydrateOrder(order)
  }

  return {
    getOrders,
    getOrderById,
  }
}
