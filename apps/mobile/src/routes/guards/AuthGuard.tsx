import { PropsWithChildren, ReactNode } from 'react';
import { Text } from 'react-native';

import { useAuthStore } from '@startup/features/auth';

type AuthGuardProps = PropsWithChildren<{
  fallback?: ReactNode;
}>;

export function AuthGuard({ children, fallback }: AuthGuardProps) {
  const userId = useAuthStore((s) => s.userId);

  if (!userId) {
    return (
      fallback ?? <Text testID="auth-guard">Faça login para continuar.</Text>
    );
  }

  return children;
}
