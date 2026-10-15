import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioService } from '../../core/services/usuario.service';
import { UsuarioResponse } from '../../core/models/usuario.model';

@Component({
  selector: 'app-usuarios-list',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './usuarios-list.component.html',
  styleUrl: './usuarios-list.component.scss'
})
export class UsuariosListComponent implements OnInit {
  private svc = inject(UsuarioService);
  private fb = inject(FormBuilder);

  usuarios = signal<UsuarioResponse[]>([]);
  loading = signal(true);
  saving = signal(false);
  errorMsg = signal('');
  successMsg = signal('');
  showForm = false;
  editingId: number | null = null;

  form = this.fb.group({
    correoCorporativo: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    area: ['', Validators.required],
    rol: ['', Validators.required],
    activo: [true, Validators.required]
  });

  ngOnInit() { this.cargar(); }

  cargar() {
    this.svc.listar().subscribe({
      next: data => { this.usuarios.set(data); this.loading.set(false); },
      error: () => { this.errorMsg.set('Error cargando usuarios.'); this.loading.set(false); }
    });
  }

  abrirCrear() {
    this.editingId = null;
    this.form.reset({ activo: true });
    this.form.get('password')!.setValidators([Validators.required, Validators.minLength(6)]);
    this.form.get('password')!.updateValueAndValidity();
    this.showForm = true;
  }

  abrirEditar(u: UsuarioResponse) {
    this.editingId = u.id;
    this.form.patchValue({ correoCorporativo: u.correoCorporativo, area: u.area, rol: u.rol, activo: u.activo, password: '' });
    this.form.get('password')!.clearValidators();
    this.form.get('password')!.updateValueAndValidity();
    this.showForm = true;
  }

  cancelar() { this.showForm = false; this.editingId = null; }

  guardar() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.saving.set(true);
    const data: any = { ...this.form.value };
    if (this.editingId !== null && !data.password) delete data.password;

    const op = this.editingId !== null
      ? this.svc.actualizar(this.editingId, data)
      : this.svc.crear(data);

    op.subscribe({
      next: () => {
        this.saving.set(false);
        this.showForm = false;
        this.flash('Usuario guardado exitosamente.');
        this.cargar();
      },
      error: (err) => {
        this.saving.set(false);
        this.errorMsg.set(err.error?.message || 'Error al guardar usuario.');
      }
    });
  }

  eliminar(id: number) {
    if (!confirm('¿Deseas desactivar este usuario?')) return;
    this.svc.eliminar(id).subscribe({
      next: () => { this.flash('Usuario desactivado.'); this.cargar(); },
      error: () => this.errorMsg.set('Error al desactivar.')
    });
  }

  private flash(msg: string) {
    this.successMsg.set(msg);
    setTimeout(() => this.successMsg.set(''), 3500);
  }

  get f() { return this.form.controls; }
}
