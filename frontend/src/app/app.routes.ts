import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'incidencias',
    canActivate: [authGuard],
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./features/incidencias/list/incidencias-list.component').then(m => m.IncidenciasListComponent)
      },
      {
        path: 'nueva',
        loadComponent: () =>
          import('./features/incidencias/form/incidencia-form.component').then(m => m.IncidenciaFormComponent)
      },
      {
        path: ':id',
        loadComponent: () =>
          import('./features/incidencias/detail/incidencia-detail.component').then(m => m.IncidenciaDetailComponent)
      }
    ]
  },
  {
    path: 'usuarios',
    loadComponent: () =>
      import('./features/usuarios/usuarios-list.component').then(m => m.UsuariosListComponent),
    canActivate: [authGuard, adminGuard]
  },
  {
    path: 'estadisticas',
    loadComponent: () =>
      import('./features/estadisticas/estadisticas.component').then(m => m.EstadisticasComponent),
    canActivate: [authGuard, adminGuard]
  },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: 'dashboard' }
];
