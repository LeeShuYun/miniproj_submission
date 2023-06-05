import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FooterComponent } from './navigation/footer.component';
import { NavbarComponent } from './navigation/navbar.component';
import { BannerComponent } from './navigation/banner.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ServiceWorkerModule } from '@angular/service-worker';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { HabitsComponent } from './view3habits/habits.component';
import { CreditsComponent } from './credits/credits.component';
import { BearerTokenInterceptor, httpInterceptorProviders } from './_helper/auth.interceptor';
import { RegisterComponent } from './view1main/register.component';
import { LoginComponent } from './view1main/login.component';
import { PetsComponent } from './view4pets/pets.component';
import { IndexComponent } from './view0index/index.component';
import { HeroComponent } from './view0index/hero.component';
import { LibraryComponent } from './view2library/library.component';
import { SearchresultComponent } from './view2library/searchresult.component';
import { LandingComponent } from './signedin/landing.component';
import { CharacterComponent } from './account/character.component';
import { MaterialModule } from './material.module';
import { TimerComponent } from './extras/timer.component';
import { GameNotifComponent } from './extras/game-notif.component';
import { StatusComponent } from './extras/status.component';
import { TermsOfAgreementComponent } from './view1main/terms-of-agreement.component';
import { ConfirmEmailComponent } from './view1main/confirm-email.component';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { playerReducer } from './_ngrx_store/player.reducer';
import { characterReducer } from './_ngrx_store/character.reducer';
import { petReducer } from './_ngrx_store/pet.reducer';
import { counterReducer } from '../app/_ngrx_store/counter.reducer';
import { gameSectionReducer } from './_ngrx_store/game-section.redux';
import { StoreRouterConnectingModule, routerReducer } from '@ngrx/router-store';
import { GameComponent } from './view6/game.component';
import { JwtHelperService, JwtModule } from '@auth0/angular-jwt';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    NavbarComponent,
    BannerComponent,
    HabitsComponent,
    CreditsComponent,
    RegisterComponent,
    LoginComponent,
    PetsComponent,
    IndexComponent,
    HeroComponent,
    LibraryComponent,
    SearchresultComponent,
    LandingComponent,
    CharacterComponent,
    TimerComponent,
    GameNotifComponent,
    StatusComponent,
    TermsOfAgreementComponent,
    ConfirmEmailComponent,
    GameComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
    DragDropModule,
    EffectsModule.forRoot([]),
    StoreModule.forRoot({
      character: characterReducer,
      pet: petReducer,
      count: counterReducer,
      isGameSectionLocked: gameSectionReducer,
      router: routerReducer
    }),
    // Connects RouterModule with StoreModule, uses MinimalRouterStateSerializer by default
    StoreRouterConnectingModule.forRoot(), //ngrx
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('jwt');
        }
        ,
        allowedDomains: ["localhost:8080", "https://miniproj-server-production.up.railway.app"],
      }
    })
  ],
  // providers: [httpInterceptorProviders],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
