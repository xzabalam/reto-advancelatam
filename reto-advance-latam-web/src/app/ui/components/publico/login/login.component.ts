import { MyErrorStateMatcher } from './../../../../domain/util/error-state-matcher';
import { FormControl, Validators } from '@angular/forms';
import { UsersService } from '../../../../domain/services/users/users.service';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  isLoading: boolean = false;
  usernameFormControl = new FormControl('', [
    Validators.required,
    Validators.min(3),
    Validators.max(20),
  ]);
  passwordFormControl = new FormControl('', [
    Validators.required,
    Validators.min(4),
    Validators.max(20),
  ]);
  errorMsg: string = '';
  matcher = new MyErrorStateMatcher();

  constructor(
    private userService: UsersService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  login() {
    this.isLoading = true;
    this.errorMsg = '';
    const user = {
      username: this.usernameFormControl.value,
      password: this.passwordFormControl.value,
    };

    this.userService.login(user).subscribe({
      next: (data) => {
        this.userService.authenticate(JSON.stringify(data));
        const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        this.router.navigateByUrl(returnUrl);
      },
      error: (e) => {
        this.errorMsg = e.message;
        this.userService.logout();
      },
    });
  }
}
