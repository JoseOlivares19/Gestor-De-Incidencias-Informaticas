export interface UsuarioRequest {
  correoCorporativo: string;
  password: string;
  area: string;
  rol: string;
  activo: boolean;
}

export interface UsuarioResponse {
  id: number;
  correoCorporativo: string;
  area: string;
  rol: string;
  activo: boolean;
}
