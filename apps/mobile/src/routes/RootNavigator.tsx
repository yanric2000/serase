import { createNativeStackNavigator } from '@react-navigation/native-stack';

import { MainStackLayout } from '../layouts/MainStackLayout';
import type { RootStackParamList } from './types';

const Stack = createNativeStackNavigator<RootStackParamList>();

export function RootNavigator() {
  return (
    <Stack.Navigator id="RootStack" screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Main" component={MainStackLayout} />
    </Stack.Navigator>
  );
}
