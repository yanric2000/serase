import { AuthStatusView, useAuthStatus } from '@startup/features/auth';
import { Shared } from '@startup/shared';
import { SafeAreaView, Text, View } from 'react-native';

export function HomePage() {
  const { isAuthenticated, isLoading } = useAuthStatus();

  return (
    <SafeAreaView>
      <View>
        <Text testID="home-heading">OLÁ MUNDO</Text>
        <AuthStatusView isAuthenticated={isAuthenticated} isLoading={isLoading} />
        <Shared />
      </View>
    </SafeAreaView>
  );
}
