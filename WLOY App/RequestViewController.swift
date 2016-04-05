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
    let ERR_NOT_ENOUGH_INFO = "Please provide a song title and artist"
    
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
            NSLog(ERR_NOT_ENOUGH_INFO) // TODO: Make a pop-up notification
            return
        }
        
        // All info provided; send request
        NSLog("Requesting " + title! + " by " + artist!) // TODO: Actually send request to server
        
        // Clear fields
        songTitleField.text = ""
        artistField.text = ""
    }
}
