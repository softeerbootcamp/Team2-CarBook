import axios from 'axios';

const BASEURL = 'https://reqres.in';

export const axiosInstance = axios.create({
  withCredentials: true,
  baseURL: BASEURL,
});
