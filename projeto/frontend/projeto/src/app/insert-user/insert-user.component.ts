import { Component } from '@angular/core';
import {FileSystemDirectoryEntry, FileSystemFileEntry, NgxFileDropEntry} from "ngx-file-drop";
import {Router} from "@angular/router";
import {ChannelService} from "../channel.service";

@Component({
  selector: 'app-insert-user',
  templateUrl: './insert-user.component.html',
  styleUrls: ['./insert-user.component.css']
})
export class InsertUserComponent {

  public files: NgxFileDropEntry[] = [];

  firstName: string;
  lastName: string;
  fullName: string;
  email: string;
 password: any;

  fileUploaded:boolean = false;
  fileEntry: FileSystemFileEntry | undefined;

  constructor(private channelService: ChannelService, private router: Router){
    this.firstName = '';
    this.lastName = '';
    this.fullName = '';
    this.email = '';
    this.password = '';
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

  uploadChannel() {
    if (this.fileEntry !== undefined) {
      this.fileEntry.file((file: File) => {
        this.channelService.registerChannel(file, this.firstName, this.lastName, this.fullName, this.email, this.password).subscribe(data => {
          this.router.navigate([`/channels`]);
        });
      });
    }

  }
}
