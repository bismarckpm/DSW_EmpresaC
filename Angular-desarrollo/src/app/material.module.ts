import {NgModule} from '@angular/core';
import { MAT_LABEL_GLOBAL_OPTIONS, MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import {MatSelectModule} from '@angular/material/select';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {MatFormFieldModule} from '@angular/material/form-field';


@NgModule({
    imports: [
        MatNativeDateModule,
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
        MatSidenavModule,
        MatListModule,
        MatCardModule ,
        MatDividerModule,
        MatSelectModule,
        MatCheckboxModule,
            
        MatCardModule,
        MatProgressSpinnerModule,
        MatFormFieldModule,
        MatSelectModule,
        MatDividerModule      
    ],
    exports: [
        MatNativeDateModule,
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
        MatSidenavModule,
        MatListModule,
        MatCardModule,
        MatDividerModule,
        MatSelectModule,
        MatCheckboxModule,
        MatProgressSpinnerModule,
        MatFormFieldModule,
        MatSelectModule,
        MatDividerModule
    ],
})

export class MaterialModule{}
