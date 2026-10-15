import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { IncidenciaService } from '../../core/services/incidencia.service';
import { EstadisticasResponse, IncidenciaResponse } from '../../core/models/incidencia.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, DatePipe],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  auth = inject(AuthService);
  incidenciaService = inject(IncidenciaService);

  incidencias = signal<IncidenciaResponse[]>([]);
  estadisticas = signal<EstadisticasResponse | null>(null);
  loading = signal(true);

  get user() { return this.auth.user(); }

  get recientes() {
    return this.incidencias().slice(0, 5);
  }

  get pendientes() {
    return this.incidencias().filter(i => i.estado === 'PENDIENTE').length;
  }
  get enProceso() {
    return this.incidencias().filter(i => i.estado === 'EN_PROCESO').length;
  }
  get resueltas() {
    return this.incidencias().filter(i => i.estado === 'RESUELTO').length;
  }

  ngOnInit() {
    this.incidenciaService.listar().subscribe({
      next: data => {
        this.incidencias.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });

    if (this.auth.isAdmin()) {
      this.incidenciaService.estadisticas().subscribe({
        next: data => this.estadisticas.set(data)
      });
    }
  }
}
