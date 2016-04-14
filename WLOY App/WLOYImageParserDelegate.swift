//
//  WLOYImageParserDelegate.swift
//  WLOY App
//
//  Created by William Robbins on 4/13/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation
import UIKit

class WLOYImageParserDelegate: NSObject, NSXMLParserDelegate {
    
    // CONSTANTS
    let XML_PHOTOURL = "ra:photourl"
    
    // DATA MEMBERS
    var currentImageURL: String
    var parsingImageURL: Bool
    var tempImageURL: String
    
    // METHODS
    override init() {
        currentImageURL = ""
        parsingImageURL = false
        tempImageURL = ""
    }
    
    // parserDidStartDocument - called when the parser begins reading the XML document
    func parserDidStartDocument(parser:NSXMLParser) {
        parsingImageURL = false
        tempImageURL = ""
    }
    
    // didStartElement - called when the parser begins parsing an XML element
    func parser(_ parser:NSXMLParser, didStartElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?,
        attributes attributeDict:[String : String]) {
            if(elementName == XML_PHOTOURL) {
                parsingImageURL = true
            }
    }
    
    // didEndElement - called when the parser finishes parsing an XML element
    func parser(_ parser:NSXMLParser, didEndElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?) {
        if(parsingImageURL) {
            parsingImageURL = false
            currentImageURL = tempImageURL
        }
    }
    
    // foundCharacters - called when this parser retrieves new characters within an element
    func parser(_ parser:NSXMLParser, foundCharacters string:String) {
        if(parsingImageURL) {
            tempImageURL = tempImageURL + string
        }
    }
    
    // getImage - returns the image stored in the current image URL (if there is one)
    func getImage() -> UIImage! {
        if(currentImageURL == "") {
            return nil
        }
        
        // Convert the data stored at the URL into an image
        return UIImage(data:NSData(contentsOfURL:NSURL(string:currentImageURL)!)!)
    }
}