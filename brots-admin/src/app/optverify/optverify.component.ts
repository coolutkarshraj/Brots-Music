import { Component, OnInit,Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-optverify',
  templateUrl: './optverify.component.html',
  styleUrls: ['./optverify.component.css']
})
export class OptverifyComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<OptverifyComponent>,
    @Inject(MAT_DIALOG_DATA) public data
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
