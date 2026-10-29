import { apiClient, setAuthToken } from '@startup/shared';

export type Session = {
  userId: string | null;
};

type LoginResponse = {
  token: string;
  userId: string;
};

export async function getSession(): Promise<Session> {
  try {
    return await apiClient.get<Session>('/auth/session');
  } catch {
    return { userId: null };
  }
}

export async function signIn(email: string, password: string): Promise<Session> {
  const result = await apiClient.post<LoginResponse>('/auth/login', { email, password });
  setAuthToken(result.token);
  return { userId: result.userId };
}

export async function register(email: string, password: string): Promise<void> {
  await apiClient.post('/auth/register', { email, password });
}
