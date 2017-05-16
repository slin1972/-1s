import axios from 'axios';

let base = '';

export const getToken = () => {
    var user = sessionStorage.getItem('user');
    if (user) {
        user = JSON.parse(user);
        return user.token ;
    }};

export const requestLogin = params => { return axios.post(`${base}/api/v1/master/register`, params).then(res => res.data); };

export const getControlledList = params => { return axios.get(`${base}/api/v1/controlled_list`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };

export const addScript = params => { return axios.post(`${base}/api/v1/script`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };

export const getScriptList = params => { return axios.get(`${base}/api/v1/script_list`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };

export const downloadControlled = () => {return axios.get(`${base}/api/v1/downloadControlled` , { headers: {"token": getToken()}}).then(res => res.data); };


export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };

export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };

export const editUser = params => { return axios.get(`${base}/user/edit`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };

export const addUser = params => { return axios.get(`${base}/user/add`, { params: params, headers: {"token": getToken()}}).then(res => res.data); };