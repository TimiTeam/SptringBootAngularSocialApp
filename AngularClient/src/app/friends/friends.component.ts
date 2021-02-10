import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { UserPageService } from '../_services/user-page.service'
import { User } from '../model/user'

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {

  userId: string
  friends: User[]
  constructor(private userPageService: UserPageService, private activedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(param =>{
      this.userId = param['userId']
    })
    console.log(this.userId);
    this.userPageService.getFriends(this.userId).subscribe(data =>{
      this.friends = data
    })
  }

  removeFromFriend(friedId: string){
    this.userPageService.removeFriend(+this.userId, +friedId).subscribe(data =>{
      window.location.reload();
    })
  }
}
