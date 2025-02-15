import axios from "axios";
import { store } from "./store";

const BASE_URL = 'http://localhost:8080/'

const server = axios.create({
    baseURL: BASE_URL,
    timeout: 1000,
    headers: {"Content-Type": "application/json" }
});



async function carregar(resource, data) {
    const response = await server.post(`${BASE_URL}${resource}`, data);

    return response.data
}

server.interceptors.request.use(req => {
    store.commit('startLoading', req.url)
    return req
}, 
    err => Promise.reject(err))

server.interceptors.response.use(res => {
    store.commit('stopLoading', res.config.url)
     return res
}, 
    err => {
        store.commit('stopLoading', err.config.url)
    })

const api = {
    carregar,
};

export default api;