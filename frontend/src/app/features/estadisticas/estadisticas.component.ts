import { Component, inject, OnInit, signal } from '@angular/core';
import { IncidenciaService } from '../../core/services/incidencia.service';
import { EstadisticasResponse } from '../../core/models/incidencia.model';

@Component({
  selector: 'app-estadisticas',
  standalone: true,
  imports: [],
  templateUrl: './estadisticas.component.html',
  styleUrl: './estadisticas.component.scss'
})
export class EstadisticasComponent implements OnInit {
  private svc = inject(IncidenciaService);

  stats = signal<EstadisticasResponse | null>(null);
  loading = signal(true);
  error = signal('');

  ngOnInit() {
    this.svc.estadisticas().subscribe({
      next: data => { this.stats.set(data); this.loading.set(false); },
      error: () => { this.error.set('Error cargando estadísticas.'); this.loading.set(false); }
    });
  }

  get categorias(): { nombre: string; valor: number }[] {
    const s = this.stats();
    if (!s?.porCategoria) return [];
    return Object.entries(s.porCategoria).map(([nombre, valor]) => ({ nombre, valor }));
  }

  get prioridades(): { nombre: string; valor: number }[] {
    const s = this.stats();
    if (!s?.porPrioridad) return [];
    return Object.entries(s.porPrioridad).map(([nombre, valor]) => ({ nombre, valor }));
  }
}

