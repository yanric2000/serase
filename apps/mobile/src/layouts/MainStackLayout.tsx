import { createNativeStackNavigator } from '@react-navigation/native-stack';

import { HomePage } from '../pages/HomePage';

type MainStackParamList = {
  Home: undefined;
};

const Stack = createNativeStackNavigator<MainStackParamList>();

export function MainStackLayout() {
  return (
    <Stack.Navigator id="MainStack">
      <Stack.Screen
        name="Home"
        component={HomePage}
        options={{ title: 'Início' }}
      />
    </Stack.Navigator>
  );
}
