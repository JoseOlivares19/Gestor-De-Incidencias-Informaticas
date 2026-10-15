export type EstadoIncidencia = 'PENDIENTE' | 'EN_PROCESO' | 'RESUELTO';
export type CategoriaIncidencia = 'HARDWARE' | 'SOFTWARE' | 'RED' | 'TELEFONIA_IP';
export type PrioridadIncidencia = 'ALTA' | 'MEDIA' | 'BAJA';

export interface IncidenciaRequest {
  titulo: string;
  descripcion: string;
  areaAfectada: string;
  categoria: CategoriaIncidencia;
  prioridad: PrioridadIncidencia;
}

export interface IncidenciaResponse {
  id: number;
  codigo: string;
  titulo: string;
  descripcion: string;
  areaAfectada: string;
  estado: EstadoIncidencia;
  categoria: CategoriaIncidencia;
  prioridad: PrioridadIncidencia;
  fechaCreacion: string;
  usuarioReportadorId: number;
  usuarioReportadorCorreo: string;
  tecnicoAsignadoId: number | null;
  tecnicoAsignadoCorreo: string | null;
}

export interface AsignarTecnicoRequest {
  tecnicoId: number;
}

export interface EstadoRequest {
  estado: EstadoIncidencia;
}

export interface SolucionRequest {
  descripcion: string;
}

export interface EstadisticasResponse {
  totalIncidencias: number;
  pendientes: number;
  enProceso: number;
  resueltas: number;
  porCategoria: { [key: string]: number };
  porPrioridad: { [key: string]: number };
}
