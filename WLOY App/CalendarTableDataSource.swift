//
//  CalendarTableDataSource.swift
//  WLOY App
//
//  Created by William Robbins on 4/14/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation
import UIKit

class CalendarTableDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {
    
    // CONSTANTS
    let CALENDAR_ADDRESS = "http://wloy.org/events/"
    let CELL_IDENTIFIER = "CALENDAR_CELL"
    let NUM_SECTIONS = 1
    
    // DATA MEMBERS
    var eventArray = [WLOYEvent]()
    var tableView:UITableView!
    
    init(tv:UITableView) {
        tableView = tv
    }
    
    // numberOfSectionsInTableView - return number of sections (always equal to NUM_SECTIONS)
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return NUM_SECTIONS
    }
    
    // numberOfRowsInSection - return number of rows (equal to size of event array)
    func tableView(tableView:UITableView, numberOfRowsInSection section:Int) -> Int {
        return eventArray.count
    }
    
    // fetchEvents - grab the list of events from the WLOY website
    func fetchEvents() {
        // TEST CODE: List of fake events
        eventArray = [WLOYEvent]()
        eventArray.append(WLOYEvent(n:"Concert", mo:4, d:29, y:2016, h:4, mi:30))
        eventArray.append(WLOYEvent(n:"Live Broadcast", mo:4, d:30, y:2016, h:2, mi:00))
        
    }
    
    // cellForRowAtIndexPath - tell what cell to be displayed at a given index path
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCellWithIdentifier(CELL_IDENTIFIER) {
            let event = eventArray[indexPath.row]
            updateCellData(cell, event:event)
            return cell
        }
        else {
            let newCell = UITableViewCell(style:UITableViewCellStyle.Subtitle, reuseIdentifier:CELL_IDENTIFIER)
            let event = eventArray[indexPath.row]
            updateCellData(newCell, event:event)
            return newCell
        }
    }
    
    // updateCellData - change a cell's display to show a given show's information
    func updateCellData(cell:UITableViewCell, event:WLOYEvent) {
        cell.textLabel?.text = event.name
        
        // Format time
        var subtitle = String(event.month) + "/" + String(event.day) + "\t\t\t" + String(event.hour) + ":"
        if(event.minute < 10) {
            subtitle = subtitle + "0" + String(event.minute)
        }
        else {
            subtitle = subtitle + String(event.minute)
        }
        cell.detailTextLabel?.text = subtitle
    }
    
    // didSelectRowAtIndexPath - handle a user tapping a cell
    func tableView(_ tableView:UITableView, didSelectRowAtIndexPath indexPath:NSIndexPath) {
        eventArray[indexPath.row].storeToCalendar()
        tableView.deselectRowAtIndexPath(indexPath, animated:true)
    }
    
    
}