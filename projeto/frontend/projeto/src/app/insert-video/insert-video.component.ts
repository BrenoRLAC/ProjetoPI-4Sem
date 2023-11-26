import { Component } from '@angular/core';
import {FileSystemFileEntry, NgxFileDropEntry} from "ngx-file-drop";
import {Router} from "@angular/router";
import {VideoService} from "../video.service";
import { take } from 'rxjs/operators';
@Component({
  selector: 'app-insert-video',
  templateUrl: './insert-video.component.html',
  styleUrls: ['./insert-video.component.css']
})
export class InsertVideoComponent {

  public files: NgxFileDropEntry[] = [];
  videoUrl: string = '';

  title: string = '';

  description: string = '';

  videoStatus!: number;

  fileUploaded: boolean = false;
  fileEntry: FileSystemFileEntry | undefined

  errorMessage: string = '';

  constructor(private videoService: VideoService, private router: Router) {

  }


  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {

// Is it a file?
      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileEntry.file((file: File) => {

// Here you can access the real file
          console.log(droppedFile.relativePath, file);


          this.fileUploaded = true;

        });
      } else {
// It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);


      }
    }
  }

  public fileOver(event:any){
    console.log(event);
  }

  public fileLeave(event:any){
    console.log(event);
  }

  uploadVideo() {
    if (this.fileEntry !== undefined) {
      this.fileEntry.file((file: File) => {
        this.videoService.registerVideo(file, this.videoUrl, this.title, this.description, this.videoStatus)
          .pipe(take(1))
          .subscribe((data: any) => {
            this.router.navigate(['/featured']);

          },
            (error: any) => {
              this.errorMessage = 'Erro ao enviar video. Valide o tamanho da URL do video que deve ser conter 11 e o status do video';
            });
      });
    }
  }



}


