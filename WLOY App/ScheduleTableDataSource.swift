//
//  ScheduleTableDataSource.swift
//  WLOY App
//
//  Created by William Robbins on 4/2/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation
import UIKit

class ScheduleTableDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {
    
    // CONSTANTS
    let CELL_IDENTIFIER = "BROADCAST_SCHEDULE_CELL"
    let DEFAULT_SHOW_LABEL = "EMPTY SHOW"
    let DEFAULT_SHOW_HOUR = 12
    let DEFAULT_SHOW_MINUTE = 0
    let DEFAULT_SHOW_DESCRIPTION = "DESCRIPTION"
    let MAX_SHOWS = 10 // maximum number of shows in schedule table
    let NUM_SECTIONS = 1
    let SCHEDULE_ADDRESS = "http://wloy.org/shows"
    
    // DATA MEMBERS
    var showArray = [Show]()
    var tableView: UITableView
    var parentViewController:ScheduleViewController
    
    // METHODS
    init(tv:UITableView, p:ScheduleViewController) {
        tableView = tv
        parentViewController = p
        
        for _ in 0...(MAX_SHOWS - 1) {
            showArray.append(Show(n:DEFAULT_SHOW_LABEL, h:DEFAULT_SHOW_HOUR, m:DEFAULT_SHOW_MINUTE, d:DEFAULT_SHOW_DESCRIPTION))
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
    
    // updateSchedule - takes an HTML page as a string and parses it to update the schedule table
    func updateSchedule() {
        let scheduleXMLParser = NSXMLParser(contentsOfURL:NSURL(string:SCHEDULE_ADDRESS)!)
        let scheduleXMLParserDelegate = WLOYScheduleParserDelegate()
        scheduleXMLParser!.delegate = scheduleXMLParserDelegate
        scheduleXMLParser!.parse()
    }
    
    // didSelectRowAtIndexPath - handle a user tapping a cell
    func tableView(_ tableView:UITableView, didSelectRowAtIndexPath indexPath:NSIndexPath) {
        parentViewController.displayNotification(showArray[indexPath.row].showDescription, title:showArray[indexPath.row].name)
        tableView.deselectRowAtIndexPath(indexPath, animated:true)
    }
}