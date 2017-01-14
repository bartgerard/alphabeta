import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {
    ButtonModule,
    CheckboxModule,
    DropdownModule,
    InputTextModule
} from 'primeng/primeng';

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        ButtonModule,
        CheckboxModule,
        DropdownModule,
        InputTextModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
