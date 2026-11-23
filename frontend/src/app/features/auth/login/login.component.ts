import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  form = this.fb.group({
    correoCorporativo: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  loading = false;
  coldStartActive = false;
  errorMsg = '';
  private coldStartTimer: any = null;

  readonly cuentasDemo = [
    {
      rol: 'Administrador TI',
      correo: 'admin@empresa.com',
      icono: '👨‍💼',
      descripcion: 'Acceso total y reportes'
    },
    {
      rol: 'Técnico Soporte',
      correo: 'tecnico@empresa.com',
      icono: '🛠️',
      descripcion: 'Atención y solución de tickets'
    },
    {
      rol: 'Colaborador',
      correo: 'colaborador@empresa.com',
      icono: '👤',
      descripcion: 'Reporte y seguimiento de incidencias'
    }
  ];

  get email() { return this.form.get('correoCorporativo')!; }
  get pass()  { return this.form.get('password')!; }

  ingresarComo(correo: string) {
    this.form.patchValue({
      correoCorporativo: correo,
      password: 'password123'
    });
    this.submit();
  }

  submit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading = true;
    this.coldStartActive = false;
    this.errorMsg = '';

    // Si la respuesta demora más de 3 segundos (despertar de Eco Dyno en Heroku), se muestra aviso
    this.coldStartTimer = setTimeout(() => {
      if (this.loading) {
        this.coldStartActive = true;
      }
    }, 3000);

    this.auth.login(this.form.value as any).subscribe({
      next: () => {
        if (this.coldStartTimer) clearTimeout(this.coldStartTimer);
        this.router.navigate(['/dashboard']);
      },
      error: err => {
        if (this.coldStartTimer) clearTimeout(this.coldStartTimer);
        this.coldStartActive = false;
        this.errorMsg = err.status === 401
          ? 'Credenciales incorrectas. Verifica tu correo y contraseña.'
          : 'Error de conexión con el servidor. Si el servidor estaba suspendido, intenta de nuevo en unos segundos.';
        this.loading = false;
      }
    });
  }
}

