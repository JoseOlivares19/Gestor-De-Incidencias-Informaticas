import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IncidenciaService } from '../../../core/services/incidencia.service';
import { AuthService } from '../../../core/services/auth.service';
import { IncidenciaResponse } from '../../../core/models/incidencia.model';

@Component({
  selector: 'app-incidencias-list',
  standalone: true,
  imports: [RouterLink, DatePipe, FormsModule],
  templateUrl: './incidencias-list.component.html',
  styleUrl: './incidencias-list.component.scss'
})
export class IncidenciasListComponent implements OnInit {
  private svc = inject(IncidenciaService);
  auth = inject(AuthService);

  incidencias = signal<IncidenciaResponse[]>([]);
  loading = signal(true);
  error = signal('');
  filtroEstado = '';
  filtroTexto = '';

  get filtradas(): IncidenciaResponse[] {
    return this.incidencias().filter(i => {
      const matchEstado = !this.filtroEstado || i.estado === this.filtroEstado;
      const matchTexto = !this.filtroTexto ||
        i.titulo.toLowerCase().includes(this.filtroTexto.toLowerCase()) ||
        i.codigo.toLowerCase().includes(this.filtroTexto.toLowerCase());
      return matchEstado && matchTexto;
    });
  }

  ngOnInit() {
    this.svc.listar().subscribe({
      next: data => { this.incidencias.set(data); this.loading.set(false); },
      error: () => { this.error.set('Error cargando incidencias.'); this.loading.set(false); }
    });
  }
}
