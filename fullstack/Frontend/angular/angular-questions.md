
## **Angular  Questions – Complete Prep**

### **Beginner Level (0–2 Years)**

#### **Angular Basics**

1. What is Angular?
2. Difference between AngularJS and Angular
3. What are components?
4. What is a module?
5. What is a service?
6. What is a directive?
7. What is data binding?
8. Types of data binding in Angular
9. What is interpolation?
10. What are templates?
11. What is the purpose of `@NgModule`?

#### **Components and Templates**

12. What is a component in Angular?
13. Difference between component and directive
14. How to pass data from parent to child component?
15. How to pass data from child to parent component?
16. What are input and output decorators?
17. What is `@ViewChild` and `@ContentChild`?

#### **Project Configuration Files**

**angular.json**

18. What is `angular.json`?
19. What is its role in an Angular project?
20. How does `angular.json` manage build and serve configurations?
21. How do you configure environments in `angular.json`?
22. How do you add assets and styles globally using `angular.json`?

**package.json**

23. What is `package.json`?
24. Why is `package.json` important?
25. How does `package.json` manage dependencies?
26. What are `devDependencies` and `dependencies`?
27. What are scripts in `package.json` and how are they used?
28. How to run custom npm scripts defined in `package.json`?

**tsconfig.json**

29. What is `tsconfig.json`?
30. What compiler options are most important?
31. What does strict mode do?
32. Difference between `target` and `module`?
33. How to enable `strictNullChecks` and `noImplicitAny`?

**Webpack**

34. What is Webpack?
35. How does Angular use Webpack internally?
36. What is the role of loaders and plugins in Webpack?
37. Difference between development and production build using Webpack?
38. How can you customize Webpack in Angular?

### **Intermediate Level (2–4 Years)**

#### **Directives and Pipes**

39. What are structural directives?
40. What are attribute directives?
41. Common built-in directives (`ngIf`, `ngFor`, `ngSwitch`)
42. What is a pipe?
43. Difference between built-in and custom pipe
44. How to create a custom pipe

#### **Services and Dependency Injection**

45. What is a service in Angular?
46. How dependency injection works in Angular?
47. What is `providedIn: 'root'`?
48. Difference between service provided in root and module
49. Singleton services vs multiple instances

#### **Routing**

50. What is Angular Router?
51. Difference between `routerLink` and `href`
52. How to pass parameters in routes?
53. What are route guards?
54. What is lazy loading?

#### **Forms**

55. Difference between template-driven and reactive forms
56. How to create a reactive form?
57. Form validation (built-in and custom)
58. `FormControl`, `FormGroup`, `FormBuilder`
59. How to dynamically add form controls?

#### **State Management**

60. What is state management in Angular?
61. Difference between local component state and global application state
62. What is RxJS and how is it used in Angular?
63. What is NgRx?
64. Explain the concept of store, actions, reducers, and selectors
65. Difference between `BehaviorSubject`, `Subject`, and `ReplaySubject`
66. How to manage async state using NgRx Effects
67. How to connect components to store using `select` and `dispatch`
68. Difference between NgRx, Akita, and other state management libraries
69. How to debug state using NgRx DevTools

### **Advanced Level (4+ Years)**

#### **Lifecycle Hooks**

70. What are lifecycle hooks?
71. Difference between `ngOnInit` and constructor
72. `ngOnChanges`, `ngDoCheck`, `ngAfterViewInit`, `ngAfterContentInit`
73. When to use each lifecycle hook

#### **Change Detection**

74. How change detection works in Angular?
75. What is `zone.js`?
76. Difference between default and `OnPush` change detection strategy
77. How to optimize change detection

#### **RxJS and Observables**

78. What is RxJS?
79. What is an Observable?
80. Difference between Promise and Observable
81. Common RxJS operators (`map`, `filter`, `switchMap`, `mergeMap`, `concatMap`, `exhaustMap`)
82. How to unsubscribe from Observables?
83. What is `Subject`, `BehaviorSubject`, `ReplaySubject`, `AsyncSubject`?
84. Difference between cold and hot Observables
85. How to handle error and retry logic in Observables

