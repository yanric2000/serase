import React from 'react';
import { render } from '@testing-library/react-native';

jest.mock('@react-navigation/native', () => ({
  NavigationContainer: ({ children }: { children: React.ReactNode }) => children,
}));

jest.mock('../routes/RootNavigator', () => {
  const ReactMock = require('react');
  const { Text } = require('react-native');
  return {
    RootNavigator: () =>
      ReactMock.createElement(Text, { testID: 'home-heading' }, 'OLÁ MUNDO'),
  };
});

import App from './App';

test('renders home heading', () => {
  const { getByTestId } = render(<App />);
  expect(getByTestId('home-heading')).toHaveTextContent('OLÁ MUNDO');
});
