import { Component } from '@angular/core';
import {ChannelService} from "../channel.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent {
  id!: number;
  profileImage!: string;
  firstName!: string;
  lastName!: string;
  fullName!: string;

  email!: string;
  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private matSnackBar: MatSnackBar,
              private userService: ChannelService){

      this.id = this.activatedRoute.snapshot.params['id'];
      console.log(this.id);
      this.userService.getAllChannelsById(this.id).subscribe(data => {
      this.profileImage = data.profileImage;
      this.firstName = data.firstName;
      this.lastName = data.lastName;
      this.fullName = data.fullName;
      this.email = data.emailAddress;

          const img = new Image();
          img.src = `data:image/jpeg;base64,${this.profileImage}`;
          this.profileImage = img.src;
    })

  }

  deleteChannel(id: number) {
    this.userService.deleteChannel(id).subscribe( () => {
      this.matSnackBar.open("Usuario removido com sucesso", "OK");

      this.router.navigate(['/featured']);

    })

  }

}