#### **Angular Modules and Architecture**

86. Difference between feature module and root module
87. Shared module vs Core module
88. What is Angular CLI and its usage?
89. What is Ahead-of-Time (AOT) compilation?
90. Difference between JIT and AOT compilation

### **Frequently Asked / Scenario Questions**

91. Explain Angular architecture in your project
92. How did you implement routing in your project?
93. How did you handle state management?
94. Explain your usage of services and dependency injection
95. Explain lifecycle hooks you used in your project
96. How did you optimize change detection in your project?
97. How did you integrate RxJS Observables in your project?
98. Explain Angular forms implementation in your project
99. How do configuration files help in project management and build optimization?
100. Explain difference between development and production builds
101. Explain your usage of NgRx store, actions, reducers, and selectors in your project
102. How do you debug state issues using NgRx DevTools?

### **Micro Frontend / Micro-Component Questions**

103. What is a Micro Frontend (MFE)? When would you use it?
104. Explain advantages and challenges of implementing MFEs in Angular.
105. How would you share common services (e.g., authentication, API service) across multiple MFEs?
106. How would you implement communication between two MFEs (e.g., cart and profile)?
107. How do you handle routing when multiple Angular MFEs are hosted on the same page?
108. How can you lazy load micro-components inside a larger Angular shell?
109. How to allow independent deployment of MFEs without affecting main app?
110. How do you handle versioning of shared libraries across multiple MFEs?
111. Using Web Components / Angular Elements for micro-components – how does it work?
112. How to package and distribute a micro-component for reuse?

### **Advanced / Missed Questions**

#### **Performance & Optimization**

113. How does `trackBy` improve performance in `*ngFor`? Give an example.
114. How would you lazy load images or components to improve performance?
115. Explain `OnPush` change detection and when you would use it.
116. How can you use memoization with pipes to optimize performance?
117. How do you avoid unnecessary API calls or component re-rendering?

#### **Advanced RxJS**

118. Explain higher-order Observables (`switchMap`, `mergeMap`, `exhaustMap`) with examples.
119. How do you handle errors in Observables using `catchError`, `retry`, or `retryWhen`?
120. How do you combine multiple Observables using `forkJoin`, `combineLatest`, or `zip`?
121. How would you implement debounce or throttle for user input or search functionality?

#### **Testing**

122. How do you unit test components, services, pipes, and directives using Jasmine/Karma?
123. How do you test Observables and async operations?
124. How do you use `HttpClientTestingModule` to test API calls?
125. How do you perform end-to-end testing using Protractor or Cypress?

#### **Security**

126. How do you protect routes using Guards (`CanActivate`, `CanLoad`)?
127. How do you prevent XSS in Angular templates?
128. How do you secure APIs and tokens on the front-end?
129. How do you use Angular `DomSanitizer` to prevent security risks?

#### **Angular CLI & Tooling**

130. What are custom schematics and how do you use them?
131. How do you handle `ng update` and migration strategies?
132. How do you optimize builds using budgets, AOT, minification, and tree-shaking?

#### **Internationalization (i18n)**

133. How do you translate Angular apps using Angular i18n or `ngx-translate`?
134. How do you lazy load translations for large Angular applications?

#### **Accessibility**

135. How do you use ARIA attributes in Angular components?
136. How do you implement keyboard navigation for Angular components?
137. How do you ensure screen reader compatibility?

#### **Server-side Rendering (Angular Universal)**

138. When and why would you use server-side rendering (SSR) in Angular?
139. Difference between pre-rendering and live server rendering in Angular Universal.
140. How do you handle API calls when using Angular Universal?

#### **State Management Patterns**

141. How do you use NgRx selectors and memoization for performance?
142. How do you handle side effects for async operations in NgRx (Effects)?
143. Difference between local component state and global state in Angular.
144. How do you share state across multiple MFEs or micro-components?

```
