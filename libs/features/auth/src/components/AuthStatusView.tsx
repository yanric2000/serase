import { ActivityIndicator, Text } from 'react-native-paper';

type AuthStatusViewProps = {
  isAuthenticated: boolean;
  isLoading: boolean;
};

export function AuthStatusView({ isAuthenticated, isLoading }: AuthStatusViewProps) {
  if (isLoading) {
    return <ActivityIndicator testID="auth-status-loading" animating={true} />;
  }

  return (
    <Text testID="auth-status" variant="bodyMedium">
      {isAuthenticated ? 'Autenticado' : 'Convidado'}
    </Text>
  );
}
