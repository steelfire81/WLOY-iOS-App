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
    let SCHEDULE_ADDRESS = "http://wloy.org/shows/"
    let TIME_UPDATE_INTERVAL = 60 // time between schedule checks in seconds
    
    // OUTLETS
    @IBOutlet weak var scheduleTable: UITableView!
    
    // DATA MEMBERS
    var scheduleDataSource: ScheduleTableDataSource!
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Initialize schedule data source
        scheduleDataSource = ScheduleTableDataSource(tv:scheduleTable)
        scheduleTable.dataSource = scheduleDataSource
        scheduleDataSource.updateSchedule()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // getCurrentTime - return the current time and date as NSDateComponents
    func getCurrentTime() -> NSDateComponents {
        return NSCalendar.currentCalendar().components([NSCalendarUnit.Year, NSCalendarUnit.Month, NSCalendarUnit.Day, NSCalendarUnit.Hour, NSCalendarUnit.Minute, NSCalendarUnit.Weekday], fromDate:NSDate())
    }
    
    /*
    // retrieveSchedule - return the schedule from the WLOY website as a string (HTML)
    func retrieveSchedule() -> String {
        let url = NSURL(string: SCHEDULE_ADDRESS)
        do {
            let html = try String(contentsOfURL:url!)
            return html
        } catch let error as NSError {
            NSLog("Error retrieving schedule HTML")
            return ""
        }
    }
    */
}

