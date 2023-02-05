import axios from 'axios';

const BASEURL = '';

export const axiosAppFormInstance = axios.create({
  withCredentials: true,
  baseURL: BASEURL,
  timeout: 3000,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded;',
    Accept: '*/*',
  },
});

export const axiosMultiFormInstance = axios.create({
  withCredentials: true,
  baseURL: BASEURL,
  timeout: 3000,
  headers: {
    'Content-Type': 'multipart/form-data;',
    Accept: '*/*',
  },
});
