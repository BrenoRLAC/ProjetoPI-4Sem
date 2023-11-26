import {Channel} from "./Channel";
import {Comment} from "./comment";
export interface Video {
  id: number;
  thumbnailUrl: string;
  videoUrl: string;
  title: string;
  description: string;
  comments: Comment[];
  channel: Channel;
  likes: number;
  disLikes: number;
  videoStatus: string;
  viewCount: number;
  channelName: string;


}
