import axios from 'axios';

const BASEURL = '';

export const axiosInstance = axios.create({
  withCredentials: true,
  baseURL: BASEURL,
});
