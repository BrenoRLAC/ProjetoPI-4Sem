import {Component, OnInit} from '@angular/core';

import {Channel} from "../Channel";
import {ChannelService} from "../channel.service";


@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent  implements OnInit {

  featuredSubscriptions: Array<Channel> = [];
  constructor(
    private channelService: ChannelService) {

  }
  ngOnInit() {

    this.channelService.getAllChannels().subscribe(response => {
      this.featuredSubscriptions = response;

      this.featuredSubscriptions.forEach(item => {
        const img = new Image();
        img.src = `data:image/jpeg;base64,${item.profileImage}`;
        item.profileImage = img.src;
      });
    });
  }
}
