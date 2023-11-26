import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {InsertVideoComponent} from "./insert-video/insert-video.component";
import {VideoDetailComponent} from "./video-detail/video-detail.component";
import {HomeComponent} from "./home/home.component";
import {SubscriptionsComponent} from "./subscriptions/subscriptions.component";
import {FeaturedComponent} from "./featured/featured.component";
import {UserDetailComponent} from "./user-detail/user-detail.component";
import {InsertUserComponent} from "./insert-user/insert-user.component";
import {ViewVideosComponent} from "./view-videos/view-videos.component";

const routes: Routes = [
  {
    path: '', component: HomeComponent,
    children: [
      {
        path: 'featured', component: FeaturedComponent,
      },
      {
        path: 'channels', component: SubscriptionsComponent,
      },
      {
        path: 'view-videos', component: ViewVideosComponent,
      },
    ]
  },

  {path: 'insert-video', component: InsertVideoComponent},

  {
    path: 'video-details/:id', component: VideoDetailComponent,
  },
  {
    path: 'user-details/:id', component: UserDetailComponent,
  },
  {
    path: 'insert-user', component: InsertUserComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

