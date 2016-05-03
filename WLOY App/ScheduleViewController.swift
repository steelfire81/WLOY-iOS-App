//
//  SecondViewController.swift
//  WLOY App
//
//  Created by William Robbins on 3/15/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import UIKit

class ScheduleViewController: UIViewController {

    // CONSTANTS - Weekdays
    let SUNDAY = 1
    let MONDAY = 2
    let TUESDAY = 3
    let WEDNESDAY = 4
    let THURSDAY = 5
    let FRIDAY = 6
    let SATURDAY = 7
    
    // CONSTANTS - Other
    let NOTIFICATION_BUTTON = "OK"
    let TIME_UPDATE_INTERVAL = 60 // time between schedule checks in seconds
    
    // OUTLETS
    @IBOutlet weak var scheduleTable: UITableView!
    
    // DATA MEMBERS
    var scheduleDataSource: ScheduleTableDataSource!
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Initialize schedule data source
        scheduleDataSource = ScheduleTableDataSource(tv:scheduleTable, p:self)
        scheduleTable.dataSource = scheduleDataSource
        scheduleTable.delegate = scheduleDataSource
        scheduleDataSource.updateSchedule()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // displayNotification - show a pop-up message with the given text
    func displayNotification(message:String, title:String) {
        let notificationController = UIAlertController(title:title, message:message, preferredStyle:UIAlertControllerStyle.Alert)
        notificationController.addAction(UIAlertAction(title:NOTIFICATION_BUTTON, style:UIAlertActionStyle.Default, handler:nil))
        presentViewController(notificationController, animated:true, completion:nil)
    }
}

