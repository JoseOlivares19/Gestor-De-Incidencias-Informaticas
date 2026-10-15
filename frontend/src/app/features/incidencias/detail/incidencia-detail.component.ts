import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { IncidenciaService } from '../../../core/services/incidencia.service';
import { UsuarioService } from '../../../core/services/usuario.service';
import { AuthService } from '../../../core/services/auth.service';
import { IncidenciaResponse } from '../../../core/models/incidencia.model';
import { UsuarioResponse } from '../../../core/models/usuario.model';

@Component({
  selector: 'app-incidencia-detail',
  standalone: true,
  imports: [RouterLink, DatePipe, ReactiveFormsModule],
  templateUrl: './incidencia-detail.component.html',
  styleUrl: './incidencia-detail.component.scss'
})
export class IncidenciaDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private svc = inject(IncidenciaService);
  private usuarioSvc = inject(UsuarioService);
  auth = inject(AuthService);
  private fb = inject(FormBuilder);

  incidencia = signal<IncidenciaResponse | null>(null);
  tecnicos = signal<UsuarioResponse[]>([]);
  loading = signal(true);
  saving = signal(false);
  error = signal('');
  successMsg = signal('');

  estadoForm = this.fb.group({ estado: ['', Validators.required] });
  solucionForm = this.fb.group({ descripcion: ['', Validators.required] });
  asignarForm = this.fb.group({ tecnicoId: ['', Validators.required] });

  get isAdmin() { return this.auth.isAdmin(); }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.svc.buscar(id).subscribe({
      next: data => { this.incidencia.set(data); this.loading.set(false); },
      error: () => { this.error.set('Incidencia no encontrada.'); this.loading.set(false); }
    });
    if (this.isAdmin) {
      this.usuarioSvc.listarTecnicos().subscribe({
        next: data => this.tecnicos.set(data)
      });
    }
  }

  cambiarEstado() {
    const inc = this.incidencia();
    if (!inc || this.estadoForm.invalid) return;
    this.saving.set(true);
    this.svc.cambiarEstado(inc.id, this.estadoForm.value as any).subscribe({
      next: data => { this.incidencia.set(data); this.saving.set(false); this.flash('Estado actualizado.'); },
      error: () => { this.saving.set(false); this.error.set('Error al cambiar estado.'); }
    });
  }

  resolver() {
    const inc = this.incidencia();
    if (!inc || this.solucionForm.invalid) return;
    this.saving.set(true);
    this.svc.resolver(inc.id, this.solucionForm.value as any).subscribe({
      next: data => { this.incidencia.set(data); this.saving.set(false); this.flash('Incidencia resuelta.'); },
      error: () => { this.saving.set(false); this.error.set('Error al registrar solución.'); }
    });
  }

  asignar() {
    const inc = this.incidencia();
    if (!inc || this.asignarForm.invalid) return;
    this.saving.set(true);
    const tecnicoId = Number(this.asignarForm.value.tecnicoId);
    this.svc.asignar(inc.id, { tecnicoId }).subscribe({
      next: data => { this.incidencia.set(data); this.saving.set(false); this.flash('Técnico asignado.'); },
      error: () => { this.saving.set(false); this.error.set('Error al asignar técnico.'); }
    });
  }

  private flash(msg: string) {
    this.successMsg.set(msg);
    setTimeout(() => this.successMsg.set(''), 3000);
  }
}
