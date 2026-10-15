import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { JwtResponse, LoginRequest } from '../models/auth.model';

const TOKEN_KEY = 'gi_token';
const USER_KEY = 'gi_user';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private _user = signal<JwtResponse | null>(this._loadUser());
  readonly user = this._user.asReadonly();

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${environment.apiUrl}/auth/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem(TOKEN_KEY, response.token);
        localStorage.setItem(USER_KEY, JSON.stringify(response));
        this._user.set(response);
      })
    );
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this._user.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  hasRole(rol: string): boolean {
    return this._user()?.rol === rol;
  }

  isAdmin(): boolean {
    return this.hasRole('ADMINISTRADOR_TI');
  }

  private _loadUser(): JwtResponse | null {
    const raw = localStorage.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  }
}
