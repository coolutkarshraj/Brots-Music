import { Injectable } from "@angular/core";
import { MatDialog,MatDialogConfig } from "@angular/material/dialog";
import { AlertComponent } from "../alert/alert.directive";


@Injectable()
export class AlertService {



  constructor(private dialog:MatDialog) {}

  openModal(dialogTittle,dialogMsg) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      title : dialogTittle,
      msg : dialogMsg
    };
    dialogConfig.position = {
      top: '250px',
      left: '500px'
    };
    dialogConfig.width = '500px' ;
    dialogConfig.height = '170px';

    const dialogRef = this.dialog.open(AlertComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      console.log("response: " + result)
    });
  }

}
