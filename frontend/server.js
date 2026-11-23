const express = require('express');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 8080;

// Directorio compilado de la aplicación Angular (Angular 21 Application Builder)
const DIST_FOLDER = path.join(__dirname, 'dist', 'gestor-incidencias', 'browser');

// Servir archivos estáticos con caché controlado
app.use(express.static(DIST_FOLDER, {
  maxAge: '1y',
  setHeaders: (res, filePath) => {
    // No almacenar en caché index.html para permitir despliegues inmediatos
    if (filePath.endsWith('index.html')) {
      res.setHeader('Cache-Control', 'no-cache, no-store, must-revalidate');
    }
  }
}));

// Health check para dyno monitoring
app.get('/health', (req, res) => {
  res.status(200).json({ status: 'UP', timestamp: new Date().toISOString() });
});

// Enrutamiento SPA: cualquier ruta no estática redirige a index.html
app.get('*', (req, res) => {
  res.sendFile(path.join(DIST_FOLDER, 'index.html'));
});

app.listen(PORT, () => {
  console.log(`[Frontend] Gestor de Incidencias iniciado en puerto ${PORT}`);
  console.log(`[Frontend] Sirviendo desde: ${DIST_FOLDER}`);
});
