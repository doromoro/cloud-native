import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173, // 개발 서버 포트
    host: '0.0.0.0', // 모든 네트워크 인터페이스에서 접근 가능
    proxy: {
      '/api': {
        target: 'http://localhost:8084', // 백엔드 API 서버 URL
        changeOrigin: true, // Origin 헤더 변경
        secure: false, // HTTPS 인증서 검증 무시 (테스트 환경에서만 사용)
      }
    }
  },
})

