import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Channel} from "./Channel";

@Injectable({
  providedIn: 'root'
})
export class ChannelService {


  constructor(private httpClient:HttpClient) { }

  registerChannel(file: any, firstName: string, lastName: string, fullName: string, email: string, password: any)  {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('firstName', firstName);
    formData.append('lastName', lastName);
    formData.append('fullName', fullName);
    formData.append('email', email);
    formData.append('password', password);

    return this.httpClient.post<any>("http://localhost:8080/api/channel", formData)
  }

  getAllChannels(): Observable<Array<Channel>>{
        return this.httpClient.get<Array<Channel>>("http://localhost:8080/api/channel")
    }

  getAllChannelsById(id: number): Observable<Channel>{
    const params = new HttpParams().set('?id', id);
    return this.httpClient.get<Channel>("http://localhost:8080/api/channel/id/" + params)
  }

  deleteChannel(id: number) {
    const params = new HttpParams().set('?id', id);
    return this.httpClient.delete("http://localhost:8080/api/channel/id/" + params)

  }

}
