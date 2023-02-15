import axios from 'axios';

const BASEURL = '';

const options = {
  withCredentials: true,
  baseURL: BASEURL,
  timeout: 3000,
};

export const basicAPI = axios.create({
  ...options,
  headers: {
    'Content-Type': 'application/json',
    Accept: '*/*',
  },
});

export const formAPI = axios.create({
  ...options,
  headers: {
    Accept: '*/*',
  },
});
