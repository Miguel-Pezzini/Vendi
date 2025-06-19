export default function loadPastPaths(route, activePage = null) {
  const oldPaths = Array.isArray(route.query.origin) ? route.query.origin : []

  const result = [...oldPaths]

  if (activePage) {
    result.push(activePage)
  }

  return result
}
