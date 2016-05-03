//
//  BackendConnectorDelegate.swift
//  WLOY App
//
//  Created by William Robbins on 4/9/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation

class BackendConnectorDelegate: NSObject, NSStreamDelegate {
    
    // handleEvent - check if an error has occured in opening a stream
    func stream(aStream:NSStream, handleEvent eventCode:NSStreamEvent) {
        // Ensure this is the correct stream
        if((BackendConnector.outputStream != nil) && (aStream == BackendConnector.outputStream)) {
            switch eventCode {
            case NSStreamEvent.ErrorOccurred:
                NSLog("ERROR: Could not open output stream")
            case NSStreamEvent.OpenCompleted:
                NSLog("Opened output stream successfully")
            case NSStreamEvent.HasSpaceAvailable:
                NSLog("Output stream has space available to write")
            default:
                break
            }
        }
    }
}