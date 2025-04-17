import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideKeycloak, withAutoRefreshToken, AutoRefreshTokenService, UserActivityService, createInterceptorCondition, IncludeBearerTokenCondition, includeBearerTokenInterceptor, INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG } from 'keycloak-angular';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

export const provideKeycloakAngular = () =>
  provideKeycloak({
    config: {
      url: 'http://localhost:8080',
      realm: 'proptech',
      clientId: 'loans-frontend'
    },
    initOptions: {
      onLoad: 'login-required'
    },
    features: [
      withAutoRefreshToken({
        onInactivityTimeout: 'logout',
        sessionTimeout: 60000
      })
    ],
    providers: [AutoRefreshTokenService, UserActivityService]
  });

const interceptorCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
  urlPattern: /^(http:\/\/localhost:8081)(\/.*)?$/i,
  bearerPrefix: 'Bearer'
});

export const appConfig: ApplicationConfig = {
  providers: [
    provideKeycloakAngular(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    {
      provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
      useValue: [interceptorCondition]
    },
    provideHttpClient(withInterceptors([includeBearerTokenInterceptor]))]
};
