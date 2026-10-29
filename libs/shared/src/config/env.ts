export const env = {
  apiBaseUrl: process.env.EXPO_PUBLIC_API_URL ?? 'http://localhost:8080',
  googleWebClientId:
    process.env['EXPO_PUBLIC_GOOGLE_WEB_CLIENT_ID'] ??
    '113592460562-dkq6gaptsroqrdupbf72a82jp82bvp3m.apps.googleusercontent.com',
} as const;
