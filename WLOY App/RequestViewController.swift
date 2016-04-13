//
//  RequestViewController.swift
//  WLOY App
//
//  Created by William Robbins on 3/29/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import UIKit

class RequestViewController: UIViewController {

    // CONSTANTS - Error Messages
    let ERR_NETWORK = "Could not connect to requests server"
    let ERR_NOT_ENOUGH_INFO = "Please provide a song title and artist."
    let ERR_TITLE = "Error"
    let NOTIFICATION_BUTTON = "OK"
    let NOTIFICATION_SENT = "Request sent!"
    let NOTIFICATION_TITLE = "Success"
    
    // OUTLETS
    @IBOutlet weak var songTitleField: UITextField!
    @IBOutlet weak var artistField: UITextField!
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        songTitleField.text = ""
        artistField.text = ""
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    @IBAction func requestSong(sender: AnyObject) {
        let title = songTitleField.text
        let artist = artistField.text
        
        // Ensure both fields are entered
        if((title == "") || (artist == "")) {
            displayNotification(ERR_NOT_ENOUGH_INFO, title:ERR_TITLE)
            return
        }
        
        let success = BackendConnector.sendRequestMessage(title!, artist:artist!)
        
        if(success) {
            // Clear fields
            songTitleField.text = ""
            artistField.text = ""
            
            // Notify user
            displayNotification(NOTIFICATION_SENT, title:NOTIFICATION_TITLE)
        } else {
            // Notify user of network error
            displayNotification(ERR_NETWORK, title:ERR_TITLE)
        }
    }
    
    // displayNotification - show a pop up notification with the given text
    func displayNotification(message:String, title:String) {
        let notificationController = UIAlertController(title:title, message:message, preferredStyle:UIAlertControllerStyle.Alert)
        notificationController.addAction(UIAlertAction(title:NOTIFICATION_BUTTON, style:UIAlertActionStyle.Default, handler: nil))
        presentViewController(notificationController, animated:true, completion:nil)
    }
}
