import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { Text, View } from 'react-native';

type AuthStackParamList = {
  SignIn: undefined;
};

const Stack = createNativeStackNavigator<AuthStackParamList>();

function SignInPlaceholder() {
  return (
    <View>
      <Text>Sign in (placeholder)</Text>
    </View>
  );
}

/** Stack reservado para fluxo público (login/cadastro). */
export function AuthStackLayout() {
  return (
    <Stack.Navigator id="AuthStack">
      <Stack.Screen name="SignIn" component={SignInPlaceholder} options={{ title: 'Entrar' }} />
    </Stack.Navigator>
  );
}
