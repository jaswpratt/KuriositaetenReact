import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  base: '/kuriositaeten-react/',
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://srv1839678.hstgr.cloud:8080/kuriositaeten-api-react',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})