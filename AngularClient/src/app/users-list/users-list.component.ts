import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service'
import { UserPageService } from '../_services/user-page.service'
import { TokenStorageService } from '../_services/token-storage.service'
import { User } from '../model/user'

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {

  users: User[]

  constructor(private userService: UserService, private userPageService: UserPageService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe(users => {
      this.users = users
    })
  }

  addToFriends(friendId: number){
    console.log(friendId);
    var curUser =  this.tokenStorage.getUser()
    this.userPageService.addToFried(curUser.userId, friendId)
    .subscribe(data =>{
      console.log(data);
    }, error => {
      console.log(error);
    })
  }
}
