export default function isAdmin() {
  return localStorage.getItem('roles').includes('ROLE_ADMIN')
}
