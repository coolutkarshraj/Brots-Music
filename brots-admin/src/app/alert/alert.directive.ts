import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';


@Component({
  selector: "alert",
  templateUrl: "alert.html",
  styleUrls: ["./alert.css"]
})

export class AlertComponent  {
  modalTitle: string ;
  modalString: string ;

  constructor(public dialogRef: MatDialogRef<AlertComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    this.modalTitle = data.title ;
    this.modalString = data.msg ;
    console.log(data)

  }

  onOkClick():void {
    this.dialogRef.close()
  }
  
}
