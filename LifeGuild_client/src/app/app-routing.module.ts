import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HabitsComponent } from './view3habits/habits.component';
import { NavbarComponent } from './navigation/navbar.component';
import { LibraryComponent } from './view2library/library.component';
import { RegisterComponent } from './view1main/register.component';
import { LoginComponent } from './view1main/login.component';
import { PetsComponent } from './view4pets/pets.component';
import { IndexComponent } from './view0index/index.component';
import { CreditsComponent } from './credits/credits.component';
import { LandingComponent } from './signedin/landing.component';
import { SearchresultComponent } from './view2library/searchresult.component';
import { ConfirmEmailComponent } from './view1main/confirm-email.component';
import { GameComponent } from './view6/game.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'confirm', component: ConfirmEmailComponent },
  {
    path: 'landing', component: LandingComponent,
    children: [
      { path: 'habits', component: HabitsComponent },
      { path: 'pets', component: PetsComponent },
      { path: 'library', component: LibraryComponent },
      { path: 'search', component: SearchresultComponent },
      { path: 'credits', component: CreditsComponent },
      { path: 'game', component: GameComponent },
    ]
  },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  // imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
