# Angular Interview Questions

---

## BEGINNER LEVEL (0–2 Years)

### Angular Basics

* What is Angular?
* Difference between AngularJS and Angular
* What are components?
* What is a module?
* What is a service?
* What is a directive?
* What is data binding?
* Types of data binding in Angular
* What is interpolation?
* What are templates?
* What is the purpose of @NgModule?

### Components and Templates

* What is a component in Angular?
* Difference between component and directive
* How to pass data from parent to child component?
* How to pass data from child to parent component?
* What are input and output decorators?
* What is @ViewChild and @ContentChild?

### Project Configuration Files

#### angular.json

* What is angular.json?
* What is its role in an Angular project?
* How does angular.json manage build and serve configurations?
* How do you configure environments in angular.json?
* How do you add assets and styles globally using angular.json?

#### package.json

* What is package.json?
* Why is package.json important?
* How does package.json manage dependencies?
* What are devDependencies and dependencies?
* What are scripts in package.json and how are they used?
* How to run custom npm scripts defined in package.json?

#### tsconfig.json

* What is tsconfig.json?
* What compiler options are most important?
* What does strict mode do?
* Difference between target and module?
* How to enable strictNullChecks and noImplicitAny?

#### Webpack

* What is Webpack?
* How does Angular use Webpack internally?
* What is the role of loaders and plugins in Webpack?
* Difference between development and production build using Webpack?
* How can you customize Webpack in Angular?

---

## INTERMEDIATE LEVEL (2–4 Years)

### Directives and Pipes

* What are structural directives?
* What are attribute directives?
* Common built-in directives (ngIf, ngFor, ngSwitch)
* What is a pipe?
* Difference between built-in and custom pipe
* How to create a custom pipe

### Services and Dependency Injection

* What is a service in Angular?
* How dependency injection works in Angular?
* What is providedIn: 'root'?
* Difference between service provided in root and module
* Singleton services vs multiple instances

### Routing

* What is Angular Router?
* Difference between routerLink and href
* How to pass parameters in routes?
* What are route guards?
* What is lazy loading?

### Forms

* Difference between template-driven and reactive forms
* How to create a reactive form?
* Form validation (built-in and custom)
* FormControl, FormGroup, FormBuilder
* How to dynamically add form controls?

### State Management

* What is state management in Angular?
* Difference between local component state and global application state
* What is RxJS and how is it used in Angular?
* What is NgRx?
* Explain the concept of store, actions, reducers, and selectors
* Difference between BehaviorSubject, Subject, and ReplaySubject
* How to manage async state using NgRx Effects
* How to connect components to store using select and dispatch
* Difference between NgRx, Akita, and other state management libraries
* How to debug state using NgRx DevTools

---

## ADVANCED LEVEL (4+ Years)

### Lifecycle Hooks

* What are lifecycle hooks?
* Difference between ngOnInit and constructor
* ngOnChanges, ngDoCheck, ngAfterViewInit, ngAfterContentInit
* When to use each lifecycle hook

### Change Detection

* How change detection works in Angular?
* What is zone.js?
* Difference between default and OnPush change detection strategy
* How to optimize change detection

### RxJS and Observables

* What is RxJS?
* What is an Observable?
* Difference between Promise and Observable
* Common RxJS operators (map, filter, switchMap, mergeMap, concatMap, exhaustMap)
* How to unsubscribe from Observables?
* What is Subject, BehaviorSubject, ReplaySubject, AsyncSubject?
* Difference between cold and hot Observables
* How to handle error and retry logic in Observables

### Angular Modules and Architecture

* Difference between feature module and root module
* Shared module vs Core module
* What is Angular CLI and its usage?
* What is Ahead-of-Time (AOT) compilation?
* Difference between JIT and AOT compilation

---

## FREQUENTLY ASKED QUESTIONS

### Configuration Files

* Why are configuration files like angular.json, package.json, tsconfig.json required?
* How does webpack help in Angular builds?
* Difference between development and production build in Angular
* How to add global styles and scripts using angular.json?
* What are the common scripts in package.json and their purpose?
* How to configure TypeScript compiler options for large Angular projects?

### General Questions

* How do you optimize Angular applications?
* How does Angular handle HTTP requests?
* Difference between Angular forms and React forms
* How do you implement authentication in Angular?
* What are best practices for Angular folder structure?
* How do you communicate between non-related components?
* Difference between ngIf and hidden attribute
* Difference between Angular and React for enterprise projects
* How do you handle errors globally in Angular?
* What is lazy loading and how is it implemented?
* How to manage state using NgRx or other state management libraries?
* Difference between using RxJS BehaviorSubject vs NgRx store

---

## VERY IMPORTANT INTERVIEW QUESTIONS

* Explain Angular architecture in your project
* How did you implement routing in your project?
* How did you handle state management?
* Explain your usage of services and dependency injection
* Explain lifecycle hooks you used in your project
* How did you optimize change detection in your project?
* How did you integrate RxJS Observables in your project?
* Explain Angular forms implementation in your project
* How do configuration files help in project management and build optimization?
* Explain difference between development and production builds
* Explain your usage of NgRx store, actions, reducers, and selectors in your project
* How do you debug state issues using NgRx DevTools?

---

## FINAL INTERVIEW NOTE

Interviewers focus on concept clarity, component design, state management, forms, routing, RxJS usage, NgRx, and understanding of project configuration files.

You should be able to explain Angular architecture, component communication, lifecycle hooks, state management strategies, configuration files, and real-time implementation clearly.

This document can be used as a last-day revision checklist for Angular interviews.
