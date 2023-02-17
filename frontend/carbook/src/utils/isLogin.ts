import { basicAPI } from '@/api';

export default async function isLogin() {
  const res = await basicAPI.get('/api/isLogin');
  const { login } = res.data;

  return login;
}
