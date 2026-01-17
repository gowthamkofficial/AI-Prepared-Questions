# FORMS ANSWERS

---

## 55. What are Template-driven forms?

### Answer:
- Forms built using **directives in templates**
- Use **ngModel** for two-way data binding
- **Simpler** for basic forms
- Validation through **HTML5 attributes** and directives
- Asynchronous by nature

### Theoretical Keywords:
**ngModel**, **Two-way binding**, **FormsModule**, **Template directives**,  
**HTML5 validation**, **Simple forms**, **Declarative**

### Example:
```typescript
// app.module.ts
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [FormsModule]
})
export class AppModule { }

// Component
@Component({
  selector: 'app-login',
  template: `
    <form #loginForm="ngForm" (ngSubmit)="onSubmit(loginForm)">
      <div class="form-group">
        <label for="email">Email</label>
        <input 
          type="email" 
          id="email"
          name="email"
          [(ngModel)]="user.email"
          required
          email
          #emailField="ngModel"
        >
        <div *ngIf="emailField.invalid && emailField.touched" class="error">
          <span *ngIf="emailField.errors?.['required']">Email is required</span>
          <span *ngIf="emailField.errors?.['email']">Invalid email format</span>
        </div>
      </div>
      
      <div class="form-group">
        <label for="password">Password</label>
        <input 
          type="password" 
          id="password"
          name="password"
          [(ngModel)]="user.password"
          required
          minlength="6"
          #passwordField="ngModel"
        >
        <div *ngIf="passwordField.invalid && passwordField.touched" class="error">
          <span *ngIf="passwordField.errors?.['required']">Password is required</span>
          <span *ngIf="passwordField.errors?.['minlength']">
            Min {{ passwordField.errors?.['minlength'].requiredLength }} characters
          </span>
        </div>
      </div>
      
      <button type="submit" [disabled]="loginForm.invalid">Login</button>
      
      <pre>Form Valid: {{ loginForm.valid }}</pre>
      <pre>Form Value: {{ loginForm.value | json }}</pre>
    </form>
  `
})
export class LoginComponent {
  user = {
    email: '',
    password: ''
  };
  
  onSubmit(form: NgForm): void {
    if (form.valid) {
      console.log('Form submitted:', this.user);
    }
  }
}
```

---

## 56. What are Reactive forms?

### Answer:
- Forms built **programmatically** in component class
- Use **FormGroup**, **FormControl**, **FormArray**
- **More control** and flexibility
- **Synchronous** and easier to test
- Validation in component, not template

### Theoretical Keywords:
**FormGroup**, **FormControl**, **ReactiveFormsModule**, **Programmatic**,  
**Synchronous**, **Testable**, **Validators**

### Example:
```typescript
// app.module.ts
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [ReactiveFormsModule]
})
export class AppModule { }

// Component
@Component({
  selector: 'app-registration',
  template: `
    <form [formGroup]="registrationForm" (ngSubmit)="onSubmit()">
      <div class="form-group">
        <label>Email</label>
        <input type="email" formControlName="email">
        <div *ngIf="email.invalid && email.touched" class="error">
          <span *ngIf="email.errors?.['required']">Email is required</span>
          <span *ngIf="email.errors?.['email']">Invalid email</span>
        </div>
      </div>
      
      <div class="form-group">
        <label>Password</label>
        <input type="password" formControlName="password">
      </div>
      
      <div class="form-group">
        <label>Confirm Password</label>
        <input type="password" formControlName="confirmPassword">
        <div *ngIf="registrationForm.errors?.['passwordMismatch']" class="error">
          Passwords don't match
        </div>
      </div>
      
      <div formGroupName="address">
        <h4>Address</h4>
        <input formControlName="street" placeholder="Street">
        <input formControlName="city" placeholder="City">
        <input formControlName="zip" placeholder="ZIP">
      </div>
      
      <button type="submit" [disabled]="registrationForm.invalid">Register</button>
    </form>
  `
})
export class RegistrationComponent implements OnInit {
  registrationForm!: FormGroup;
  
  constructor(private fb: FormBuilder) { }
  
  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      address: this.fb.group({
        street: [''],
        city: ['', Validators.required],
        zip: ['', Validators.pattern(/^\d{5}$/)]
      })
    }, {
      validators: this.passwordMatchValidator
    });
  }
  
  get email() { return this.registrationForm.get('email')!; }
  
  passwordMatchValidator(form: FormGroup): ValidationErrors | null {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordMismatch: true };
  }
  
  onSubmit(): void {
    if (this.registrationForm.valid) {
      console.log(this.registrationForm.value);
    }
  }
}
```

