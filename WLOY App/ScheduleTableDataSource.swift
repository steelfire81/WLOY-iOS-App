//
//  ScheduleTableDataSource.swift
//  WLOY App
//
//  Created by William Robbins on 4/2/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation
import UIKit

class ScheduleTableDataSource: NSObject, UITableViewDataSource {
    
    // CONSTANTS
    let CELL_IDENTIFIER = "cell"
    let DEFAULT_SHOW_LABEL = "EMPTY SHOW"
    let DEFAULT_SHOW_HOUR = 12
    let DEFAULT_SHOW_MINUTE = 00
    let MAX_SHOWS = 10 // maximum number of shows in schedule table
    let NUM_SECTIONS = 1
    
    // DATA MEMBERS
    var showArray = [Show]()
    var tableView: UITableView!
    
    // METHODS
    init(tv:UITableView) {
        tableView = tv
        
        for _ in 0...(MAX_SHOWS - 1) {
            showArray.append(Show(n:DEFAULT_SHOW_LABEL, h:DEFAULT_SHOW_HOUR, m:DEFAULT_SHOW_MINUTE))
        }
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return NUM_SECTIONS
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return MAX_SHOWS
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell:UITableViewCell!
        
        if let cell = tableView.dequeueReusableCellWithIdentifier(CELL_IDENTIFIER) as UITableViewCell {
        
        if(cell == nil) {
            cell = UITableViewCell(style:UITableViewCellStyle.Default, reuseIdentifier:CELL_IDENTIFIER)
        }
        
        NSLog("OHOHOHOHOHOHOHOHOHOHOHOHOHOHOHOHO")
        let show = showArray[indexPath.row]
        cell.textLabel?.text = String(show.startHour) + ":" + String(show.startMinute) + " - " + showArray[indexPath.row].name
        return cell
    }
}