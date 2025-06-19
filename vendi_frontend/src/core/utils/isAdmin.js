export default function isAdmin() {
  console.log(localStorage.getItem('roles').includes('ROLE_ADMIN'))
  return localStorage.getItem('roles').includes('ROLE_ADMIN')
}