---

## 57. Difference between Template-driven and Reactive forms?

### Answer:

| Feature | Template-driven | Reactive |
|---------|-----------------|----------|
| **Module** | FormsModule | ReactiveFormsModule |
| **Form model** | Directives create | Explicit in class |
| **Data binding** | Two-way (ngModel) | Form control binding |
| **Validation** | Directives/HTML5 | Validators in class |
| **Testability** | Harder (async) | Easier (sync) |
| **Flexibility** | Less | More |
| **Best for** | Simple forms | Complex forms |
| **Dynamic** | Harder | Easy (FormArray) |

### Example:
```typescript
// Template-driven
@Component({
  template: `
    <input [(ngModel)]="name" required #nameField="ngModel">
    <span *ngIf="nameField.invalid">Required</span>
  `
})
export class TemplateComponent {
  name = '';
}

// Reactive
@Component({
  template: `
    <input [formControl]="nameControl">
    <span *ngIf="nameControl.invalid">Required</span>
  `
})
export class ReactiveComponent {
  nameControl = new FormControl('', Validators.required);
}
```

---

## 58. What is FormGroup and FormControl?

### Answer:
- **FormControl**: Single input field
- **FormGroup**: Collection of controls
- **FormArray**: Dynamic array of controls

### Example:
```typescript
@Component({
  template: `
    <form [formGroup]="profileForm">
      <!-- FormControl -->
      <input formControlName="firstName">
      <input formControlName="lastName">
      
      <!-- Nested FormGroup -->
      <div formGroupName="address">
        <input formControlName="street">
        <input formControlName="city">
      </div>
      
      <!-- FormArray -->
      <div formArrayName="phones">
        <div *ngFor="let phone of phones.controls; let i = index">
          <input [formControlName]="i">
          <button (click)="removePhone(i)">Remove</button>
        </div>
        <button (click)="addPhone()">Add Phone</button>
      </div>
    </form>
  `
})
export class ProfileComponent {
  profileForm = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl(''),
    address: new FormGroup({
      street: new FormControl(''),
      city: new FormControl('')
    }),
    phones: new FormArray([
      new FormControl('')
    ])
  });
  
  // Or using FormBuilder
  constructor(private fb: FormBuilder) {
    this.profileForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: [''],
      address: this.fb.group({
        street: [''],
        city: ['']
      }),
      phones: this.fb.array([''])
    });
  }
  
  get phones(): FormArray {
    return this.profileForm.get('phones') as FormArray;
  }
  
  addPhone(): void {
    this.phones.push(new FormControl(''));
  }
  
  removePhone(index: number): void {
    this.phones.removeAt(index);
  }
}
```

---

## 59. How to create custom validators?

### Answer:
- **Sync validators**: Return errors or null immediately
- **Async validators**: Return Observable/Promise

### Example:
```typescript
// Sync validator function
function forbiddenNameValidator(forbiddenName: RegExp): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const forbidden = forbiddenName.test(control.value);
    return forbidden ? { forbiddenName: { value: control.value } } : null;
  };
}

// Async validator function
function uniqueEmailValidator(userService: UserService): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    return userService.checkEmailExists(control.value).pipe(
      map(exists => exists ? { emailTaken: true } : null),
      catchError(() => of(null))
    );
  };
}

// Cross-field validator
function dateRangeValidator(control: AbstractControl): ValidationErrors | null {
  const startDate = control.get('startDate')?.value;
  const endDate = control.get('endDate')?.value;
  
  if (startDate && endDate && new Date(startDate) > new Date(endDate)) {
    return { dateRange: 'Start date must be before end date' };
  }
  return null;
}

// Usage
@Component({
  template: `
    <form [formGroup]="form">
      <input formControlName="username">
      <div *ngIf="username.errors?.['forbiddenName']">
        Name "{{ username.errors?.['forbiddenName'].value }}" is not allowed
      </div>
      
      <input formControlName="email">
      <div *ngIf="email.pending">Checking...</div>
      <div *ngIf="email.errors?.['emailTaken']">Email already taken</div>
      
      <input type="date" formControlName="startDate">
      <input type="date" formControlName="endDate">
      <div *ngIf="form.errors?.['dateRange']">{{ form.errors?.['dateRange'] }}</div>
    </form>
  `
})
export class ValidatorComponent {
  form: FormGroup;
  
  constructor(private fb: FormBuilder, private userService: UserService) {
    this.form = this.fb.group({
      username: ['', [
        Validators.required,
        forbiddenNameValidator(/admin/i)
      ]],
      email: ['', 
        [Validators.required, Validators.email],
        [uniqueEmailValidator(userService)]
      ],
      startDate: [''],
      endDate: ['']
    }, {
      validators: dateRangeValidator
    });
  }
  
  get username() { return this.form.get('username')!; }
  get email() { return this.form.get('email')!; }
}

// Directive-based validator (for template-driven)
@Directive({
  selector: '[appForbiddenName]',
  providers: [{
    provide: NG_VALIDATORS,
    useExisting: ForbiddenNameDirective,
    multi: true
  }]
})
export class ForbiddenNameDirective implements Validator {
  @Input('appForbiddenName') forbiddenName = '';
  
  validate(control: AbstractControl): ValidationErrors | null {
    return this.forbiddenName 
      ? forbiddenNameValidator(new RegExp(this.forbiddenName, 'i'))(control)
      : null;
  }
}
```

