import { Component, OnInit } from '@angular/core';
import {AuthService} from '../_services/auth.service'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  form: any = {
    username: null,
    sex: null,
    email: null,
    password: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private service: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(): void{
    const { username, sex, email, password } = this.form
    console.log(this.form);
    this.service.register(username, sex, email, password).subscribe(
      data => {
        console.log(data)
        this.isSuccessful = true
        this.isSignUpFailed = false
      },
      err => {
        this.errorMessage = err.error.message
        this.isSignUpFailed = true
      }
    )
  }

}
