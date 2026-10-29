import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';

import { getSession } from '../services/authService';
import { useAuthStore } from '../store/authStore';

export function useAuthStatus() {
  const setUserId = useAuthStore((s) => s.setUserId);

  const query = useQuery({
    queryKey: ['auth', 'session'],
    queryFn: getSession,
    staleTime: 60_000,
  });

  useEffect(() => {
    if (query.data) {
      setUserId(query.data.userId);
    }
  }, [query.data, setUserId]);

  return {
    userId: query.data?.userId ?? null,
    isLoading: query.isLoading,
    isAuthenticated: Boolean(query.data?.userId),
    refetch: query.refetch,
  };
}
