import { defineConfig } from 'vite';
import * as path from 'path';

export default () =>
  defineConfig({
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src'),
      },
    },
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: `@import "./src/styles/color.scss";`,
        },
      },
    },
    server: {
      proxy: {
        '/api': {
          target: 'http://3.39.1.92:8080',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ''),
          secure: false,
          ws: true,
        },
      },
    },
  });
