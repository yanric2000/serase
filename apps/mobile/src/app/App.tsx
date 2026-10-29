import { NavigationContainer } from '@react-navigation/native';
import { StatusBar } from 'react-native';

import { RootNavigator } from '../routes/RootNavigator';
import { AppProviders } from './providers';

import { GoogleSignin } from '@react-native-google-signin/google-signin'; // <--- Importar
import { env } from '@startup/shared';

GoogleSignin.configure({
  webClientId: env.googleWebClientId,
  offlineAccess: true,
});

export const App = () => {
  return (
    <AppProviders>
      <NavigationContainer>
        <StatusBar barStyle="dark-content" />
        <RootNavigator />
      </NavigationContainer>
    </AppProviders>
  );
};

export default App;
