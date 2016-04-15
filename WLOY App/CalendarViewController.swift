//
//  CalendarViewController.swift
//  WLOY App
//
//  Created by William Robbins on 3/29/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import UIKit

class CalendarViewController: UIViewController {

    // OUTLETS
    @IBOutlet weak var calendarTable: UITableView!
    
    // DATA MEMBERS
    var calendarDataSource: CalendarTableDataSource!
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Initialize calendar data source
        calendarDataSource = CalendarTableDataSource(tv:calendarTable)
        calendarTable.dataSource = calendarDataSource
        calendarDataSource.fetchEvents()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

}
