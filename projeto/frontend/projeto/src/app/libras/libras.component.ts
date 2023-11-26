import { Component, OnInit } from '@angular/core';

declare global {
  interface Window {
    VLibras: any;
  }
}

@Component({
  selector: 'app-libras',
  templateUrl: './libras.component.html',
  styleUrls: ['./libras.component.css']
})
export class LibrasComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    this.loadVLibrasScript();
  }

  loadVLibrasScript(): void {
    function initializeVLibras() {
      if (window.VLibras) {
        new window.VLibras.Widget();
      } else {
        console.error('VLibras script not found.');
      }
    }

    function loadScript(url: string, callback: () => void) {
      const script = document.createElement('script');
      script.type = 'text/javascript';
      script.src = url;
      script.onload = callback;
      document.body.appendChild(script);
    }

    window.onload = () => {
      loadScript('https://vlibras.gov.br/app/vlibras-plugin.js', initializeVLibras);
    };
  }
}
