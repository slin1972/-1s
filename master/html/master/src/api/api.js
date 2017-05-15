import axios from 'axios';

let base = '';

export const requestLogin = params => { return axios.post(`${base}/api/v1/master/register`, params).then(res => res.data); };

export const getControlledList = params => { return axios.get(`${base}/api/v1/controlled_list`, { params: params }).then(res => res.data); };


export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }).then(res => res.data); };

export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params }).then(res => res.data); };

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }).then(res => res.data); };

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }).then(res => res.data); };

export const editUser = params => { return axios.get(`${base}/user/edit`, { params: params }).then(res => res.data); };

export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }).then(res => res.data); };