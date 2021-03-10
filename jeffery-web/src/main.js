import HMR from '@roxi/routify/hmr';
import App from './App.svelte';

window.__VERSION__ = __env.VERSION;
window.__COMMIT__ = __env.COMMIT;
window.__BUILT_AT__ = new Date().toUTCString();

const app = HMR(App, { target: document.body }, 'routify-app');

export default app;
