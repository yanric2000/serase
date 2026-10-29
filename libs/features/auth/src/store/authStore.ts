import { setAuthToken } from '@startup/shared';
import { create } from 'zustand';

type AuthState = {
  userId: string | null;
  setUserId: (userId: string | null) => void;
  signOut: () => void;
};

export const useAuthStore = create<AuthState>((set) => ({
  userId: null,
  setUserId: (userId) => set({ userId }),
  signOut: () => {
    setAuthToken(null);
    set({ userId: null });
  },
}));
