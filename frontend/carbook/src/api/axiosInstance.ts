import axios from 'axios';

const BASEURL = 'http://3.39.1.92:8080';

export const axiosAppFormInstance = axios.create({
  withCredentials: true,
  baseURL: BASEURL,
  timeout: 3000,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
    Accept: '*/*',
  },
});

export const axiosMultiFormInstance = axios.create({
  withCredentials: true,
  baseURL: BASEURL,
  timeout: 3000,
  headers: {
    'Content-Type': 'multipart/form-data;charset=UTF-8',
    Accept: '*/*',
  },
});
