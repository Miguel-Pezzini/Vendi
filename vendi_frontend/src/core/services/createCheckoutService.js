export function createCheckoutService({ apiClient }) {
  async function createSession(payload) {
    return apiClient.create('checkout/session', payload)
  }

  async function getSessionStatus(sessionId) {
    return apiClient.get('checkout/session', sessionId)
  }

  return {
    createSession,
    getSessionStatus,
  }
}
