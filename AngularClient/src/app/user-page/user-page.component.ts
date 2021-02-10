import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { UserPageService } from '../_services/user-page.service'

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  userId: string

  constructor(private userPageService: UserPageService, private activedRoute: ActivatedRoute) { }

  ngOnInit(): void {
     this.activedRoute.paramMap.subscribe(param =>{
       this.userId = param['userId']
     })
     console.log(this.userId);
     
  }
}
