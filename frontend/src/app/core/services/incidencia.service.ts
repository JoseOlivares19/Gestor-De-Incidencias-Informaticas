import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  AsignarTecnicoRequest,
  EstadisticasResponse,
  EstadoRequest,
  IncidenciaRequest,
  IncidenciaResponse,
  SolucionRequest
} from '../models/incidencia.model';

@Injectable({ providedIn: 'root' })
export class IncidenciaService {
  private base = `${environment.apiUrl}/incidencias`;

  constructor(private http: HttpClient) {}

  crear(data: IncidenciaRequest): Observable<IncidenciaResponse> {
    return this.http.post<IncidenciaResponse>(this.base, data);
  }

  listar(): Observable<IncidenciaResponse[]> {
    return this.http.get<IncidenciaResponse[]>(this.base);
  }

  buscar(id: number): Observable<IncidenciaResponse> {
    return this.http.get<IncidenciaResponse>(`${this.base}/${id}`);
  }

  asignar(id: number, data: AsignarTecnicoRequest): Observable<IncidenciaResponse> {
    return this.http.put<IncidenciaResponse>(`${this.base}/${id}/asignar`, data);
  }

  cambiarEstado(id: number, data: EstadoRequest): Observable<IncidenciaResponse> {
    return this.http.put<IncidenciaResponse>(`${this.base}/${id}/estado`, data);
  }

  resolver(id: number, data: SolucionRequest): Observable<IncidenciaResponse> {
    return this.http.post<IncidenciaResponse>(`${this.base}/${id}/solucion`, data);
  }

  estadisticas(): Observable<EstadisticasResponse> {
    return this.http.get<EstadisticasResponse>(`${this.base}/estadisticas`);
  }
}
