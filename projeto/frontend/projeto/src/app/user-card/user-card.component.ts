import {Component, Input} from '@angular/core';
import {Channel} from "../Channel";

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent {
  @Input() channel!: Channel;

}
