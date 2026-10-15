import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { IncidenciaService } from '../../../core/services/incidencia.service';

@Component({
  selector: 'app-incidencia-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './incidencia-form.component.html',
  styleUrl: './incidencia-form.component.scss'
})
export class IncidenciaFormComponent {
  private fb = inject(FormBuilder);
  private svc = inject(IncidenciaService);
  private router = inject(Router);

  form = this.fb.group({
    titulo:       ['', [Validators.required, Validators.maxLength(150)]],
    descripcion:  ['', [Validators.required]],
    areaAfectada: ['', [Validators.required, Validators.maxLength(100)]],
    categoria:    ['', [Validators.required]],
    prioridad:    ['', [Validators.required]]
  });

  loading = false;
  errorMsg = '';
  successMsg = '';

  submit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading = true;
    this.errorMsg = '';

    this.svc.crear(this.form.value as any).subscribe({
      next: (inc) => {
        this.successMsg = `Incidencia ${inc.codigo} creada exitosamente.`;
        this.loading = false;
        setTimeout(() => this.router.navigate(['/incidencias', inc.id]), 1500);
      },
      error: () => {
        this.errorMsg = 'Error al crear la incidencia. Verifica los datos.';
        this.loading = false;
      }
    });
  }

  get f() { return this.form.controls; }
}
