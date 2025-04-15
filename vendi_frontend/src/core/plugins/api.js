import axios from "axios";
import { store } from "./store";

const BASE_URL = 'http://localhost:8080/'

const server = axios.create({
    baseURL: BASE_URL,
    timeout: 1000,
    headers: {"Content-Type": "application/json" }
});

async function getAll(resource, params) {
    const response = await server.get(`${BASE_URL}${resource}`, params)

    return response.data
}

async function create(resource, data) {
    const response = await server.post(`${BASE_URL}${resource}`, data);

    return response.data
}

async function login(email, password) {
    const response = await server.post(`${BASE_URL}auth/login`, {email, password});

    return response.data
}

server.interceptors.request.use(req => {
    store.commit('startLoading', req.url)
    return req
}, 
    err => Promise.reject(err.response.data))

server.interceptors.response.use(res => {
    store.commit('stopLoading', res.config.url)
     return res
}, 
    err => {
        store.commit('stopLoading', err.config.url)
        return Promise.reject(err.response.data)
    })

const api = {
    login,
    getAll,
    create,
};

export default api;