---

## Form Control States

### Answer:
FormControl tracks various states:

| State | Description |
|-------|-------------|
| **valid/invalid** | Validation status |
| **pristine/dirty** | User modified value |
| **touched/untouched** | User focused/blurred |
| **pending** | Async validation running |
| **enabled/disabled** | Can interact |

### Example:
```typescript
@Component({
  template: `
    <input [formControl]="name">
    
    <div>
      <p>Value: {{ name.value }}</p>
      <p>Valid: {{ name.valid }}</p>
      <p>Invalid: {{ name.invalid }}</p>
      <p>Pristine: {{ name.pristine }}</p>
      <p>Dirty: {{ name.dirty }}</p>
      <p>Touched: {{ name.touched }}</p>
      <p>Untouched: {{ name.untouched }}</p>
      <p>Pending: {{ name.pending }}</p>
    </div>
    
    <!-- Show error only when touched and invalid -->
    <div *ngIf="name.invalid && name.touched" class="error">
      Name is required
    </div>
    
    <!-- CSS classes applied automatically -->
    <!-- .ng-valid, .ng-invalid -->
    <!-- .ng-pristine, .ng-dirty -->
    <!-- .ng-touched, .ng-untouched -->
  `,
  styles: [`
    input.ng-invalid.ng-touched {
      border-color: red;
    }
    input.ng-valid.ng-touched {
      border-color: green;
    }
  `]
})
export class FormStateComponent {
  name = new FormControl('', Validators.required);
  
  programmaticUpdates(): void {
    // Set value
    this.name.setValue('John');
    
    // Patch value (for FormGroup)
    // this.form.patchValue({ name: 'John' });
    
    // Mark states
    this.name.markAsTouched();
    this.name.markAsDirty();
    this.name.markAsPristine();
    this.name.markAsUntouched();
    
    // Reset
    this.name.reset();
    this.name.reset('default value');
    
    // Disable/Enable
    this.name.disable();
    this.name.enable();
    
    // Update validation
    this.name.setValidators([Validators.required, Validators.minLength(3)]);
    this.name.updateValueAndValidity();
  }
}
```

---

## Dynamic Forms

### Answer:
Use **FormArray** and **FormGroup** for dynamic form generation.

### Example:
```typescript
@Component({
  template: `
    <form [formGroup]="dynamicForm">
      <div formArrayName="questions">
        <div *ngFor="let question of questions.controls; let i = index" [formGroupName]="i">
          <input formControlName="label" placeholder="Question">
          <select formControlName="type">
            <option value="text">Text</option>
            <option value="number">Number</option>
            <option value="select">Select</option>
          </select>
          <input formControlName="required" type="checkbox"> Required
          <button (click)="removeQuestion(i)">Remove</button>
        </div>
      </div>
      <button (click)="addQuestion()">Add Question</button>
    </form>
  `
})
export class DynamicFormComponent {
  dynamicForm = new FormGroup({
    questions: new FormArray<FormGroup>([])
  });
  
  get questions(): FormArray<FormGroup> {
    return this.dynamicForm.get('questions') as FormArray<FormGroup>;
  }
  
  addQuestion(): void {
    const questionGroup = new FormGroup({
      label: new FormControl('', Validators.required),
      type: new FormControl('text'),
      required: new FormControl(false)
    });
    this.questions.push(questionGroup);
  }
  
  removeQuestion(index: number): void {
    this.questions.removeAt(index);
  }
}
```

---
