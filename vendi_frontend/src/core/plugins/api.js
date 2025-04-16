import axios from "axios";
import { store } from "./store";

const BASE_URL = 'http://localhost:8080/'

const server = axios.create({
    baseURL: BASE_URL,
    timeout: 1000,
  });
  
  server.interceptors.request.use(
    (req) => {
        const token = localStorage.getItem("token");
        req.headers["Content-Type"] = "application/json"

        if (token) {
          req.headers["Authorization"] = `Bearer ${token}`;
        }
  
      store.commit("startLoading", req.url);
      return req;
    },
    (err) => Promise.reject(err?.response?.data || err)
  );

  server.interceptors.response.use(res => {
    store.commit('stopLoading', res.config.url)
     return res
}, 
    err => {
        store.commit('stopLoading', err.config.url)
        return Promise.reject(err.response.data)
    })
  
    async function getAll(resource, params = {}) {
        const response = await server.get(resource, {params});
        return response.data;
      }

      async function get(resource, id = null) {
        const response = await server.get(`${resource}/${id}`);
        return response.data;
      }
  
  
  async function create(resource, data) {
    const response = await server.post(resource, data);
    return response.data;
  }
  
  async function login(email, password) {
    const response = await server.post("auth/login", { email, password });
    localStorage.setItem("token", response.data.token);
    return response.data;
  }


const api = {
    login,
    get,
    getAll,
    create,
};

export default api;