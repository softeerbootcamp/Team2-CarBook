import {
  HomePage,
  LoginPage,
  PostDetailPage,
  PostPage,
  ProfilePage,
  SignupPage,
} from '@/pages';

export const routes = [
  { path: /^\/$/, element: HomePage },
  { path: /^\/login$/, element: LoginPage },
  { path: /^\/signup$/, element: SignupPage },
  { path: /^\/post\/[\d]+$/, element: PostDetailPage },
  { path: /^\/post\/[\d]+\/edit$/, element: PostPage },
  { path: /^\/post\/new$/, element: PostPage },
  { path: /^\/profile\/.+$/, element: ProfilePage },
];
