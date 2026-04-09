export function resolveNavigation(to, session = {}) {
  const token = session.token
  const roles = session.roles || ''

  if (to.meta?.requiresAuth && !token) {
    return { path: '/login' }
  }

  if (to.meta?.requiresAdmin && !roles.includes('ROLE_ADMIN')) {
    return { path: '/home' }
  }

  return null
}

export function createNavigationGuard(storage = globalThis.localStorage) {
  return (to, from, next) => {
    const decision = resolveNavigation(to, {
      token: storage?.getItem('token'),
      roles: storage?.getItem('roles') || '',
    })

    if (decision) {
      next(decision)
      return
    }

    next()
  }
}
