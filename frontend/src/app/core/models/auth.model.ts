export interface LoginRequest {
  correoCorporativo: string;
  password: string;
}

export interface JwtResponse {
  token: string;
  type: string;
  id: number;
  correoCorporativo: string;
  rol: string;
}

export type Rol = 'ADMINISTRADOR_TI' | 'TECNICO_SOPORTE' | 'COLABORADOR';
