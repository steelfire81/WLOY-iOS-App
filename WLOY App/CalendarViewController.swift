//
//  CalendarViewController.swift
//  WLOY App
//
//  Created by William Robbins on 3/29/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import UIKit

class CalendarViewController: UIViewController {

    // CONSTANTS
    let ACCEPT_BUTTON = "Yes"
    let CALENDAR_TITLE = "Store Event"
    let DECLINE_BUTTON = "No"
    let NOTIFICATION_BUTTON = "OK"
    
    // OUTLETS
    @IBOutlet weak var calendarTable: UITableView!
    
    // DATA MEMBERS
    var calendarDataSource: CalendarTableDataSource!
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Initialize calendar data source
        calendarDataSource = CalendarTableDataSource(tv:calendarTable, p:self)
        calendarTable.dataSource = calendarDataSource
        calendarTable.delegate = calendarDataSource
        calendarDataSource.fetchEvents()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    /*
    // askForCalendarApproval - ask if the user wants to store the event to the calendar
    func askForCalendarApproval(showName:String) -> Bool {
        let message = "Add " + showName + " to your phone's calendar?"
        
        let notificationController = UIAlertController(title:CALENDAR_TITLE, message:message, preferredStyle:UIAlertControllerStyle.Alert)
        notificationController.addAction(UIAlertAction(title:ACCEPT_BUTTON, style:UIAlertActionStyle.Default, handler:{
            (alert) -> Void in
            CalendarViewController.approval = true}))
        notificationController.addAction(UIAlertAction(title:DECLINE_BUTTON, style:UIAlertActionStyle.Default, handler:{
            (alert) -> Void in
            CalendarViewController.approval = false}))
        
        return CalendarViewController.approval
    }
*/
    
    // displayNotification - show a pop-up message with the given text
    func displayNotification(message:String, title:String) {
        let notificationController = UIAlertController(title:title, message:message, preferredStyle:UIAlertControllerStyle.Alert)
        notificationController.addAction(UIAlertAction(title:NOTIFICATION_BUTTON, style:UIAlertActionStyle.Default, handler:nil))
        presentViewController(notificationController, animated:true, completion:nil)
    }
}
