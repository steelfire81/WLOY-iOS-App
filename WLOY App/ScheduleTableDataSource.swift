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
    let CELL_IDENTIFIER = "BROADCAST_SCHEDULE_CELL"
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
    
    // cellForRowAtIndexPath - tell what cell to be displayed at a given index path
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCellWithIdentifier(CELL_IDENTIFIER) {
            let show = showArray[indexPath.row]
            updateCellData(cell, show:show)
            return cell
        }
        else {
            // fatalError("Unknown cell identifier - " + String(indexPath.row))
            let newCell = UITableViewCell(style:UITableViewCellStyle.Subtitle, reuseIdentifier:CELL_IDENTIFIER)
            let show = showArray[indexPath.row]
            updateCellData(newCell, show:show)
            return newCell
        }
    }
    
    // updateCellData - change a cell's display to show a given show's information
    func updateCellData(cell:UITableViewCell, show:Show) {
        cell.textLabel?.text = show.name
        
        // If minute is under 10, add an extra 0
        if(show.startMinute < 10) {
            cell.detailTextLabel?.text = String(show.startHour) + ":0" + String(show.startMinute)
        }
        else {
            cell.detailTextLabel?.text = String(show.startHour) + ":" + String(show.startMinute)
        }
    }
}